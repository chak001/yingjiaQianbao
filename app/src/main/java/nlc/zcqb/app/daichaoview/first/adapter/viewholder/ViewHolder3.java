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

public class ViewHolder3 extends RecyclerView.ViewHolder {
    public TextView title;
    public ImageView backgroundimage;

    public ViewHolder3(View itemView) {
        super(itemView);
        backgroundimage=(ImageView) itemView.findViewById(R.id.backgroundImage);
        title=(TextView) itemView.findViewById(R.id.title);
    }
}
