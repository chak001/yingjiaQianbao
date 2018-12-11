package nlc.zcqb.app.daichaoview.fourth.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import com.xinlan.discview.DiscView;

import ncl.zcqb.app.R;

/**
 * Created by lvqiu on 2018/10/14.
 */

public class ResultViewHolder extends RecyclerView.ViewHolder {
    public DiscView discView;

    public ResultViewHolder(View itemView) {
        super(itemView);
        discView=(DiscView) itemView.findViewById(R.id.discview);
    }
}
