package com.example.customview.adview;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.customview.HttpUtil;

import java.util.ArrayList;

/**
 * Created by lvqiu on 2018/10/9.
 */

public class AdAdapter extends PagerAdapter {

    private ArrayList<ContentBean> beans;
    private ArrayList<ImageView> imageList;
    private Context mContext;

    public AdAdapter(ArrayList<ContentBean> beans, Context context) {
        this.beans = beans;
        this.mContext=context;
        if (beans!=null && beans.size()>0){
            imageList=new ArrayList<>();
            for (int i=0;i<beans.size();i++){
                imageList.add(new ImageView(mContext));
            }
        }
    }



    // 需要实现以下四个方法
    @Override
    public int getCount() {
        // 获得页面的总数
        return Integer.MAX_VALUE;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        // 判断view和Object对应是否有关联关系
        if (view == object) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (imageList.size()==0){
            return null;
        }

        ImageView imageView=imageList.get(position % imageList.size());
        // 获得相应位置上的view； container view的容器，其实就是viewpage自身,
        // position: viewpager上的位置
        // 给container添加内容
        if (container.equals(imageView.getParent())){
            container.removeView(imageView);
        }
        container.addView(imageView,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        HttpUtil.loadimage(beans.get(position % imageList.size()).getImageurl(),
                imageView,mContext);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // 销毁对应位置上的Object
        // super.destroyItem(container, position, object);
        if (imageList.size()==0){
            return ;
        }
        ImageView imageView=imageList.get(position % imageList.size());
        if (imageView.equals(object)){
            container.removeView((View) object);
        }
    }


    public void updateData(ArrayList<ContentBean> beans){
        this.beans=beans;
        this.notifyDataSetChanged();
    }

}
