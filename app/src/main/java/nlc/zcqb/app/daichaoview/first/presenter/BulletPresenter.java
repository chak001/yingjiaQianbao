package nlc.zcqb.app.daichaoview.first.presenter;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import nlc.zcqb.app.daichaoview.common.CommonView;
import nlc.zcqb.app.util.DC;
import nlc.zcqb.baselibrary.basemodel.BaseRequest;
import nlc.zcqb.baselibrary.basemodel.bean.CommonJson;
import nlc.zcqb.baselibrary.basemodel.netRequest.HttpConnectUtil;
import nlc.zcqb.baselibrary.presenter.BasePresenter;

import static nlc.zcqb.app.daichaoview.first.fragment.Firstfragment.BGTYPE;
import static nlc.zcqb.app.daichaoview.first.fragment.Firstfragment.BULLETTYPE;


/**
 * Created by lvqiu on 2018/10/20.
 */

public class BulletPresenter extends BasePresenter {
    CommonView commonView;

    public BulletPresenter(CommonView commonView) {
        this.commonView = commonView;
    }

    @Override
    public void attach() {

    }


    public void getBullet(){
        BaseRequest postRequest=getAutoRequest(POST);
        postRequest.setResultCallback(new BaseRequest.ResultCallback() {
            @Override
            public void Success(final String mess) {
                final CommonJson<String> commonJson= CommonJson.fromJson(mess,String.class);
                postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        commonView.success(commonJson.getData(),BULLETTYPE);
                    }
                });
            }

            @Override
            public void failure(final String mess) {
                postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        commonView.failure(mess,BULLETTYPE);
                    }
                });
            }
        });
        postRequest.Url(DC.gonggao).Heads(false).begin();
    }

    public void getBg(){
        HttpConnectUtil httpConnectUtil=new HttpConnectUtil(new HttpConnectUtil.XykjHttpCall() {
            @Override
            public void success(final String response) {
                JSONObject jsonObject=null;
                String image="";
                try {
                    jsonObject=new JSONObject(response);
                    image= (String) jsonObject.get("img");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                final String imageurl= image;
                postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        commonView.success(imageurl,BGTYPE);
                    }
                });
            }

            @Override
            public void error(final String error_message) {
                postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        commonView.success(error_message,BGTYPE);
                    }
                });
            }
        },DC.homebg,null);
        httpConnectUtil.start();
    }


    @Override
    public void detach() {
        commonView=null;
        super.detach();
    }
}
