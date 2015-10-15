package happyhours.dimooon.com.happyhours.tools;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

import happyhours.dimooon.com.happyhours.model.database.facade.interfaces.Activity;

public class FormatUtils {

    public static int toDip(Context context,float value){

        Resources resource = context.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, resource.getDisplayMetrics());
    }

    public static float[] getScreenSize(Context context){

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return  new float[]{metrics.widthPixels,metrics.heightPixels};
    }

}
