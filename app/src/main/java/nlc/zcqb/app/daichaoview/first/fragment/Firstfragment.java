package nlc.zcqb.app.daichaoview.first.fragment;

import android.annotation.SuppressLint;
import android.support.annotation.IntRange;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import ncl.zcqb.app.R;
import nlc.zcqb.app.daichaoview.common.CommonView;
import nlc.zcqb.app.daichaoview.first.adapter.FirstAdapter;
import nlc.zcqb.app.daichaoview.first.presenter.BulletPresenter;
import nlc.zcqb.app.daichaoview.first.presenter.RecommendPresenter;
import nlc.zcqb.app.daichaoview.third.presenter.GongLuePresenter;
import nlc.zcqb.app.event.ClickEvent;
import nlc.zcqb.baselibrary.baseview.BaseListFragment;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static nlc.zcqb.app.daichaoview.third.fragment.Thirdfragment.GONGLUETYPE;
import static nlc.zcqb.baselibrary.baseview.BaseTypeAdapter.REFRESH;

/**
 * Created by lvqiu on 2017/11/20.
 */

public class Firstfragment extends BaseListFragment  implements CommonView {
    private BulletPresenter bulletPresenter;
    private RecommendPresenter recommendPresenter;
    private GongLuePresenter gongLuePresenter;
    private FirstAdapter firstadapter;
    public final static int BGTYPE=1;
    public final static int BULLETTYPE=2;
    public final static int RECOMMENDTYPE=3;

    @SuppressLint({"NewApi", "ValidFragment"})
    public Firstfragment() {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ClickEvent event) {

    }

    @SuppressLint({"NewApi", "ValidFragment"})
    public Firstfragment(FragmentManager fragmentManager) {
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_first;
    }

    @Override
    public void initOther() {
        firstadapter=new FirstAdapter(getContext());
        setAdapter(firstadapter);

        gongLuePresenter=new GongLuePresenter(this);
        recommendPresenter=new RecommendPresenter(this);
        bulletPresenter=new BulletPresenter(this);
        addPresenter(bulletPresenter);
        addPresenter(gongLuePresenter);
        addPresenter(recommendPresenter);
        bulletPresenter.getBg();
        bulletPresenter.getBullet();
    }

    ImageView backgroundImage;
    TextView title;
    FrameLayout rootview_t;
    int totaly=0;
    @Override
    public void initViews(View view) {
        rootview_t=(FrameLayout) view.findViewById(R.id.rootview_t);
        backgroundImage=(ImageView) view.findViewById(R.id.bgImage);
        title=(TextView) view.findViewById(R.id.title_name);
        title.setText(getResources().getText(R.string.Firsttitle));
        setScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager= (LinearLayoutManager) recyclerView.getLayoutManager();
                int firstVisibleItemPosition =  layoutManager.findFirstVisibleItemPosition();
                totaly+=dy;
                if (totaly>255 || firstVisibleItemPosition>1){
                    changeColor(255);
                }else {
                    changeColor(totaly);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            }
        });
    }

    public void changeColor(int alpha){
        if (alpha>255){
            alpha=255;
        }
        if (alpha<0){
            alpha=0;
        }
        if (alpha==0){
            backgroundImage.setVisibility(View.GONE);
            rootview_t.setVisibility(View.GONE);
        }else {
            backgroundImage.setVisibility(View.VISIBLE);
            rootview_t.setVisibility(View.VISIBLE);
        }
        if (backgroundImage.getBackground()!=null){
            //设置mutate，使其不会影响其他布局
            backgroundImage.getBackground().mutate().setAlpha(alpha);//s0~255透明度值 ，0为完全透明，255为不透明
        }
    }

    @Override
    public void asyRefreshData() {
        startLoadingAnimation();
        recommendPresenter.getRecommendList();
        gongLuePresenter.getGLList();
        bulletPresenter.getBg();
        bulletPresenter.getBullet();
    }

    @Override
    public void asyLoadingMoreData() {

    }


    @Override
    public void success(Object o,int type) {
        if (type==BULLETTYPE) { //公告
            String bullet = (String) o;
            firstadapter.updateBullet(bullet);
        }else if (type==BGTYPE){ //背景图片
            String bullet = (String) o;
            firstadapter.updateBG(bullet);
        }else if (type==RECOMMENDTYPE){
            updateData(o);
        }else if (type==GONGLUETYPE){
            updateDataByType(o,GONGLUETYPE,REFRESH);
        }
        stopLoadingAnimation();
    }

    @Override
    public void failure(Object o,int type) {
        Toast.makeText(getContext(), (String) o,Toast.LENGTH_SHORT).show();
        stopLoadingAnimation();
    }
}
