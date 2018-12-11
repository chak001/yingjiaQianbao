package nlc.zcqb.baselibrary.baseview;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import ncl.zcqb.app.R;
import nlc.zcqb.app.daichaoview.login.viewholder.TitleBarViewHolder;

public class WebViewActivity extends BaseActivity implements View.OnClickListener {
    private TitleBarViewHolder viewHolder;
    private WebView mWebView;
    private ProgressBar mBar;
    private String url;
    private String title;
    public final static String URL="URL";
    public final static String TITLE="TITLE";


    @Override
    public int getLayoutId() {
        return R.layout.activity_webview;
    }

    @Override
    public void InitMenu() {
        viewHolder =new TitleBarViewHolder(this);

        viewHolder.back.setVisibility(View.VISIBLE);
        viewHolder.back.setImageResource(R.mipmap.back_gray);
        viewHolder.back.setOnClickListener(this);
        viewHolder.titlename.setText(title);
        viewHolder.titleBg.setImageDrawable(null);
        viewHolder.titleBg.setBackgroundResource(R.color.white);
        viewHolder.titlename.setTextColor(getResources().getColor(R.color.black));


        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            changeStatusBarTextColor(true);
        }else {
            viewHolder.title_layout.setBackgroundColor(getResources().getColor(R.color.white));
            viewHolder.rootview.setBackgroundColor(getResources().getColor(R.color.hintcolor));
        }
    }

    @Override
    public void InitOthers() {
        initView();
        initWebView();
    }

    @Override
    public void parseIntent() {
        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            url=bundle.getString(URL,"");
            title=bundle.getString(TITLE,"");
        }
    }


    private void initView() {
        mBar=(ProgressBar) findViewById(R.id.process_bar);
        mWebView = (WebView) findViewById(R.id.webview);
    }

    private void initWebView() {
        WebSettings settings = mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        mWebView.loadUrl(url);

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    mBar.setVisibility(View.GONE);
                } else {
                    mBar.setVisibility(View.VISIBLE);
                    mBar.setProgress(newProgress);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (title != null) {
                    viewHolder.titlename.setText(title);
                }
            }
        });

        /**
         * 监听WebView的加载状态    分别为 ： 加载的 前 中 后期
         * */
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String title = view.getTitle();
                if (!TextUtils.isEmpty(title)) {
                    viewHolder.titlename.setText(title);
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //本应该加载的H5静态界面
                mWebView.loadUrl(url);
                return true;
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back_action:
                onBackPressed();
            default:
                break;
        }
    }



    @Override
    public void onDestroy() {
        if( mWebView!=null) {

            // 如果先调用destroy()方法，则会命中if (isDestroyed()) return;这一行代码，需要先onDetachedFromWindow()，再
            // destory()
            ViewParent parent = mWebView.getParent();
            if (parent != null) {
                ((ViewGroup) parent).removeView(mWebView);
            }

            mWebView.stopLoading();
            // 退出时调用此方法，移除绑定的服务，否则某些特定系统会报错
            mWebView.getSettings().setJavaScriptEnabled(false);
            mWebView.clearHistory();
            mWebView.clearView();
            mWebView.removeAllViews();
            mWebView.destroy();

        }
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        } else {
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }


}