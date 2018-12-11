package nlc.zcqb.app.daichaoview.fourth.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ncl.zcqb.app.R;

/**
 * Created by lvqiu on 2018/10/14.
 */

public class XinYongViewHolder1 extends RecyclerView.ViewHolder {
    public TextView title;
    public ImageView back;
    public TextView jigouduijie,shujuduibi,blacklist;

    public XinYongViewHolder1(View itemView) {
        super(itemView);
        title=(TextView) itemView.findViewById(R.id.title_name);
        back=(ImageView) itemView.findViewById(R.id.back_action);

        jigouduijie=(TextView)itemView.findViewById(R.id.jigouduijie);
        shujuduibi=(TextView)itemView.findViewById(R.id.shujuduibi);
        blacklist=(TextView) itemView.findViewById(R.id.blacklist);
    }
}


