package happyhours.dimooon.com.happyhours.view.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import happyhours.dimooon.com.happyhours.R;
import happyhours.dimooon.com.happyhours.model.database.data.SessionDataProvider;
import happyhours.dimooon.com.happyhours.service.ActivityService;
import happyhours.dimooon.com.happyhours.view.custom.HappyKeyboardView;
import happyhours.dimooon.com.happyhours.view.custom.KeyboardView;
import happyhours.dimooon.com.happyhours.view.custom.KeyboardViewPresenter;
import happyhours.dimooon.com.happyhours.view.fragments.mainsession.session.ISessionView;
import happyhours.dimooon.com.happyhours.view.fragments.mainsession.session.SessionView;
import happyhours.dimooon.com.happyhours.view.fragments.mainsession.session.SessionViewPresenter;
import happyhours.dimooon.com.happyhours.view.fragments.mainsession.startsession.StartNewSessionView;
import happyhours.dimooon.com.happyhours.view.fragments.mainsession.startsession.StartSessionViewPresenter;
import happyhours.dimooon.com.happyhours.view.fragments.toolbar.ToolBarView;
import happyhours.dimooon.com.happyhours.view.fragments.toolbar.ToolbarPresenter;
import happyhours.dimooon.com.happyhours.view.fragments.toolbar.mainsessionaction.MainSessionToolPresenter;
import happyhours.dimooon.com.happyhours.view.fragments.toolbar.mainsessionaction.MainSessionTools;

@SuppressLint("ValidFragment")
public class MainSessionFragment extends Fragment implements SelectableFragment{

    private SessionDataProvider manager;
    private ActivityService service;

    private ToolBarView toolbar;
    private ISessionView sessionView;
    private StartSessionViewPresenter startSessionViewPresenter;

    @SuppressLint("ValidFragment")
    public MainSessionFragment(SessionDataProvider manager,ActivityService service) {
        super();

        this.manager = manager;
        this.service = service;
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

        if(service.sessionExists()){
            startSessionViewPresenter.startSessionWithName(service.getSession());
        }
    }

    private void initView(){

        toolbar = ((ToolBarView) getActivity().findViewById(R.id.toolbar));

        MainSessionTools mainSessionToolsView = new MainSessionTools(getActivity().findViewById(R.id.toolbar_action_bar));
        MainSessionToolPresenter mainSessionToolPresenter = new MainSessionToolPresenter(mainSessionToolsView);
        mainSessionToolsView.setPresenter(mainSessionToolPresenter);

        KeyboardView keyboardView = new HappyKeyboardView(getActivity().findViewById(R.id.keyboardViewLayout));
        KeyboardViewPresenter keyboardViewPresenter = new KeyboardViewPresenter(keyboardView);

        sessionView = (SessionView) getView().findViewById(R.id.sessionView);
        SessionViewPresenter sessionViewPresenter = new SessionViewPresenter(getActivity(), sessionView, manager, keyboardViewPresenter,service);
        sessionView.setPresenter(sessionViewPresenter);

        StartNewSessionView startSessionView = new StartNewSessionView(getView());
        startSessionViewPresenter = new StartSessionViewPresenter(getActivity(),startSessionView,sessionView,mainSessionToolsView,keyboardViewPresenter,manager);
        startSessionView.setPresenter(startSessionViewPresenter);

        ToolbarPresenter toolbarPresenter = new ToolbarPresenter(toolbar,sessionViewPresenter);
        toolbar.setPresenter(toolbarPresenter);

        toolbar.show();

    }

    @Override
    public void onSelected() {
        toolbar.updateTitle("Main Session");
        if(service.sessionExists()&&service.sessionActive()){
            toolbar.showMainSessionTool();
        }
    }
}