package happyhours.dimooon.com.happyhours.view.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v7.internal.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import happyhours.dimooon.com.happyhours.R;
import happyhours.dimooon.com.happyhours.database.SessionManager;
import happyhours.dimooon.com.happyhours.database.facade.HappyFacade;
import happyhours.dimooon.com.happyhours.database.facade.bean.HappySession;
import happyhours.dimooon.com.happyhours.database.facade.bean.HappyTimer;
import happyhours.dimooon.com.happyhours.view.fragments.adapters.TimersAdapter;

public class StartSessionDialog {

    private AlertDialog alertDialog;
    private EditText create_session_name;

    public void show(final Activity activity, final SessionManager manager, final CreateSessionDialogListener listener) {
        // layout and inflater

        LayoutInflater inflater = activity.getLayoutInflater();
        final View content = inflater.inflate(R.layout.session_create_dialog, null);

        AlertDialog.Builder dialog = new AlertDialog.Builder(new ContextThemeWrapper(activity, android.R.style.Theme_Holo_Light));
        dialog.setTitle("Starting session");
        dialog.setView(content);
        dialog.setPositiveButton("Start", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (TextUtils.isEmpty(create_session_name.getText())) {
                    create_session_name.setError("No way to go without session name!");
                    return;
                }

                HappySession session = manager.startNewSession(create_session_name.getText().toString());

                if(listener!=null){
                    listener.onSessionCreated(session);
                }
            }
        });
        dialog.setNegativeButton("Not now", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        create_session_name = (EditText) content.findViewById(R.id.create_session_name);

        alertDialog = dialog.show();
    }

    public interface CreateSessionDialogListener{
        void onSessionCreated(HappySession session);
    }

}