package dev.mirror.library.android.util;

import android.app.Activity;
import android.util.DisplayMetrics;

/**
 * Created by 王沛栋 on 2016/3/1.
 */
public class ScreenUtil {
    public static int getScreenWidth(Activity context){
        // 获取屏幕密度（方法1）
        DisplayMetrics metric = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;  // 屏幕宽度（像素）

        return width;
//        int height = metric.heightPixels;  // 屏幕高度（像素）
//        float density = metric.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
//        int densityDpi = metric.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）


//
//
//        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
//        Display display = wm.getDefaultDisplay();
////        disp
////        Log.i(tag, "屏幕尺寸1: 宽度 = " + display.getWidth() + "高度 = :" + display.getHeight()
////        );</span>
//
//        int screenWidth  = context.getWindowManager().getDefaultDisplay().getWidth();       // 屏幕宽（像素，如：480px）
//        int screenHeight = context.getWindowManager().getDefaultDisplay()getWindowManager.getHeight();
    }
}
