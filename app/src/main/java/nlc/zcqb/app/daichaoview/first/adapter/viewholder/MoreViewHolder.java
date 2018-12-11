package nlc.zcqb.app.daichaoview.first.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ncl.zcqb.app.R;


/**
 * Created by lvqiu on 2017/11/20.
 */

public class MoreViewHolder extends RecyclerView.ViewHolder {
    public LinearLayout more;
    public String tag;

    public MoreViewHolder(View itemView,String tag) {
        super(itemView);
        this.tag=tag;
        more=(LinearLayout) itemView.findViewById(R.id.more_linearLayout);

    }
}
