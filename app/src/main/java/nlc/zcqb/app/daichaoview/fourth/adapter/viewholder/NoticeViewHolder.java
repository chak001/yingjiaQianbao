package nlc.zcqb.app.daichaoview.fourth.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import ncl.zcqb.app.R;

/**
 * Created by lvqiu on 2018/10/14.
 */

public class NoticeViewHolder extends RecyclerView.ViewHolder {
    public TextView jiedaijine,deadline,meiqihuankuan,title,fix;

    public NoticeViewHolder(View itemView) {
        super(itemView);
        jiedaijine=(TextView) itemView.findViewById(R.id.moneyrange);
        deadline=(TextView) itemView.findViewById(R.id.quicktime);
        meiqihuankuan=(TextView) itemView.findViewById(R.id.huankuan);
        title=(TextView) itemView.findViewById(R.id.nick_name);
        fix=(TextView) itemView.findViewById(R.id.fix);
    }
}
