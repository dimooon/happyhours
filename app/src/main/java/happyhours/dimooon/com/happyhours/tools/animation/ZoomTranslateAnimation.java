package happyhours.dimooon.com.happyhours.tools.animation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

public class ZoomTranslateAnimation {

    private Animator mCurrentAnimator;
    private final int shortAnimationDuration;

    private AnimatedViewActionListener listener;

    public interface AnimatedViewActionListener {
        void handleAnimatedViewClick();
    }

    public ZoomTranslateAnimation(Context context,AnimatedViewActionListener listener){
        this.listener = listener;
        shortAnimationDuration = context.getResources().getInteger(android.R.integer.config_shortAnimTime);
    }

    public void zoomImageFromThumb(final View container,final View start,final View finish) {

        start.bringToFront();

        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        final float startScale;

        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        start.getGlobalVisibleRect(startBounds);

        container.getGlobalVisibleRect(finalBounds, globalOffset);

        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);


        if ((float) finalBounds.width() / finalBounds.height()  > (float) startBounds.width() / startBounds.height()) {

            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;

        } else {

            startScale = (float) startBounds.height() / finalBounds.height();

            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;

            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;

        }

        start.setAlpha(0f);
        finish.setVisibility(View.VISIBLE);

        finish.setPivotX(0f);
        finish.setPivotY(0f);

        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(finish, View.X, startBounds.left, finish.getLeft()))
                .with(ObjectAnimator.ofFloat(finish, View.Y, startBounds.top, finish.getTop()))
                .with(ObjectAnimator.ofFloat(finish, View.SCALE_X, startScale, 1f))
                .with(ObjectAnimator.ofFloat(finish, View.SCALE_Y, startScale, 1f));

        set.setDuration(shortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });

        set.start();

        mCurrentAnimator = set;

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(listener!=null){
                    listener.handleAnimatedViewClick();
                }

                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator.ofFloat(finish, View.X, start.getLeft()))
                        .with(ObjectAnimator.ofFloat(finish, View.Y, startBounds.top))
                        .with(ObjectAnimator.ofFloat(finish, View.SCALE_X, 1f, (((float) start.getWidth()) / finish.getWidth())))
                        .with(ObjectAnimator.ofFloat(finish, View.SCALE_Y, 1f, (((float) start.getWidth()) / finish.getWidth())));

                set.setDuration(shortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());

                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        start.setAlpha(1f);
                        finish.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        start.setAlpha(1f);
                        finish.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });
    }

}
