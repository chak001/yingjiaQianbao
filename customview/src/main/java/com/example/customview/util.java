package com.example.customview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import java.util.regex.Pattern;

/**
 * Created by lvqiu on 2018/10/9.
 */

public class util {

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /*判断字符串是否仅为数字:*/
    public static boolean isNumeric(String str){

        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobileNums) {
		 /*
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
         * 联通：130、131、132、152、155、156、185、186
         * 电信：133、153、180、189、177（1349卫通）
         * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
         */
//		String telRegex = "[1][3578]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String telRegex = "[1]\\d{10}";// "[1]"代表第1位为数字1 ，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobileNums))
            return false;
        else
            return mobileNums.matches(telRegex);
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
