package nlc.zcqb.app.daichaoview.fourth.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;

import ncl.zcqb.app.R;

/**
 * Created by lvqiu on 2018/10/14.
 */

public class CompanyViewHolder extends RecyclerView.ViewHolder {
    public FrameLayout frameLayout;

    public CompanyViewHolder(View itemView) {
        super(itemView);
        frameLayout=(FrameLayout) itemView.findViewById(R.id.rootview);
    }
}
