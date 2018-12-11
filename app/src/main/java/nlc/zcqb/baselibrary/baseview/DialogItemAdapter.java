package nlc.zcqb.baselibrary.baseview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ncl.zcqb.app.R;
import nlc.zcqb.baselibrary.basemodel.netRequest.HttpUtil;
import nlc.zcqb.baselibrary.callback.ClickCallback;
import nlc.zcqb.baselibrary.util.Images;

/**
 * Created by lvqiu on 2017/6/12.
 */

public class DialogItemAdapter extends BaseAdapter {

    private ArrayList<String> list;
    private Context mContext;


    public DialogItemAdapter(Context mContext, ArrayList<String> list) {
        this.list=list;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        if (list!=null && list.size()>0){
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }


    @Override
    public long getItemId(int i) {
        return i;
    }

    public void addDate(ArrayList<String> list){
        if (list!=null){
            this.list.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void updateDate(ArrayList<String> list){
        if (list!=null){
            this.list=list;
            notifyDataSetChanged();
        }
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        Viewholder viewholder=null;
        if (view==null){
            view= LayoutInflater.from(mContext).inflate(R.layout.dialog_item,null);
            viewholder=new Viewholder();
            viewholder.textView=(TextView) view.findViewById(R.id.choose);
            view.setTag(viewholder);
        }else {
            viewholder= (Viewholder) view.getTag();
        }
        viewholder.textView.setText(list.get(i));
        viewholder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickCallback!=null){
                    clickCallback.click(i);
                }
            }
        });
        return view;
    }

    public class Viewholder{
        public TextView textView;
    }

    private ClickCallback clickCallback;

    public void setClickCallback(ClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }

}
