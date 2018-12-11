package com.example.customview.filtermenu;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.example.customview.R;
import com.example.customview.util;
import java.util.ArrayList;

public class FilterMenu extends LinearLayout  {
    GridView gridView;
    int[] pos ;
    Rect rect;
    PopupWindow mPopWindow;
    private int status;
    private final static int FOCUSED=111,IDEL=113;
    private final static String lefttext="金额不限";
    private final static String righttext="类型不限";
    TextView leftText,rightText;
    public final static String LEFT="left",RIGHT="right";
    private ArrayList<String> leftlist,rightlist;

    public FilterMenu(Context context) {
        super(context);
        InitViews();
    }

    public FilterMenu(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        InitViews();
    }

    public FilterMenu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        InitViews();
    }


    public void initMenu(ArrayList<String> leftlist,ArrayList<String> rightlist){
        this.leftlist=leftlist;
        this.rightlist=rightlist;
        leftText.setOnClickListener(new Textlistener(this.leftlist,LEFT));
        rightText.setOnClickListener(new Textlistener(this.rightlist,RIGHT));
        leftText.setText(leftlist.get(0));
        rightText.setText(rightlist.get(0));
    }

    public void initMenu(String[] leftlist,String[] rightlist){
        ArrayList<String> temp1=new ArrayList<>();
        for (String t :leftlist) {
            temp1.add(t);
        }
        this.leftlist=temp1;
        ArrayList<String> temp2=new ArrayList<>();
        for (String t :rightlist) {
            temp2.add(t);
        }
        this.rightlist=temp2;
        leftText.setOnClickListener(new Textlistener(this.leftlist,LEFT));
        rightText.setOnClickListener(new Textlistener(this.rightlist,RIGHT));
        leftText.setText(leftlist[0]);
        rightText.setText(rightlist[0]);
    }

    private void InitViews(){
        this.setBackgroundColor(getResources().getColor(R.color.white));


        this.setOrientation(HORIZONTAL);
        this.setGravity(Gravity.CENTER_VERTICAL);
        this.setWeightSum(2);

        leftText=new TextView(getContext());
        leftText.setText(lefttext);
        leftText.setTextColor(getResources().getColor(R.color.transparentblack));
        leftText.setTextSize(15);
        leftText.setGravity(Gravity.CENTER);
        leftText.setTag(IDEL);
        Rect rect= util.getBounds(leftText.getPaint(),lefttext);
        LinearLayout.LayoutParams layoutParams1=new LayoutParams(0,rect.height()*2,1);
        leftText.setLayoutParams(layoutParams1);

        rightText=new TextView(getContext());
        rightText.setText(righttext);
        rightText.setTextSize(15);
        rightText.setGravity(Gravity.CENTER);
        rightText.setTextColor(getResources().getColor(R.color.transparentblack));
        rightText.setTag(IDEL);
        LinearLayout.LayoutParams layoutParams2=new LayoutParams(0,rect.height()*2,1);
        rightText.setLayoutParams(layoutParams2);

        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                pos=util.getPosition(FilterMenu.this);
            }
        });

        this.addView(leftText,layoutParams1);
        this.addView(rightText,layoutParams2);

        View view=new View(getContext());
        view.setBackgroundColor(getResources().getColor(R.color.linecolor));
        view.setLayoutParams(new LinearLayoutCompat.LayoutParams(util.dip2px(getContext(),1), (int) (rect.height()*1.4)));
        this.addView(view,1);

        leftText.setOnClickListener(new Textlistener(leftlist,LEFT));
        rightText.setOnClickListener(new Textlistener(rightlist,RIGHT));
    }


    private void showPopupWindow(ArrayList<String> strings,String tag) {

        //设置contentView
        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.popwindow_layout, null);
        mPopWindow = new PopupWindow(contentView,
                LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(contentView);
        mPopWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.popwindow_bg));
        mPopWindow.setFocusable(true);//获取焦点
        mPopWindow.setOutsideTouchable(true);//获取外部触摸事件
        mPopWindow.setTouchable(true);//能够响应触摸事件

        //设置各个控件的点击响应
        gridView= (GridView) contentView.findViewById(R.id.gridview);
        gridView.setNumColumns(3);
        MenuAdapter adapter=new  MenuAdapter(getContext(),strings,tag);
        adapter.setListener(new MenuAdapter.onclicklistener() {
            @Override
            public void click(String s, String tag) {
                choose(s,tag);
                if (clickCallback!=null){
                    clickCallback.click(getposition(s,tag),tag);
                }
            }
        });
        gridView.setAdapter(adapter);
        //显示PopupWindow
        mPopWindow.showAtLocation(this, Gravity.TOP, pos[0], pos[1]+getMeasuredHeight());
        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                defaultstyle(true);
            }
        });
    }


    private void hidenPopWindow(){
        if (mPopWindow!=null && mPopWindow.isShowing()){
            mPopWindow.dismiss();
        }
    }

    class Textlistener  implements View.OnClickListener{
        private ArrayList<String> strings;
        private String tag;

        public Textlistener(ArrayList<String> strings,String tag) {
            this.strings = strings;
            this.tag=tag;
        }

        @Override
        public void onClick(View v) {
            defaultstyle(false);
            if ((Integer)v.getTag()==IDEL){
                hidenPopWindow();
                showPopupWindow(strings,tag);
                changestatus(v,FOCUSED);
            } else if ((Integer)v.getTag()==FOCUSED){
                hidenPopWindow();
                changestatus(v,IDEL);
            }
        }
    }

    private void changestatus(View view,int sta){
        view.setTag(sta);
        TextView textView=(TextView) view;
        if (sta==IDEL){
            textView.setTextColor(getResources().getColor(R.color.transparentblack));
        }else {
            textView.setTextColor(getResources().getColor(R.color.dot_color));
        }

    }

    private void defaultstyle(boolean fixstatus){
        leftText.setTextColor(getResources().getColor(R.color.transparentblack));
        rightText.setTextColor(getResources().getColor(R.color.transparentblack));
        if (fixstatus){
            leftText.setTag(IDEL);
            rightText.setTag(IDEL);
        }
    }

    private void choose(String str,String tag){
        hidenPopWindow();
        if (tag.equalsIgnoreCase(LEFT)){
            leftText.setTextColor(getResources().getColor(R.color.transparentblack));
            leftText.setText(str);
        }else {
            rightText.setTextColor(getResources().getColor(R.color.transparentblack));
            rightText.setText(str);
        }
    }

    public int getposition(String str,String tag){
        if (tag.equalsIgnoreCase(LEFT)){
            for (int i=0;i<leftlist.size();i++) {
                if (leftlist.get(i).equalsIgnoreCase(str)){
                    return i;
                }
            }
        }else {
            for (int i=0;i<rightlist.size();i++) {
                if (rightlist.get(i).equalsIgnoreCase(str)){
                    return i;
                }
            }
        }
        return 0;
    }

    private ClickCallback clickCallback;

    public void setClickCallback(ClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }

}
