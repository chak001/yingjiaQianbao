package nlc.zcqb.app.util;

import android.content.Context;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import ncl.zcqb.app.R;
import nlc.zcqb.baselibrary.baseview.WebViewActivity;

/**
 * 这个类 实际上和第一种改变颜色的方法差不多，只不过 那是个专门改变颜色的Span，这是个专门负责点击处理的Span
 * @author Administrator
 */
public class WebClickSpan extends ClickableSpan {
    private Context context;
    private Bundle bundle;

    public WebClickSpan(Context context, Bundle bundle)
    {
        this.context = context;
        this.bundle = bundle;
    }

    //在这里设置字体的大小，等待各种属性
    public void updateDrawState(TextPaint ds) {
        ds.setColor(context.getResources().getColor(R.color.dot_color));
    }

    @Override
    public void onClick(View widget) {
        ARouter.jumpIn(context,bundle,WebViewActivity.class);
    }
}
