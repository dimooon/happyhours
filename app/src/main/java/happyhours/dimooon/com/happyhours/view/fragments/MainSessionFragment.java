package happyhours.dimooon.com.happyhours.view.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import happyhours.dimooon.com.happyhours.R;
import happyhours.dimooon.com.happyhours.model.database.manager.SessionManager;
import happyhours.dimooon.com.happyhours.view.custom.HappyKeyboardView;
import happyhours.dimooon.com.happyhours.view.custom.KeyboardView;
import happyhours.dimooon.com.happyhours.view.custom.KeyboardViewPresenter;
import happyhours.dimooon.com.happyhours.view.fragments.mainsession.session.ISessionView;
import happyhours.dimooon.com.happyhours.view.fragments.mainsession.session.SessionView;
import happyhours.dimooon.com.happyhours.view.fragments.mainsession.session.SessionViewPresenter;
import happyhours.dimooon.com.happyhours.view.fragments.mainsession.startsession.StartNewSessionView;
import happyhours.dimooon.com.happyhours.view.fragments.mainsession.startsession.StartSessionViewPresenter;
import happyhours.dimooon.com.happyhours.view.fragments.toolbar.ActionToolBar;
import happyhours.dimooon.com.happyhours.view.fragments.toolbar.ToolbarPresenter;
import happyhours.dimooon.com.happyhours.view.fragments.toolbar.mainsessionaction.MainSessionToolPresenter;
import happyhours.dimooon.com.happyhours.view.fragments.toolbar.mainsessionaction.MainSessionTools;

@SuppressLint("ValidFragment")
public class MainSessionFragment extends Fragment implements SelectableFragment{

    private SessionManager manager;

    private ActionToolBar toolbar;
    private ISessionView sessionView;

    @SuppressLint("ValidFragment")
    public MainSessionFragment(SessionManager manager) {
        super();

        this.manager = manager;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        onSelected();
    }

    private void initView(){

        toolbar = ((ActionToolBar) getActivity().findViewById(R.id.toolbar));

        MainSessionTools mainSessionToolsView = new MainSessionTools(getActivity().findViewById(R.id.toolbar_action_bar));
        MainSessionToolPresenter mainSessionToolPresenter = new MainSessionToolPresenter(mainSessionToolsView);
        mainSessionToolsView.setPresenter(mainSessionToolPresenter);

        KeyboardView keyboardView = new HappyKeyboardView(getActivity().findViewById(R.id.keyboardViewLayout));
        KeyboardViewPresenter keyboardViewPresenter = new KeyboardViewPresenter(keyboardView);

        sessionView = (SessionView) getView().findViewById(R.id.sessionView);
        SessionViewPresenter sessionViewPresenter = new SessionViewPresenter(getActivity(), sessionView, manager, keyboardViewPresenter);
        sessionView.setPresenter(sessionViewPresenter);

        StartNewSessionView startSessionView = new StartNewSessionView(getView());
        StartSessionViewPresenter startSessionViewPresenter = new StartSessionViewPresenter(getActivity(),startSessionView,sessionView,mainSessionToolsView,keyboardViewPresenter,manager);
        startSessionView.setPresenter(startSessionViewPresenter);

        ToolbarPresenter toolbarPresenter = new ToolbarPresenter(toolbar,sessionViewPresenter);
        toolbar.setPresenter(toolbarPresenter);

        toolbar.show();
    }

    @Override
    public void onSelected() {
        toolbar.updateTitle("Main Session");
        if(sessionView.getPresenter().isSessionStarted()){
            toolbar.showMainSessionTool();
        }
    }
}