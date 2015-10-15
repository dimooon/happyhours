package happyhours.dimooon.com.happyhours.tools.animation;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import happyhours.dimooon.com.happyhours.tools.FormatUtils;

public class HeightAnimation extends Animation {
    protected final int originalHeight;
    protected final View view;
    protected float perValue;

    public static int HIDDEN_HEIGHT = 0;
    public static int COLLAPSED_HEIGHT = 60;
    public static int EXPANDED_HEIGHT = COLLAPSED_HEIGHT * 4;

    public HeightAnimation(View view, int fromHeight, int toHeight) {
        this.view = view;
        this.originalHeight = FormatUtils.toDip(view.getContext(),fromHeight);
        this.perValue = (FormatUtils.toDip(view.getContext(),toHeight) - FormatUtils.toDip(view.getContext(),fromHeight));
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        view.getLayoutParams().height = (int) (originalHeight + perValue * interpolatedTime);
        view.requestLayout();
    }

    @Override
    public boolean willChangeBounds() {
        return true;
    }
}