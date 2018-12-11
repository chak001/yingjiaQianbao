package nlc.zcqb.app.daichaoview.first.presenter;

import org.json.JSONException;
import org.json.JSONObject;

import nlc.zcqb.app.daichaoview.common.CommonView;
import nlc.zcqb.app.daichaoview.first.bean.RecommendBean;
import nlc.zcqb.app.util.DC;
import nlc.zcqb.baselibrary.basemodel.BaseRequest;
import nlc.zcqb.baselibrary.basemodel.bean.CommonJson;
import nlc.zcqb.baselibrary.basemodel.bean.CommonJson4List;
import nlc.zcqb.baselibrary.basemodel.netRequest.HttpConnectUtil;
import nlc.zcqb.baselibrary.presenter.BasePresenter;

import static nlc.zcqb.app.daichaoview.first.fragment.Firstfragment.BGTYPE;
import static nlc.zcqb.app.daichaoview.first.fragment.Firstfragment.BULLETTYPE;
import static nlc.zcqb.app.daichaoview.first.fragment.Firstfragment.RECOMMENDTYPE;


/**
 * Created by lvqiu on 2018/10/20.
 */

public class RecommendPresenter extends BasePresenter {
    CommonView commonView;

    public RecommendPresenter(CommonView commonView) {
        this.commonView = commonView;
    }

    @Override
    public void attach() {

    }


    public void getRecommendList(){
        BaseRequest postRequest=getAutoRequest(POST);
        postRequest.setResultCallback(new BaseRequest.ResultCallback() {
            @Override
            public void Success(final String mess) {
                final CommonJson4List<RecommendBean> commonJson4List= CommonJson4List.fromJson(mess,RecommendBean.class);
                postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        commonView.success(commonJson4List.getData(),RECOMMENDTYPE);
                    }
                });
            }

            @Override
            public void failure(final String mess) {
                postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        commonView.failure(mess,RECOMMENDTYPE);
                    }
                });
            }
        });
        postRequest.Url(DC.tuijianpingtailist).Heads(false).begin();
    }

    @Override
    public void detach() {
        commonView=null;
        super.detach();
    }
}
