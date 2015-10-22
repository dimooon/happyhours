package happyhours.dimooon.com.happyhours.view.fragments.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import happyhours.dimooon.com.happyhours.R;
import happyhours.dimooon.com.happyhours.model.database.facade.HappyFacade;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappySession;
import happyhours.dimooon.com.happyhours.model.database.facade.bean.HappyTimer;
import happyhours.dimooon.com.happyhours.model.database.manager.DatabaseSessionDataProvider;
import happyhours.dimooon.com.happyhours.view.custom.HappyEditText;
import happyhours.dimooon.com.happyhours.view.custom.KeyboardViewPresenter;

public class CreateTimerController {

    private Button createNewTimerButton;
    private RecyclerView timersList;
    private View listContainer;
    private View createContainer;
    private Button backButton;
    private Button addNewTimerButton;
    private CheckBox happyBox;
    private HappyFacade facade;
    private CreateTimerDialogListener listener;
    private KeyboardViewPresenter keyboardViewPresenter;
    private HappyEditText createNewTimerDialogName;

    public void show(final Context activity, final View content, final HappySession session, CreateTimerDialogListener listener,KeyboardViewPresenter keyboardViewPresenter){
            this.keyboardViewPresenter = keyboardViewPresenter;
            this.listener = listener;

            listContainer = content.findViewById(R.id.createNewTimerDialogListView);
            createContainer = content.findViewById(R.id.createNewTimerDialogCreateView);

            createNewTimerButton = (Button) content.findViewById(R.id.createNewTimerButton);
            backButton = (Button) content.findViewById(R.id.createNewTimerDialogGoBackButton);
            addNewTimerButton = (Button) content.findViewById(R.id.createNewTimerDialogAddButton);

            facade = new HappyFacade(activity);

            happyBox = (CheckBox) content.findViewById(R.id.happy_task_check_box);

            createNewTimerDialogName = ((HappyEditText)content.findViewById(R.id.createNewTimerDialogName));

            timersList = (RecyclerView) content.findViewById(R.id.available_timers);
            initTimersList(activity,session);

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
                    String name = createNewTimerDialogName.getText().toString();

                    if(!TextUtils.isEmpty(name)){
                        facade.createTimer(name,happyBox.isChecked());
                        initTimersList(activity, session);
                        changeLayout(false);
                    }
                }
            });

        }

    public interface CreateTimerDialogListener{
        void onNewItemSelected(HappyTimer timer);
    }

    private void changeLayout(boolean showCreateTimerView){
        createContainer.setVisibility(showCreateTimerView ? View.VISIBLE : View.GONE);
        listContainer.setVisibility(showCreateTimerView ? View.GONE : View.VISIBLE);

        if(showCreateTimerView){
            keyboardViewPresenter.bindEditText(createNewTimerDialogName);
            createNewTimerDialogName.bindToKeyboard(keyboardViewPresenter);
        }else{
            keyboardViewPresenter.close();
            createNewTimerDialogName.unbindFromKeyboard();
        }

    }

    private void initTimersList(Context activity,HappySession session){

        timersList.setHasFixedSize(true);
        timersList.setLayoutManager(new LinearLayoutManager(activity));
        TimersAdapter adapter = new TimersAdapter(new DatabaseSessionDataProvider(activity).getTimersNotAssignedToSession(session));
        adapter.setListener(this.listener);
        timersList.setAdapter(adapter);
    }
}