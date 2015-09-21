package happyhours.dimooon.com.happyhours.view.custom.toolbar;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;

import happyhours.dimooon.com.happyhours.R;
import happyhours.dimooon.com.happyhours.tools.animation.HeightAnimation;
import happyhours.dimooon.com.happyhours.view.session.SessionView;

public class HappyToolbar extends Toolbar implements ActionToolBar{

    private View titleBar;
    private View addTimerBar;
    private View root;
    private View addNewTimerButton;
    private View toolbar_action_bar_close;
    private SessionView sessionView;

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
    public void initView(Activity activity){
        root = findViewById(R.id.toolbar);

        titleBar = findViewById(R.id.toolbar_action_bar);
        addTimerBar = findViewById(R.id.toolbar_add_create_timer);

        addNewTimerButton = findViewById(R.id.timersListButton);

        toolbar_action_bar_close = findViewById(R.id.toolbar_action_bar_close);

        toolbar_action_bar_close.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                collapse();
            }
        });

        addNewTimerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expand();
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
    public void addView() {

    }

    @Override
    public void expand(){

        HeightAnimation anim = new HeightAnimation(this,this.getHeight(),this.getHeight() + this.getHeight()*4);
        anim.setDuration(400);

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                sessionView.showTimersView();
                changeVisibilityForAddButton(false);
                showAddTimerBar(true);
                toolbar_action_bar_close.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        this.startAnimation(anim);
    }

    @Override
    public void collapse(){

        HeightAnimation anim = new HeightAnimation(this,this.getHeight() + this.getHeight(),this.getHeight()/4);
        anim.setDuration(400);

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                changeVisibilityForAddButton(true);
                showAddTimerBar(false);
                toolbar_action_bar_close.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        this.startAnimation(anim);
    }

    @Override
    public void showAddTimerBar(boolean show) {
        addTimerBar.setVisibility(show ? View.VISIBLE: View.GONE);
     }

    @Override
    public void setSessionView(SessionView sessionView) {
        this.sessionView = sessionView;
    }

}