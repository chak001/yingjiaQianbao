package nlc.zcqb.app.daichaoview.fourth.fragment;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import nlc.zcqb.app.application.MyApplication;
import nlc.zcqb.app.daichaoview.common.CommonSimplePresenter;
import nlc.zcqb.app.daichaoview.common.CommonView;
import nlc.zcqb.app.daichaoview.fourth.bean.ShareBean;
import nlc.zcqb.app.util.DC;
import nlc.zcqb.baselibrary.basemodel.sharepreferenceRequest.ObjPreferUtil;
import nlc.zcqb.baselibrary.baseview.BaseListFragment;
import nlc.zcqb.app.daichaoview.fourth.adapter.FourthAdapter;
import nlc.zcqb.baselibrary.event.ClickEvent;
import nlc.zcqb.baselibrary.util.StringUtils;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.concurrent.ExecutionException;

import static nlc.zcqb.app.daichaoview.second.view.DaiKuanDetailActivity.GETQQ;

/**
 * Created by lvqiu on 2017/11/20.
 */

public class Fourthfragment extends BaseListFragment implements CommonView {
    private CommonSimplePresenter Presenter;
    public String kefuQQ="";
    public int GETSHAREMESSAGE=1114;
    @SuppressLint({"NewApi", "ValidFragment"})
    public Fourthfragment() {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ClickEvent event) {

    }

    @SuppressLint({"NewApi", "ValidFragment"})
    public Fourthfragment(FragmentManager fragmentManager) {
    }

    @Override
    public void initOther() {
        Presenter=new CommonSimplePresenter(this);
        addPresenter(Presenter);
        Presenter.getCommonData(GETQQ,null, DC.kefuqq,"num");
        Presenter.getCommonData(GETSHAREMESSAGE,null,DC.getsharemess,"data");
        setAdapter(new FourthAdapter(getContext()));
    }

    @Override
    public void initViews(View view) {

    }


    @Override
    public void asyRefreshData() {
        Presenter.getCommonData(GETQQ,null, DC.kefuqq,"num");
        Presenter.getCommonData(GETSHAREMESSAGE,null,DC.getsharemess,"data");
    }

    @Override
    public void asyLoadingMoreData() {

    }

    public void refreshHead(){
        getAdapter().updateData(MyApplication.mUser);
    }

    @Override
    public void success(Object o, int type) {
        if (type==GETQQ ) {
            if (StringUtils.isNumeric((String) o)) {
                kefuQQ = (String) o;
            }else {
                kefuQQ=DC.tempQQ;
            }
            updateData(kefuQQ);
        }else if (type==GETSHAREMESSAGE){
            Gson gson=new Gson();
            ShareBean bean= gson.fromJson((String) o,ShareBean.class);
            downLoadImage(bean);
        }
    }

    @Override
    public void failure(Object o, int type) {
        Toast.makeText(getContext(),"客服qq获取失败!",Toast.LENGTH_SHORT).show();
    }


    public void downLoadImage(final ShareBean bean){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File file = Glide.with(getContext())
                            .load(bean.getUrl())
                            .downloadOnly(200, 200)
                            .get();
                    bean.setLocalPath(file.getPath());
                    ObjPreferUtil<ShareBean> preferUtil=new ObjPreferUtil<>(ShareBean.class);
                    preferUtil.save(bean,"GETSHAREMESSAGE");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
