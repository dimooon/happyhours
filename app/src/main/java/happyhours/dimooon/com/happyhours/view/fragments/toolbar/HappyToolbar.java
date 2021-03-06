package happyhours.dimooon.com.happyhours.view.fragments.toolbar;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import happyhours.dimooon.com.happyhours.R;
import happyhours.dimooon.com.happyhours.tools.animation.HeightAnimation;

public class HappyToolbar extends Toolbar implements ToolBarView {

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
        ((TextView)titleBar.findViewById(R.id.custom_toolbar_title)).setText(title);
    }

    @Override
    public void changeVisibilityForAddButton(boolean show) {
        titleBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showAddTimerBar(boolean show) {
        addTimerBar.setVisibility(show ? View.VISIBLE : View.GONE);
     }

    @Override
    public void hide() {
        HeightAnimation expandAnimation =  new HeightAnimation(this,HeightAnimation.COLLAPSED_HEIGHT,HeightAnimation.HIDDEN_HEIGHT);
        expandAnimation.setDuration(400);
        this.startAnimation(expandAnimation);
    }

    @Override
    public void show() {
        HeightAnimation expandAnimation =  new HeightAnimation(this,HeightAnimation.HIDDEN_HEIGHT,HeightAnimation.COLLAPSED_HEIGHT);
        expandAnimation.setDuration(400);
        this.startAnimation(expandAnimation);
    }

    @Override
    public void showMyStoryTool() {
        findViewById(R.id.stopSessionButton).setVisibility(View.GONE);
        findViewById(R.id.timersListButton).setVisibility(View.GONE);
    }

    @Override
    public void showMainSessionTool() {
        findViewById(R.id.stopSessionButton).setVisibility(View.VISIBLE);
        findViewById(R.id.timersListButton).setVisibility(View.VISIBLE);
    }
}