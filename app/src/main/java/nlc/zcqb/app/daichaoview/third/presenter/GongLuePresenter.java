package nlc.zcqb.app.daichaoview.third.presenter;

import java.util.HashMap;

import nlc.zcqb.app.daichaoview.common.CommonView;
import nlc.zcqb.app.daichaoview.common.IdBean;
import nlc.zcqb.app.daichaoview.first.bean.RecommendBean;
import nlc.zcqb.app.daichaoview.third.bean.GongLueDetailBean;
import nlc.zcqb.app.daichaoview.third.bean.GongLueItemBean;
import nlc.zcqb.app.util.DC;
import nlc.zcqb.baselibrary.basemodel.BaseRequest;
import nlc.zcqb.baselibrary.basemodel.bean.CommonJson;
import nlc.zcqb.baselibrary.basemodel.bean.CommonJson4List;
import nlc.zcqb.baselibrary.presenter.BasePresenter;

import static nlc.zcqb.app.daichaoview.first.fragment.Firstfragment.RECOMMENDTYPE;
import static nlc.zcqb.app.daichaoview.third.fragment.Thirdfragment.GONGLUEDETAILTYPE;
import static nlc.zcqb.app.daichaoview.third.fragment.Thirdfragment.GONGLUETYPE;


/**
 * Created by lvqiu on 2018/10/20.
 */

public class GongLuePresenter extends BasePresenter {
    CommonView commonView;

    public GongLuePresenter(CommonView commonView) {
        this.commonView = commonView;
    }

    @Override
    public void attach() {

    }


    public void getGLList(){
        BaseRequest postRequest=getAutoRequest(POST);
        postRequest.setResultCallback(new BaseRequest.ResultCallback() {
            @Override
            public void Success(final String mess) {
                final CommonJson4List<GongLueItemBean> commonJson4List= CommonJson4List.fromJson(mess,GongLueItemBean.class);
                postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        commonView.success(commonJson4List.getData(),GONGLUETYPE);
                    }
                });
            }

            @Override
            public void failure(final String mess) {
                postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        commonView.failure(mess,GONGLUETYPE);
                    }
                });
            }
        });
        HashMap map=new HashMap<String, String>();
        map.put("state","1");
        postRequest.Url(DC.gonglueliebiao).Heads(false).BodyByKeyValue(map).begin();
    }

    //一页十个
    public void getGLListByPage(int page){

        BaseRequest postRequest=getAutoRequest(POST);
        postRequest.setResultCallback(new BaseRequest.ResultCallback() {
            @Override
            public void Success(final String mess) {
                final CommonJson4List<GongLueItemBean> commonJson4List= CommonJson4List.fromJson(mess,GongLueItemBean.class);
                postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        commonView.success(commonJson4List.getData(),GONGLUETYPE);
                    }
                });
            }

            @Override
            public void failure(final String mess) {
                postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        commonView.failure(mess,GONGLUETYPE);
                    }
                });
            }
        });
        HashMap map=new HashMap<String, String>();
        map.put("state","2");
        map.put("page",page+"");
        postRequest.Url(DC.gonglueliebiao).Heads(false).BodyByKeyValue(map).begin();
    }



    public void getGLDetail(String id){
        IdBean idBean=new IdBean(id);

        BaseRequest postRequest=getAutoRequest(POST);
        postRequest.setResultCallback(new BaseRequest.ResultCallback() {
            @Override
            public void Success(String mess) {
                final CommonJson<GongLueDetailBean> commonJson= CommonJson.fromJson(mess,GongLueDetailBean.class);
                postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        commonView.success(commonJson,GONGLUEDETAILTYPE);
                    }
                });
            }

            @Override
            public void failure(final String mess) {
                postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        commonView.success(mess,GONGLUEDETAILTYPE);
                    }
                });
            }
        });
        postRequest.Url(DC.gongluexiangqing).Heads(false).BodyByKeyValue(idBean).begin();
    }

    @Override
    public void detach() {
        commonView=null;
        super.detach();
    }
}
