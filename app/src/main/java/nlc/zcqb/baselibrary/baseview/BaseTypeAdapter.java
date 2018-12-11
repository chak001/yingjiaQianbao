package nlc.zcqb.baselibrary.baseview;


import android.content.Context;
import android.support.annotation.IntRange;
import android.support.v7.widget.RecyclerView;


/**
 * Created by lvqiu on 2017/11/20.
 */

public abstract class BaseTypeAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder>{
    protected Context mContext;
    public final static int REFRESH=1;
    public final static int LOADMORE=2;

    public BaseTypeAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public final int getItemViewType(int position) {
        return getTypeByPosition(position);
    }

    public abstract int getTypeByPosition(int position);

    public abstract void updateData(Object object);

    public abstract void loadMoreData(Object object);

    public abstract void TypeFactory(Object object,int type,@IntRange(from=1,to=2) int UpdateType);
}
