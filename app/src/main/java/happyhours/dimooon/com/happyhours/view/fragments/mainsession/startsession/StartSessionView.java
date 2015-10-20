package happyhours.dimooon.com.happyhours.view.fragments.mainsession.startsession;

import android.view.View;

import happyhours.dimooon.com.happyhours.view.custom.HappyEditText;

/**
 * Created by dmytro on 10/20/15.
 */
public interface StartSessionView {

    void setPresenter(StartSessionViewPresenter presenter);
    View getStartButton();
    View getEnterSessionNameLayout();
    View getRoot();
    View getStartSessionOkButton();
    View getStartSessionCancelButton();
    HappyEditText getCreateSessionName();
}
