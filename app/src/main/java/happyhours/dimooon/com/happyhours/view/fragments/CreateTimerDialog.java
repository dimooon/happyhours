package happyhours.dimooon.com.happyhours.view.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.support.v7.internal.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import happyhours.dimooon.com.happyhours.R;
import happyhours.dimooon.com.happyhours.database.facade.HappyFacade;

import happyhours.dimooon.com.happyhours.database.facade.bean.HappyTimer;
import happyhours.dimooon.com.happyhours.view.fragments.adapters.TimersAdapter;

public class CreateTimerDialog{

        private AlertDialog alertDialog;

        private Button createNewTimerButton;
        private RecyclerView timersList;

        private View listContainer;
        private View createContainer;

        private Button backButton;
        private Button addNewTimerButton;

        private CheckBox happyBox;

        private HappyFacade facade;

        private CreateTimerDialogListener listener;

        public void show(final Activity activity, CreateTimerDialogListener listener){
            // layout and inflater

            this.listener = listener;

            LayoutInflater inflater = activity.getLayoutInflater();
            final View content = inflater.inflate(R.layout.create_timer_layout, null);

            AlertDialog.Builder  dialog = new AlertDialog.Builder(new ContextThemeWrapper(activity, android.R.style.Theme_Holo_Light));
            dialog.setTitle("Available timers");
            dialog.setView(content);

            listContainer = content.findViewById(R.id.createNewTimerDialogListView);
            createContainer = content.findViewById(R.id.createNewTimerDialogCreateView);

            createNewTimerButton = (Button) content.findViewById(R.id.createNewTimerButton);
            backButton = (Button) content.findViewById(R.id.createNewTimerDialogGoBackButton);
            addNewTimerButton = (Button) content.findViewById(R.id.createNewTimerDialogAddButton);

            facade = new HappyFacade(activity);

            happyBox = (CheckBox) content.findViewById(R.id.happy_task_check_box);

            timersList = (RecyclerView) content.findViewById(R.id.available_timers);
            initTimersList(activity);

            createNewTimerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeLayout(true);
                }
            });

            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    changeLayout(false);
                }
            });

            addNewTimerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = ((EditText)content.findViewById(R.id.createNewTimerDialogName)).getText().toString();

                    if(!TextUtils.isEmpty(name)){
                        facade.createTimer(name,happyBox.isChecked());
                        initTimersList(activity);
                    }

                    changeLayout(false);

                }
            });

            alertDialog = dialog.show();
        }

    public void dismiss(){
        alertDialog.dismiss();
    }

    public interface CreateTimerDialogListener{
        void onNewItemSelected(HappyTimer timer);
    }

    private void changeLayout(boolean showCreateTimerView){
        createContainer.setVisibility(showCreateTimerView ? View.VISIBLE : View.GONE);
        listContainer.setVisibility(showCreateTimerView ? View.GONE : View.VISIBLE);
    }

    private void initTimersList(Activity activity){

        timersList.setHasFixedSize(true);
        timersList.setLayoutManager(new LinearLayoutManager(activity));
        TimersAdapter adapter = new TimersAdapter(facade.getTimers());
        adapter.setListener(this.listener);
        timersList.setAdapter(adapter);
    }
}