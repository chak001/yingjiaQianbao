package nlc.zcqb.app.daichaoview.fourth.view;

import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import ncl.zcqb.app.R;
import nlc.zcqb.app.daichaoview.common.CommonSimplePresenter;
import nlc.zcqb.app.daichaoview.common.CommonView;
import nlc.zcqb.app.daichaoview.fourth.adapter.XinYongAdapter;
import nlc.zcqb.app.daichaoview.fourth.bean.XInYongNumBean;
import nlc.zcqb.app.util.DC;
import nlc.zcqb.baselibrary.baseview.BaseListActivity;

/**
 * Created by lvqiu on 2018/10/14.
 */

public class XinYongActivity extends BaseListActivity implements CommonView{
    private CommonSimplePresenter numPresenter;
    private final static int NUMTYPE= 15;


    @Override
    public int getLayoutId() {
        return R.layout.activity_xinyongzhenduan;
    }

    @Override
    public void initTitle() {

    }

    @Override
    public void InitOthers() {
        numPresenter=new CommonSimplePresenter(this);
        addPresenter(numPresenter);
        setAdapter(new XinYongAdapter(this));
        numPresenter.getCommonData(NUMTYPE,null, DC.zhenduanshuju,null);
    }

    @Override
    public void parseIntent() {

    }

    @Override
    public void asyRefreshData() {
        startLoadingAnimation();
        numPresenter.getCommonData(NUMTYPE,null, DC.zhenduanshuju,null);
    }

    @Override
    public void asyLoadingMoreData() {

    }

    @Override
    public void success(Object o, int type) {
        if (type==NUMTYPE){
            Gson gson=new Gson();
            XInYongNumBean bean=gson.fromJson((String)o, XInYongNumBean.class);
            getAdapter().updateData(bean);
        }
        stopLoadingAnimation();
    }

    @Override
    public void failure(Object o, int type) {
        Toast.makeText(this,(String)o,Toast.LENGTH_SHORT).show();
        stopLoadingAnimation();
    }
}
