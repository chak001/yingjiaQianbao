package com.example.customview.tabmenu;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.customview.R;
import com.example.customview.util;
import java.util.ArrayList;

/**
 * Created by lvqiu on 2018/10/11.
 */

public class TabsMenu  extends LinearLayout{
    ArrayList<TextView> textViews;
    int num ;
    private int status;
    private final static int FOCUSED=111,IDEL=113;
    private int textsize=15;
    private String[] words=new String[]{"综合排序","通过率最高","下款最快","利率最低"};

    public TabsMenu(Context context) {
        super(context);
        InitView();
    }

    public TabsMenu(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        InitView();
    }

    public TabsMenu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        InitView();
    }

    private void InitView(){
        textViews=new ArrayList<>();
        num=words.length;
        this.setBackgroundColor(getResources().getColor(R.color.white));
        this.setOrientation(HORIZONTAL);
        this.setWeightSum(num);

        for (int i=0;i<num;i++){
            TextView  textView=new TextView(getContext());
            textView.setText(words[i]);
            textView.setTextColor(getResources().getColor(R.color.transparentblack));
            textView.setTextSize(textsize);
            textView.setGravity(Gravity.CENTER);
            textView.setTag(IDEL);
            textView.setBackground(null);
            Rect rect= util.getBounds(textView.getPaint(),words[i]);
            LinearLayout.LayoutParams layoutParams =new LayoutParams(0,rect.height()*2,1);
            textView.setLayoutParams(layoutParams);
            textView.setOnClickListener(new onclickListener());
            textViews.add(textView);
            this.addView(textView);
        }
    }

    public void initView(ArrayList<String> arrayList){
        if (arrayList==null){
            return;
        }
        String[] strings=new String[arrayList.size()];
        for (int i=0;i<arrayList.size();i++){
            strings[i]=arrayList.get(i);
        }

        initView(strings);
    }

    public void initView(String[] words){
        this.words=words;
        num= words.length;
        textViews=new ArrayList<>();

        this.removeAllViews();
        this.setBackgroundColor(getResources().getColor(R.color.white));
        this.setOrientation(HORIZONTAL);
        this.setWeightSum(num);

        for (int i=0;i<num;i++){
            TextView  textView=new TextView(getContext());
            textView.setText(words[i]);
            textView.setTextColor(getResources().getColor(R.color.transparentblack));
            textView.setTextSize(textsize);
            textView.setGravity(Gravity.CENTER);
            textView.setTag(IDEL);
            textView.setBackground(null);
            Rect rect= util.getBounds(textView.getPaint(),words[i]);
            LinearLayout.LayoutParams layoutParams =new LayoutParams(0,rect.height()*2,1);
            textView.setLayoutParams(layoutParams);
            textView.setOnClickListener(new onclickListener());
            textViews.add(textView);
            this.addView(textView);
        }
        textViews.get(0).performClick();
    }


    class onclickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            for (int i=0;i<num;i++){
                changestatus(textViews.get(i),IDEL);
            }
            if ((Integer)v.getTag()==IDEL){
                changestatus((TextView) v,FOCUSED);
                if (clickCallback!=null){
                    clickCallback.click(getPosition(v));
                }
            }
        }
    }


    public int getPosition(View textView){
        if (textViews==null){
            return 0;
        }
        for (int i=0;i<num;i++){
            if ( textView.equals(textViews.get(i)) ) {
                return i;
            }
        }
        return 0;
    }


    private void changestatus(TextView v,int sta){
        if (sta==IDEL){
            v.setTag(IDEL);
            v.setBackground(null);
            v.setTextColor(getResources().getColor(R.color.transparentblack));
        }else if (sta==FOCUSED){
            v.setTag(FOCUSED);
            v.setBackground(getResources().getDrawable(R.drawable.bottom_line));
            v.setTextColor(getResources().getColor(R.color.dot_color));
        }
    }

    public void setCurrentPosition(int position){
        if (textViews!=null && textViews.size()>position) {
            TextView textView = textViews.get(position);
            textView.performClick();
        }
    }

    private ClickCallback clickCallback;

    public void setClickCallback(ClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }
}
