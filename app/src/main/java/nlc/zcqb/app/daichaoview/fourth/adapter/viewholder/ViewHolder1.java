package nlc.zcqb.app.daichaoview.fourth.adapter.viewholder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.customview.util;

import ncl.zcqb.app.R;

/**
 * Created by lvqiu on 2017/11/20.
 */

public class ViewHolder1 extends RecyclerView.ViewHolder {
    public TextView nickname,desribe;
    public ImageView usericon;
    public FrameLayout relativeLayout;
    public RelativeLayout wrap_user;
    public TextView favorite,history;
    public ImageView backgroundimage;
    public RelativeLayout favorate_wrap,history_wrap;


    public ViewHolder1(View itemView) {
        super(itemView);
        Context mContext= itemView.getContext();
        backgroundimage=(ImageView) itemView.findViewById(R.id.backgroundImage);
        nickname=(TextView) itemView.findViewById(R.id.nick_name);
        desribe=(TextView) itemView.findViewById(R.id.discribetion);
        usericon=(ImageView) itemView.findViewById(R.id.user_icon);
        relativeLayout=(FrameLayout) itemView.findViewById(R.id.rootview);
        wrap_user=(RelativeLayout) itemView.findViewById(R.id.wrap_user);

        favorite=(TextView)itemView.findViewById(R.id.favorite);
        history=(TextView)itemView.findViewById(R.id.history);

        Drawable drawable=mContext.getResources().getDrawable(R.mipmap.shoucang);
        drawable.setBounds(0,0, util.dip2px(mContext,21),util.dip2px(mContext,21));//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        favorite.setCompoundDrawables(drawable,null,null,null);//只放左边

        Drawable drawable1=mContext.getResources().getDrawable(R.mipmap.zuijinliulan);
        drawable1.setBounds(0,0, util.dip2px(mContext,21),util.dip2px(mContext,21));//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        history.setCompoundDrawables(drawable1,null,null,null);//只放左边

        favorate_wrap=(RelativeLayout) itemView.findViewById(R.id.favorite_wrap);
        history_wrap=(RelativeLayout) itemView.findViewById(R.id.history_wrap);
    }
}
