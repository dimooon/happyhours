package happyhours.dimooon.com.happyhours.tools.animation;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by dmytro on 10/13/15.
 */
public class ColorUtils {

    public static  int getRandomColor() {
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        return color;
    }

}
