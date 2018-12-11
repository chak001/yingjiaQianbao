package nlc.zcqb.app.daichaoview.common;


import android.util.Log;

import com.example.customview.validateview.Codecallback;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import nlc.zcqb.app.util.DC;
import nlc.zcqb.baselibrary.basemodel.BaseRequest;
import nlc.zcqb.baselibrary.basemodel.bean.CommonJson;
import nlc.zcqb.baselibrary.basemodel.netRequest.HttpConnectUtil;
import nlc.zcqb.baselibrary.presenter.BasePresenter;

/**
 * Created by lvqiu on 2017/11/8.
 */

public class GetCodePresenter extends BasePresenter{

    private Codecallback codecallback;

    public GetCodePresenter(Codecallback codecallback) {
        super();
        this.codecallback = codecallback;
    }

    @Override
    public void attach() {

    }


    public void getCode(final String username ){
        HashMap<String,String> map=new HashMap<>();
        map.put("tel",username);

        BaseRequest postRequest=getAutoRequest(SIMPLE);
        postRequest.setResultCallback(new BaseRequest.ResultCallback() {
            @Override
            public void Success(String mess) {
                Gson gson=new Gson();
                final CodeBean codeBean=gson.fromJson(mess,CodeBean.class);

                postRunnable(new Runnable() {
                    @Override
                    public void run() {
                       // codecallback.success(String.valueOf(codeBean.getYzm()));

                    }
                });
            }

            @Override
            public void failure(final String mess) {
                postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        codecallback.success("获取验证码失败！"+mess);
                    }
                });
            }
        });
        postRequest.Url(DC.sendcode).Heads(false).BodyByKeyValue(map).begin();
    }

    public void setCodecallback(Codecallback codecallback) {
        this.codecallback = codecallback;
    }

    @Override
    public void detach() {
        codecallback=null;
        super.detach();
    }

}
