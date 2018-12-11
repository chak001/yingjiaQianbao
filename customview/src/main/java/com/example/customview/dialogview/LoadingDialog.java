package com.example.customview.dialogview;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.customview.R;


/**
 * 自定义dialog
 * @author sfshine
 *
 */
public class LoadingDialog {
    Context context;
    Dialogcallback dialogcallback;
    Dialog dialog;
    TextView  textview;
    int progress;
    String text;
    ProgressBar progressBar;

    /**
     * init the dialog
     * @return
     */
    public LoadingDialog(Context con) {
        this.context = con;
        dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        dialog.setContentView(R.layout.loading_dialog);
        textview = (TextView) dialog.findViewById(R.id.text_progress);
        progressBar=(ProgressBar) dialog.findViewById(R.id.progressbar);
    }

    /**
     * 设定一个interfack接口,使mydialog可以處理activity定義的事情
     * @author sfshine
     *
     */
    public interface Dialogcallback {
        public void dialogdo();
    }
    public void setDialogCallback(Dialogcallback dialogcallback) {
        this.dialogcallback = dialogcallback;
    }


    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        progressBar.setProgress(progress);
    }


    public void setText(String text) {
        this.text = text;
        textview.setText(text);
    }

    public void show() {
        dialog.show();
    }
    public void hide() {
        dialog.hide();
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void dismiss() {

        dialog.dismiss();
        dialog=null;
    }
} 