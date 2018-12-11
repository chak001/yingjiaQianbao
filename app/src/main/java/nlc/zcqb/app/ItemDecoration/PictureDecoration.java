package nlc.zcqb.app.ItemDecoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by fxl on 2016/8/12.
 */
public class PictureDecoration extends RecyclerView.ItemDecoration {


    int pageMargin =10;
    int sideVisibleWidth=50;
    public static int PageConsumeX=0;

    public PictureDecoration(int space) {
        this.sideVisibleWidth = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect,view,parent,state);
        int itemnewWidth= parent.getMeasuredWidth()-(2*sideVisibleWidth+4*pageMargin);

        PageConsumeX = itemnewWidth + 2*pageMargin;
        int pos = parent.getChildAdapterPosition(view);
        int itemcount= parent.getLayoutManager().getChildCount();

        int leftMargin= pos==0? 2*pageMargin+sideVisibleWidth:pageMargin;
        int rightMargin= pos==itemcount-1? 2*pageMargin+sideVisibleWidth:pageMargin;
        RecyclerView.LayoutParams layoutParams= (RecyclerView.LayoutParams) view.getLayoutParams();
        layoutParams.setMargins(leftMargin,0,rightMargin,0);
        layoutParams.width=itemnewWidth;
        view.setLayoutParams(layoutParams);


//        outRect.left = space;
//        outRect.right = space;
        outRect.bottom = pageMargin;
        outRect.top = pageMargin;
    }

    /**
     * @param scrolled  已经滑动的距离
     */
    public static int getPosition(int scrolled){
        float offset= (float) scrolled/(float) PageConsumeX;
        return Math.round(offset);
    }

}