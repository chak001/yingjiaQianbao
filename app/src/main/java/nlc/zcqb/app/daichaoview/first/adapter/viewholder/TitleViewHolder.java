package nlc.zcqb.app.daichaoview.first.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ncl.zcqb.app.R;


/**
 * Created by lvqiu on 2017/11/20.
 */

public class TitleViewHolder extends RecyclerView.ViewHolder {
    private TextView textView;

    public TitleViewHolder(View itemView,String word) {
        super(itemView);
        textView=(TextView) itemView.findViewById(R.id.title);
        textView.setText(word);
    }
}
