package nlc.zcqb.app.util;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;

import ncl.zcqb.app.R;
import nlc.zcqb.baselibrary.baseview.WebViewActivity;

/**
 * Created by lvqiu on 2018/10/25.
 */

public class CheckBoxUtil {

    public static void initView(AppCompatCheckBox checkbox){
        //3，实现部分字体颜色的改变，并能点击
        Context mContext= checkbox.getContext();

        String str_1 = mContext.getResources().getString(R.string.protocalA);
        String str_2 = mContext.getResources().getString(R.string.protocalB);
        //这里无论是使用  SpannableString 还是  SpannableStringBuilder 都一样
        SpannableString ss = new SpannableString(str_2);
        Bundle bundle=new Bundle();
        bundle.putString(WebViewActivity.URL,"");
        bundle.putString(WebViewActivity.TITLE,mContext.getResources().getString(R.string.protocalB));
        WebClickSpan clickSpan = new WebClickSpan(mContext, bundle);
        ss.setSpan(clickSpan, 0, str_2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        checkbox.setText(str_1);
        checkbox.append(ss);
        //必须加这一句，否则就无法被点击
        checkbox.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
