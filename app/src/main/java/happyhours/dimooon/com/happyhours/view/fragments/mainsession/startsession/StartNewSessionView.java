package happyhours.dimooon.com.happyhours.view.fragments.mainsession.startsession;

import android.view.View;

import happyhours.dimooon.com.happyhours.R;
import happyhours.dimooon.com.happyhours.view.custom.HappyEditText;

/**
 * Created by dmytro on 10/20/15.
 */
public class StartNewSessionView implements StartSessionView {

    private final View enterSessionNameLayout;
    private final HappyEditText reateSessionName;
    private StartSessionViewPresenter presenter;
    private View root;

    private View startButton;
    private View startSessionOkButton;
    private View startSessionCancelButton;

    public StartNewSessionView(View root) {

        this.root = root;

        startButton = root.findViewById(R.id.startSessionButton);
        enterSessionNameLayout = root.findViewById(R.id.startSessionLayout);

        startSessionOkButton = enterSessionNameLayout.findViewById(R.id.startSessionOkButton);
        startSessionCancelButton = enterSessionNameLayout.findViewById(R.id.startSessionCancelButton);
        reateSessionName = (HappyEditText) enterSessionNameLayout.findViewById(R.id.create_session_name);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.startNewMainSession();
            }
        });

        startSessionOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.startSessionWithName();
            }
        });

        startSessionCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.cancelStartSession();
            }
        });
    }

    @Override
    public void setPresenter(StartSessionViewPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public View getStartButton() {
        return this.startButton;
    }

    @Override
    public View getEnterSessionNameLayout() {
        return this.enterSessionNameLayout;
    }

    @Override
    public View getRoot() {
        return this.root;
    }

    @Override
    public View getStartSessionOkButton() {
        return startSessionOkButton;
    }

    @Override
    public View getStartSessionCancelButton() {
        return startSessionCancelButton;
    }

    @Override
    public HappyEditText getCreateSessionName() {
        return reateSessionName;
    }
}