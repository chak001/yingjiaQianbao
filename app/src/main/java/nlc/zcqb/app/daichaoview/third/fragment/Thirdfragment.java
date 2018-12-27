package nlc.zcqb.app.daichaoview.third.fragment;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ncl.zcqb.app.R;
import nlc.zcqb.app.daichaoview.common.CommonView;
import nlc.zcqb.app.daichaoview.third.adapter.ThirdAdapter;
import nlc.zcqb.app.daichaoview.third.presenter.GongLuePresenter;
import nlc.zcqb.baselibrary.event.ClickEvent;
import nlc.zcqb.baselibrary.baseview.BaseListFragment;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by lvqiu on 2017/11/20.
 */

public class Thirdfragment extends BaseListFragment  implements CommonView{
    ImageView back;
    TextView title;
    public final static int GONGLUETYPE=5;
    public final static int GONGLUEDETAILTYPE=6;
    private GongLuePresenter presenter;
    private int pagenum=1;

    @SuppressLint({"NewApi", "ValidFragment"})
    public Thirdfragment() {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ClickEvent event) {

    }

    @SuppressLint({"NewApi", "ValidFragment"})
    public Thirdfragment(FragmentManager fragmentManager) {
    }

    @Override
    public void initOther() {
        setAdapter(new ThirdAdapter(getContext()));

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_third;
    }

    @Override
    public void initViews(View view) {
        initTitleBar(view);

        presenter=new GongLuePresenter(this);
        addPresenter(presenter);
    }

    public void initTitleBar(View view){
        back=(ImageView) view.findViewById(R.id.back_action);
        title=(TextView) view.findViewById(R.id.title_name);
        title.setText(getResources().getText(R.string.thirdtitle));
    }

    @Override
    public void asyRefreshData() {
        startLoadingAnimation();
        presenter.getGLListByPage(1);
    }

    @Override
    public void asyLoadingMoreData() {
        presenter.getGLListByPage(pagenum);
    }


    @Override
    public void success(Object o, int type) {
        if (GONGLUETYPE==type){
            updateData(o);
            pagenum++;
        }
        stopLoadingAnimation();
    }

    @Override
    public void failure(Object o, int type) {
        Toast.makeText(getContext(),(String)o,Toast.LENGTH_SHORT).show();
        stopLoadingAnimation();
    }
}
