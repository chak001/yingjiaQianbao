package nlc.zcqb.app.daichaoview.second.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import ncl.zcqb.app.R;


/**
 * Created by lvqiu on 2017/11/20.
 */

public class ViewHolder1 extends RecyclerView.ViewHolder {
    public FrameLayout rootview;

    public ImageView imageView;
    public TextView title,desc,num;

    public TextView jine,shijian,lilv;
    public ViewHolder1(View itemView) {
        super(itemView);
        rootview=(FrameLayout) itemView.findViewById(R.id.rootview);

        imageView=(ImageView) itemView.findViewById(R.id.user_icon);

        title=(TextView) itemView.findViewById(R.id.nick_name);
        desc=(TextView) itemView.findViewById(R.id.discribetion);
        num=(TextView) itemView.findViewById(R.id.peoplennum);

        jine=(TextView)itemView.findViewById(R.id.moneyrange);
        shijian=(TextView) itemView.findViewById(R.id.quicktime);
        lilv=(TextView) itemView.findViewById(R.id.lilv);

    }
}
