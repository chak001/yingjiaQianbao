package nlc.zcqb.app.daichaoview.login.viewholder;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import ncl.zcqb.app.R;

/**
 * Created by lvqiu on 2018/10/14.
 */

public class TitleBarViewHolder {
    public ImageView titleBg;
    public ImageView back;
    public TextView titlename;
    public FrameLayout rootview,title_layout;


    public TitleBarViewHolder(Activity activity) {
        rootview=(FrameLayout) activity.findViewById(R.id.rootview_title);
        titleBg=(ImageView) activity.findViewById(R.id.backgroundImage);
        back=(ImageView) activity.findViewById(R.id.back_action);
        titlename=(TextView) activity.findViewById(R.id.title_name);
        title_layout=(FrameLayout)activity.findViewById(R.id.title_layout);
    }

}
