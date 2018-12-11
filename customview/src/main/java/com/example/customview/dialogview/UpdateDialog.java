package com.example.customview.dialogview;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.customview.R;

/**
 * 自定义dialog
 * @author sfshine
 *
 */
public class UpdateDialog {
    Context context;
    Dialogcallback dialogcallback;
    Dialog dialog;
    TextView sure,text,version;
    ImageView close;
    FrameLayout background;

    /**
     * init the dialog
     * @return
     */
    public UpdateDialog(Context con) {
        this.context = con;
        dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        dialog.setContentView(R.layout.update_dialog);
        text = (TextView) dialog.findViewById(R.id.update_content);
        background=(FrameLayout) dialog.findViewById(R.id.sure);
        sure = (TextView) dialog.findViewById(R.id.choice);
        version = (TextView) dialog.findViewById(R.id.version);
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                dialogcallback.dialogdo();
            }
        });
        close=(ImageView) dialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
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
    /**
     * @category Set The Content of the TextView
     * */
    public void setContent(String content) {
        text.setText(content);
    }

    /**
     * Get the Text of the EditText
     * */
    public void setVersion(String Netversion) {
       version.setText(""+Netversion);
    }

    public  void  setchoice(String choice){
        sure.setText(""+choice);
    }

    public void show() {
        dialog.show();
    }
    public void hide() {
        dialog.hide();
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void dismiss() {
        background.setBackground(null);;
        dialog.dismiss();
        dialog=null;
    }
} 