package happyhours.dimooon.com.happyhours.tools;

import android.util.DisplayMetrics;
import android.util.TypedValue;

public class FormatUtils {

    public static int toDip(float value){
        return  (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, new DisplayMetrics());
    }

}
