package happyhours.dimooon.com.happyhours.tools.animation;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

import happyhours.dimooon.com.happyhours.tools.FormatUtils;

public class WidthAnimation {


    private static final String TAG = WidthAnimation.class.getSimpleName();

    public void animateIn(View view){
        float[] screenSize = FormatUtils.getScreenSize(view.getContext());
        animate(view, (int) screenSize[0],0);
    }

    public void animateOut(View view){
        float[] screenSize = FormatUtils.getScreenSize(view.getContext());
        animate(view,0, (int) screenSize[0]);
    }

    private void animate(final View view, final int fromX, final int toX){

        final boolean animateOut = toX > fromX;

        Animation animation = new TranslateAnimation(fromX, toX,0, 0);
        animation.setDuration(600);
        animation.setFillAfter(!animateOut);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.bringToFront();
                view.setVisibility(animateOut ? View.GONE: View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(animation);
        view.setVisibility(View.VISIBLE);
        view.bringToFront();
    }
}