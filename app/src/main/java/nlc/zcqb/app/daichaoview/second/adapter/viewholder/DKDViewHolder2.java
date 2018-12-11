package nlc.zcqb.app.daichaoview.second.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ncl.zcqb.app.R;

/**
 * Created by lvqiu on 2018/10/14.
 */

public class DKDViewHolder2 extends RecyclerView.ViewHolder {
    public TextView subTitle;
    public TextView content;

    public DKDViewHolder2(View itemView) {
        super(itemView);
        subTitle=(TextView) itemView.findViewById(R.id.title);
        content=(TextView) itemView.findViewById(R.id.text_context);
    }
}