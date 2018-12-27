package nlc.zcqb.baselibrary.ItemDecoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by fxl on 2016/8/12.
 */
public class CusItemDecoration extends RecyclerView.ItemDecoration {

    private int space=5;

    public CusItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {


        outRect.right = space;

        outRect.left = space;
    }

}