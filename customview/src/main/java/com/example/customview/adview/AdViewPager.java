package com.example.customview.adview;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.example.customview.R;
import com.example.customview.util;
import java.util.ArrayList;

import static android.support.v4.view.ViewPager.SCROLL_STATE_DRAGGING;
import static android.support.v4.view.ViewPager.SCROLL_STATE_IDLE;


/**
 * Created by lvqiu on 2018/10/9.
 */

public class AdViewPager  extends FrameLayout {
    ViewPager viewPager;
    PagerAdapter pagerAdapter;
    LinearLayout dotsWraper;
    ArrayList<ContentBean> beans=new ArrayList<>();
    ArrayList<View> dotViews=new ArrayList<>();
    private  int currentposition=0;
    private static final int REMOVEALL=11111;
    private static final int NEXT=111144;
    private int delayTime=2000;

    private Handler  mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case REMOVEALL:
                    break;
                case NEXT:
                    viewPager.setCurrentItem((currentposition+1),true);
                    break;
                default:break;
            }
        }
    };

    public AdViewPager(@NonNull Context context) {
        super(context);
        InitView();
    }

    public AdViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        InitView();
    }

    public AdViewPager(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        InitView();
    }


    private void InitView(){
        View view= LayoutInflater.from(getContext()).inflate(R.layout.adviewpager_layout,null);
        viewPager=(ViewPager) view.findViewById(R.id.ad_viewpager);
        dotsWraper=(LinearLayout) view.findViewById(R.id.indices_dots);
        this.addView(view);
    }


    public void InitContent(ArrayList<ContentBean> beans){
        if (beans!=null && beans.size()>0){
            this.beans=beans;
            InitViewpager();
            InitDots();
        }
    }

    private void InitViewpager(){

        pagerAdapter = new AdAdapter(beans,getContext());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(beans.size()*2);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mHandler.removeMessages(NEXT);
                changestatus(currentposition,position);
                mHandler.sendEmptyMessageDelayed(NEXT,delayTime);
            }

            /**
             * SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
             * @param state
             */
            @Override
            public void onPageScrollStateChanged(int state) {
                if (state!=android.support.v4.view.ViewPager.SCROLL_STATE_IDLE){
                    mHandler.removeMessages(NEXT);
                }else if (state==SCROLL_STATE_IDLE){
                    mHandler.sendEmptyMessageDelayed(NEXT,delayTime);
                }
            }
        });
        mHandler.removeMessages(NEXT);
        mHandler.sendEmptyMessage(NEXT);
    }

    private void InitDots(){
        dotViews.clear();
        dotsWraper.removeAllViews();
        int part=util.dip2px(getContext(),7);
        for (int i=0;i<beans.size();i++){
            View view=new View(getContext());
            view.setBackgroundResource(R.drawable.dots_bg);
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(part,part);
            layoutParams.setMargins(part,part,part,part);
            view.setLayoutParams(layoutParams);
            dotViews.add(view);
            dotsWraper.addView(view);
        }
        dotViews.get(0).setBackgroundResource(R.drawable.dots_focused_bg);
    }

    private void changestatus(int before,int now){
        currentposition=now;
        before= before%beans.size();
        now= now%beans.size();

        if (before!=now){
            dotViews.get(before).setBackgroundResource(R.drawable.dots_bg);
            dotViews.get(now).setBackgroundResource(R.drawable.dots_focused_bg);
        }
    }


}
