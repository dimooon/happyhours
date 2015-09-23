package happyhours.dimooon.com.happyhours.view.fragments.mainsession.session;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import happyhours.dimooon.com.happyhours.R;
import happyhours.dimooon.com.happyhours.view.custom.TimeProgressBar;

public class SessionView extends LinearLayout implements ISessionView{

    private SessionViewPresenter presenter;

    public SessionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(getContext(), R.layout.view_session, this);
    }

    public SessionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(getContext(), R.layout.view_session, this);
    }

    @Override
    public void setPresenter(SessionViewPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public TextView getCaption(){
        return ((TextView) findViewById(R.id.caption));
    }

    @Override
    public TimeProgressBar getMainProgressBar(){
        return (TimeProgressBar) findViewById(R.id.sessionMainProgress);
    }

    @Override
    public RecyclerView getSessionTimersList(){
        return  (RecyclerView) findViewById(R.id.sessionTimersList);
    }

    @Override
    public View getRootView(){
        return this;
    }
}