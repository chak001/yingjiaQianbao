package nlc.zcqb.baselibrary.ItemDecoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by fxl on 2016/8/12.
 */
public class CirclePictureDecoration extends RecyclerView.ItemDecoration {

    private int space=5;

    public CirclePictureDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;
        outRect.top = space;
    }

}