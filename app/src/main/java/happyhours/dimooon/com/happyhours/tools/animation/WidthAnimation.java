package happyhours.dimooon.com.happyhours.tools.animation;

import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
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

    private void animate(View view,int fromX,int toX){
        Animation animation = new TranslateAnimation(fromX, toX,0, 0);
        animation.setDuration(700);
        animation.setFillAfter(true);
        view.startAnimation(animation);
        view.setVisibility(View.VISIBLE);
        view.bringToFront();
    }

}