package happyhours.dimooon.com.happyhours.view.fragments.toolbar.mainsessionaction;

import android.view.View;

import happyhours.dimooon.com.happyhours.R;

/**
 * Created by dmytro on 10/20/15.
 */
public class MainSessionTools implements MainSessionToolsView{

    private MainSessionToolPresenter presenter;
    private final View addNewTimerButton;
    private final View stopButton;
    private View root;

    public MainSessionTools(View root) {
        this.root = root;

        stopButton = root.findViewById(R.id.stopSessionButton);
        addNewTimerButton = root.findViewById(R.id.timersListButton);

    }


    @Override
    public void setPresenter(MainSessionToolPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public View getStopButton() {
        return this.stopButton;
    }

    @Override
    public View getAddNewTimerButton() {
        return this.addNewTimerButton;
    }
}
