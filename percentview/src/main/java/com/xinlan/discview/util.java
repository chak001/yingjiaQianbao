package com.xinlan.discview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

/**
 * Created by lvqiu on 2018/10/9.
 */

public class util {

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public static   int[] getScreenParams(Context context){
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        float density = dm.density;
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        int[] screenParams=new int[]{width,height};
        return screenParams;
    }

    public static int[] getPosition(View view){
        int[] location=new int[2];
        view.getLocationOnScreen(location);
        Log.d("test", "Screenx--->" + location[0] + "  " + "Screeny--->" + location[1] );
        view.getLocationInWindow(location);
        Log.d("test", "Screenx--->" + location[0] + "  " + "Screeny--->" + location[1] );
        return location;
    }


    public static Rect getBounds(Paint mPaint,String mStr){
        Rect mTextBound;
        mTextBound = new Rect();
        mPaint.getTextBounds(mStr,0,mStr.length(), mTextBound);
        return mTextBound;
    }

}
