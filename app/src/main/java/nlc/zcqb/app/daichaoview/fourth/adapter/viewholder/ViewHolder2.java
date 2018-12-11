package nlc.zcqb.app.daichaoview.fourth.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import ncl.zcqb.app.R;


/**
 * Created by lvqiu on 2017/11/20.
 */

public class ViewHolder2 extends RecyclerView.ViewHolder {
    public RelativeLayout[] relativeLayouts;
    private int[] relativeid={R.id.wrap1,R.id.wrap2,R.id.wrap3,R.id.wrap4,R.id.wrap5,R.id.wrap6,R.id.wrap7};

    public ViewHolder2(View itemView) {
        super(itemView);
        relativeLayouts=new RelativeLayout[7];
        for (int i=0;i<relativeid.length;i++) {
            relativeLayouts[i]= (RelativeLayout) itemView.findViewById(relativeid[i]);
        }
    }
}
