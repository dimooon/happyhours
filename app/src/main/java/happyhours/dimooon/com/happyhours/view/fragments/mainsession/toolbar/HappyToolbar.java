package happyhours.dimooon.com.happyhours.view.fragments.mainsession.toolbar;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import happyhours.dimooon.com.happyhours.R;

public class HappyToolbar extends Toolbar implements ActionToolBar{

    private View titleBar;
    private View addTimerBar;
    private View addNewTimerButton;
    public View toolbarActionBarClose;

    private ToolbarPresenter presenter;

    public HappyToolbar(Context context) {
        super(context);
    }

    public HappyToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HappyToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void setPresenter(ToolbarPresenter presenter) {
        this.presenter = presenter;
    }

    public void initView(){

        titleBar = findViewById(R.id.toolbar_action_bar);
        addTimerBar = findViewById(R.id.toolbar_add_create_timer);

        addNewTimerButton = findViewById(R.id.timersListButton);

        toolbarActionBarClose = findViewById(R.id.toolbar_action_bar_close);
        toolbarActionBarClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (presenter != null) {
                    presenter.collapse();
                };
            }
        });

        addNewTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (presenter != null) {
                    presenter.expand();
                }
            }
        });
    }

    @Override
    public void updateTitle(String title) {
        setTitle(title);
    }

    @Override
    public void changeVisibilityForAddButton(boolean show) {
        titleBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showAddTimerBar(boolean show) {
        addTimerBar.setVisibility(show ? View.VISIBLE: View.GONE);
     }
}