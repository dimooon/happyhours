package happyhours.dimooon.com.happyhours.view.fragments.mainsession.toolbar;

import android.view.View;
import android.view.animation.Animation;

import happyhours.dimooon.com.happyhours.tools.animation.HeightAnimation;
import happyhours.dimooon.com.happyhours.view.fragments.mainsession.session.SessionViewPresenter;

public class ToolbarPresenter {

    private ActionToolBar toolBar;
    private SessionViewPresenter sessionViewPresenter;

    public ToolbarPresenter(ActionToolBar toolBar,SessionViewPresenter sessionViewPresenter) {
        this.toolBar = toolBar;
        this.sessionViewPresenter = sessionViewPresenter;

    }

    public void setTitle(String title){
        toolBar.updateTitle(title);
    }

    public void expand(){

        HeightAnimation anim = new HeightAnimation((View) toolBar,toolBar.getHeight(),HeightAnimation.EXPANDED_HEIGHT);
        anim.setDuration(400);

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                sessionViewPresenter.showTimersView();
                toolBar.changeVisibilityForAddButton(false);
                toolBar.showAddTimerBar(true);
                ((HappyToolbar)toolBar).toolbarActionBarClose.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ((View)toolBar).startAnimation(anim);
    }

    public void collapse(){

        HeightAnimation anim = new HeightAnimation((View) toolBar,HeightAnimation.EXPANDED_HEIGHT,HeightAnimation.COLLAPSED_HEIGHT);
        anim.setDuration(400);

        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                toolBar.changeVisibilityForAddButton(true);
                toolBar.showAddTimerBar(false);
                ((HappyToolbar)toolBar).toolbarActionBarClose.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ((View)toolBar).startAnimation(anim);
    }

}
