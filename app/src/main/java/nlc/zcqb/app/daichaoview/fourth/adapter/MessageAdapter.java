package nlc.zcqb.app.daichaoview.fourth.adapter;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;

import ncl.zcqb.app.R;
import nlc.zcqb.app.daichaoview.fourth.adapter.viewholder.MessageViewHolder;
import nlc.zcqb.app.daichaoview.fourth.bean.MessageBean;
import nlc.zcqb.app.util.ARouter;
import nlc.zcqb.baselibrary.baseview.BaseTypeAdapter;
import nlc.zcqb.baselibrary.baseview.WebViewActivity;



/**
 * Created by lvqiu on 2017/11/20.
 */

public class MessageAdapter extends BaseTypeAdapter{

    private final static  int MESSAGE=11116;
    ArrayList<MessageBean> messageBeans=new ArrayList<>();

    public MessageAdapter(Context mContext) {
        super(mContext);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder=null;
        View view=null;
        switch (viewType){
            case MESSAGE:
                view= LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item,parent,false);
                viewHolder=new MessageViewHolder(view);
                break;
             default:break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (messageBeans==null || messageBeans.size()==0){
            return;
        }
        MessageViewHolder viewHolder=(MessageViewHolder) holder;
        viewHolder.wrap_mes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString(WebViewActivity.URL,messageBeans.get(position).getId());
                ARouter.jumpIn(mContext,new Bundle(),WebViewActivity.class);
            }
        });
        viewHolder.desc.setText(""+messageBeans.get(position).getContent());
        viewHolder.title.setText(""+messageBeans.get(position).getTitle());
        viewHolder.time.setText(""+messageBeans.get(position).getTime());
    }

    @Override
    public int getItemCount() {
        int totalcount=0;
        if (messageBeans!=null && messageBeans.size()!=0){
            totalcount=totalcount+messageBeans.size();
        }
        return totalcount;
    }


    @Override
    public int getTypeByPosition(int position) {
       return MESSAGE;
    }

    @Override
    public void updateData(Object object) {
        messageBeans= (ArrayList<MessageBean>) object;
        notifyDataSetChanged();
    }

    @Override
    public void loadMoreData(Object object) {
        if (object!=null){
            messageBeans.addAll((Collection<? extends MessageBean>) object);
            notifyDataSetChanged();
        }
    }

    @Override
    public void TypeFactory(Object object,int type, int UpdateType) {

    }


}
