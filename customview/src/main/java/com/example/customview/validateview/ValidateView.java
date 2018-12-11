package com.example.customview.validateview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.customview.R;
import com.example.customview.util;


/**
 * Created by lvqiu on 2018/10/10.
 */

public class ValidateView extends LinearLayout {
    private int totalTime=60*1000;
    private Integer status;
    private final static int IDEL=11111;
    private final static int COUNTING=11112;
    private EditText editText;
    private TextView textView;
    private final String TAG="ValidateView";

    private int maxLength=6;
    private final int part=util.dip2px(getContext(),5);
    private GetCodeFactory factory;
    private GetTel getTel;

    public ValidateView(Context context) {
        super(context);
        InitView();
    }

    public ValidateView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        InitView();
    }

    public ValidateView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        InitView();
    }


    private void InitView(){
        status=IDEL;

        this.setOrientation(HORIZONTAL);
        this.setGravity(Gravity.CENTER_VERTICAL);

        editText=new EditText(getContext());
//        editText.setBackground(null);
        editText.setHint(R.string.code_hint);
        editText.setInputType( InputType.TYPE_CLASS_NUMBER);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxLength)});

        textView=new TextView(getContext());
        textView.setSingleLine();
        textView.setTextSize(15);
        textView.setText(R.string.get_code);
        textView.setBackgroundResource(R.drawable.code_bg);
        textView.setTextColor(getContext().getResources().getColor(R.color.dot_color));
        textView.setGravity(Gravity.CENTER);

        Rect rect=new Rect();
        Paint paint=textView.getPaint();
        paint.getTextBounds("获取验证码",0,"获取验证码".length(),rect);

        //view加载完成时回调
        textView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                int width=ValidateView.this.getMeasuredWidth();
                width=width-textView.getMeasuredWidth()-3*part;
                ValidateView.this.addView(editText,0,new LinearLayout.LayoutParams(width, ViewGroup.LayoutParams.WRAP_CONTENT));
                textView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(rect.width()+2*part,rect.height()*2);
        layoutParams.setMargins(part,part,part,part);
        this.addView(textView,layoutParams);
        textView.setTag(status);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switch ((Integer) v.getTag()){
                    case IDEL:
                        if (factory!=null && getTel!=null){
                            String temp=getTel.getTel();
                            if (util.isMobileNO(temp)){
                                factory.getCode(getTel.getTel(),null,null);
                                changestatus(COUNTING);
                                startAnimation();
                            }else {
                                Toast.makeText(getContext(),"电话格式错误！",Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(getContext(),"电话为空！",Toast.LENGTH_SHORT).show();
                        }

                        break ;
                    case COUNTING:
//                        Toast.makeText(getContext(),"倒计时未完成，请稍后！",Toast.LENGTH_SHORT).show();
                        Log.e(TAG,"正在等待！");
                        break;
                    default:break;
                }
            }
        });
    }

    private void changestatus(int sta){
        if (sta==IDEL){
            textView.setBackgroundResource(R.drawable.code_bg);
            textView.setTextColor(getContext().getResources().getColor(R.color.dot_color));
            textView.setText(R.string.get_code);
        }else if (sta==COUNTING){
            textView.setBackgroundResource(R.drawable.code_gray_bg);
            textView.setTextColor(getContext().getResources().getColor(R.color.white));
            textView.setText(R.string.wait_code);
        }
        textView.setTag(sta);
        status=sta;
    }

    @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
    private void startAnimation(){
        Toast.makeText(getContext(),"正在获取验证码，请稍后！",Toast.LENGTH_SHORT).show();
        ValueAnimator valueAnimator=new ValueAnimator();
        valueAnimator.setDuration(totalTime);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setIntValues(totalTime,0);
        valueAnimator.setRepeatCount(0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int current= (int) animation.getAnimatedValue();
                textView.setText("稍等"+current/1000+"秒");
            }
        });
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.e(TAG,"onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.e(TAG,"onAnimationEnd");
                changestatus(IDEL);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        valueAnimator.start();
    }

    String tempcode="";
    public void setCode(String code){
        if (code!=null && !code.equalsIgnoreCase("") && util.isNumeric(code) ) {
            editText.setText(code);
            tempcode=code;
        }
    }

    public String getCode(){
        if (tempcode==null|| tempcode.length()<2){
            tempcode= editText.getText().toString();
        }
        return tempcode;
    }

    public boolean isRight(String code){
        String mCode=editText.getText().toString();
        if (mCode.length()==0){
            Toast.makeText(getContext(),"还未填写验证码！",Toast.LENGTH_SHORT).show();
        }else if (mCode.equalsIgnoreCase(code)){
            return true;
        }else {
            Toast.makeText(getContext(),"输入验证码错误！",Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public void setGetTel(GetTel getTel) {
        this.getTel = getTel;
    }

    public void setFactory(GetCodeFactory factory) {
        this.factory = factory;
    }


}
