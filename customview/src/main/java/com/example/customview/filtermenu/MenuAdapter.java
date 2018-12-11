package com.example.customview.filtermenu;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.customview.R;

import java.util.ArrayList;

public class MenuAdapter extends BaseAdapter {
    private ArrayList<String> mNameList = new ArrayList<String>();
    private LayoutInflater mInflater;
    private Context mContext;
    private String tag;


    public MenuAdapter(Context context, ArrayList<String> nameList,String tag) {
        mNameList = nameList;
        mContext = context;
        mInflater = LayoutInflater.from(context);
        this.tag=tag;
    }

    public int getCount() {
        return mNameList.size();
    }

    public Object getItem(int position) {
        return mNameList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ItemViewTag viewTag;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.gridview_item, null);
            TextView textView= (TextView) convertView.findViewById(R.id.text);
            viewTag=new ItemViewTag(textView);
            convertView.setTag(viewTag);
        } else {
            viewTag = (ItemViewTag) convertView.getTag();
        }

        // set name
        viewTag.mName.setText(mNameList.get(position));
        viewTag.mName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.click(mNameList.get(position),tag);
                }
            }
        });

        return convertView;
    }


    class ItemViewTag
    {
        protected TextView mName;

        /**
         * The constructor to construct a navigation nlc.zcqb.weiyiliao.view tag
         *
         * @param name
         *            the name nlc.zcqb.weiyiliao.view of the item

         */
        public ItemViewTag(TextView name)
        {
            this.mName = name;
        }
    }

    private onclicklistener listener;
    interface onclicklistener{
        void click(String s, String tag);
    }

    public void setListener(onclicklistener onclicklistener){
        this.listener=onclicklistener;
    }
}