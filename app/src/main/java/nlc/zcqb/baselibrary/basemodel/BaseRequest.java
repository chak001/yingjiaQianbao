package nlc.zcqb.baselibrary.basemodel;

import nlc.zcqb.app.application.MyApplication;
import nlc.zcqb.app.application.User.User;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by lvqiu on 2017/11/7.
 */

public abstract class BaseRequest {


    public enum TYPE {LOGIN,  OTHER}
    private TYPE type;
    public final static int NOT_EXIST=103;
    public final static int ERROR_PARAMATER=104;
    public final static int LESS_PARAMATER=105;
    public final static int NOT_LOGIN =106;
    public final static int TOKEN_INVALID =107;
    public final static int TOKEN_EXCEPTION =108;
    public final static int RESPONCE_SUCCESS =200;
    public final static int SUCCESS =100;

    public  OkHttpClient okHttpClient;
    public  okhttp3.Call mCall;
    public ResultCallback resultCallback;
    protected User user= MyApplication.mUser;
    protected boolean isDone=false;
    protected String url;
    public boolean isDone() {
        return isDone;
    }

    public void stop() {
        if (mCall!=null){
            mCall.cancel();
        }
        okHttpClient=null;
    }

    //String url, Map<String,String> heads, String body
    public BaseRequest() {
        okHttpClient=builder();
    }


    public OkHttpClient builder(){
        okhttp3.OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient  = httpBuilder
                //设置超时
                .connectTimeout(1000, TimeUnit.SECONDS)
                .writeTimeout(1000, TimeUnit.SECONDS)
                .readTimeout(1000,TimeUnit.SECONDS)
                .build();
        return okHttpClient;
    }

    public abstract BaseRequest Url(String url);

    public abstract BaseRequest Heads(Map<String,String> heads);

    public abstract BaseRequest Heads(boolean needhead);
    public abstract BaseRequest Body(Object object);
    public abstract BaseRequest BodyByKeyValue(HashMap<String,String> object);
    public abstract BaseRequest BodyByKeyValue(Object object);

    public abstract void begin();


    public void setResultCallback(ResultCallback resultCallback) {
        this.resultCallback = resultCallback;
    }

    /**
     * 参数转换函数
     * map -> http[post] 参数
     * @param form_data
     * @return
     */
    public String formDataConnect(Map<String,String> form_data){
        StringBuilder url_form = new StringBuilder();
        //遍历map，按照url参数形式拼接
        for(String key:form_data.keySet()){
            if(url_form.length() != 0){
                //从第二个参数开始，每个参数key、value前添加 & 符号
                url_form.append("&");
            }
            url_form.append(key).append("=").append(form_data.get(key));
        }
        return url_form.toString();
    }

    public interface ResultCallback{
        public void Success(String mess);
        public void failure(String mess);
    }

}
