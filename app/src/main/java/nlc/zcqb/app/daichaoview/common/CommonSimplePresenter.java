package nlc.zcqb.app.daichaoview.common;

import android.util.Log;

import com.google.gson.Gson;
import com.xinlan.discview.util;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

import nlc.zcqb.app.util.DC;
import nlc.zcqb.baselibrary.basemodel.BaseRequest;
import nlc.zcqb.baselibrary.basemodel.bean.CommonJson;
import nlc.zcqb.baselibrary.basemodel.bean.CommonJson4List;
import nlc.zcqb.baselibrary.basemodel.netRequest.HttpConnectUtil;
import nlc.zcqb.baselibrary.presenter.BasePresenter;
import nlc.zcqb.baselibrary.util.ReflectionUtils;


/**
 * Created by lvqiu on 2018/10/21.
 */

public class CommonSimplePresenter  extends BasePresenter{
    CommonView commonView;

    public CommonSimplePresenter(CommonView commonView) {
        this.commonView = commonView;
    }

    @Override
    public void attach() {

    }


    public void getCommonData(final int type, Object object, String url, final String Key){
        Map<String,String> map= null;
        try {
            map = ReflectionUtils.objectToMap(object);
        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpConnectUtil util=new HttpConnectUtil(new HttpConnectUtil.XykjHttpCall() {
            @Override
            public void success(final String response) {
                if (Key==null){
                    postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            commonView.success(response,type);
                        }
                    });
                }else {
                    Log.e("HttpConnectUtil", "结果是：" + response);
                    try {
                        if (response == null || response.length() == 0) {
                            return;
                        }
                        JSONObject jsonObject = new JSONObject(response);
                        final String value = jsonObject.getString(Key);
                        postRunnable(new Runnable() {
                            @Override
                            public void run() {
                                commonView.success(value, type);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void error(final String error_message) {
                Log.e("HttpConnectUtil","结果是："+error_message);
                postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        commonView.failure(error_message,type);
                    }
                });
            }
        }, url,map);
        util.start();
    }



    @Override
    public void detach() {
        commonView=null;
        super.detach();
    }


}
