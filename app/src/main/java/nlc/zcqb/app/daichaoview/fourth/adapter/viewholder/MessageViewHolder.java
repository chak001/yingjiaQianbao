package nlc.zcqb.app.daichaoview.fourth.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import ncl.zcqb.app.R;

/**
 * Created by lvqiu on 2018/10/14.
 */

public class MessageViewHolder extends RecyclerView.ViewHolder {
    public TextView title,desc,time;
    public LinearLayout wrap_mes;

    public MessageViewHolder(View itemView) {
        super(itemView);
        wrap_mes=(LinearLayout) itemView.findViewById(R.id.wrap_mes);
        title=(TextView) itemView.findViewById(R.id.title);
        desc=(TextView) itemView.findViewById(R.id.discribetion);
        time=(TextView) itemView.findViewById(R.id.time);
    }
}
