package nlc.zcqb.app.daichaoview.second.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import ncl.zcqb.app.R;

/**
 * Created by lvqiu on 2018/10/14.
 */

public class DKDViewHolder1 extends RecyclerView.ViewHolder {
    public TextView title;
    public ImageView back,logo;
    public RelativeLayout left_func,right_func;
    public ImageView favorite;
    public TextView totalnum,deadline,discribetion,peplenum,companyname;
    public TextView huandai,shijian,lilv;

    public DKDViewHolder1(View itemView) {
        super(itemView);
        left_func=(RelativeLayout) itemView.findViewById(R.id.left_func);
        right_func=(RelativeLayout) itemView.findViewById(R.id.right_func);
        title=(TextView) itemView.findViewById(R.id.title_name);
        back=(ImageView) itemView.findViewById(R.id.back_action);

        favorite=(ImageView) itemView.findViewById(R.id.favorite);

        logo=(ImageView) itemView.findViewById(R.id.user_icon);
        discribetion=(TextView) itemView.findViewById(R.id.discribetion);
        peplenum=(TextView) itemView.findViewById(R.id.peplenum);
        companyname=(TextView) itemView.findViewById(R.id.nick_name);


        totalnum=(TextView) itemView.findViewById(R.id.totalnum);
        deadline=(TextView) itemView.findViewById(R.id.deadline);
        huandai=(TextView) itemView.findViewById(R.id.huandai);
        shijian=(TextView) itemView.findViewById(R.id.shijian);
        lilv=(TextView) itemView.findViewById(R.id.lilv);
    }
}