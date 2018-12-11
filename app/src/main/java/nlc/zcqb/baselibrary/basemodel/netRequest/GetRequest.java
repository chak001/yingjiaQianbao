package nlc.zcqb.baselibrary.basemodel.netRequest;

import android.util.Log;

import nlc.zcqb.app.event.CommandEvent;
import nlc.zcqb.baselibrary.basemodel.BaseRequest;
import nlc.zcqb.baselibrary.util.Constant;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by lvqiu on 2017/11/7.
 */

public class GetRequest extends BaseRequest{
    Request.Builder mBuilder;
    private final static String TAG="GetRequest";

    public GetRequest() {
        super();
        mBuilder = new Request.Builder();
    }

    @Override
    public BaseRequest Url(String url) {
        Log.e(TAG,""+url);
        this.url=url;
        mBuilder.url(url).header(Constant.DEVICE_ID,user.getDeviceId()).get();
        return this;
    }

    @Override
    public BaseRequest Heads(Map<String, String> heads) {
        return this;
    }

    @Override
    public BaseRequest Heads(boolean needhead) {
        return this;
    }


    @Override
    public BaseRequest Body(Object object) {
        return this;
    }

    @Override
    public BaseRequest BodyByKeyValue(HashMap<String, String> object) {
        return this;
    }

    @Override
    public BaseRequest BodyByKeyValue(Object object) {
        return this;
    }


    @Override
    public void begin() {
        mCall = okHttpClient.newCall(mBuilder.build());
        mCall.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG,"请求失败");
                resultCallback.failure(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.code()==RESPONCE_SUCCESS){
                    String result = response.body().string();
                    Log.e(TAG,"请求成功: "+result);
                    JSONObject json = null;
                    int re=0 ;
                    String reMessage=null;
                    try {
                        json = new JSONObject(result);
                        re = Integer.valueOf(json.getString(Constant.RESULTCODE));
                        reMessage = json.getString(Constant.RESULTMESSAGE);
                    } catch (JSONException e) {
                        resultCallback.failure("error:json数据解析错误" );
                        e.printStackTrace();
                    }
                    if (re== SUCCESS) {
                        resultCallback.Success(result);
                    } else if (re== ERROR_PARAMATER||re== NOT_EXIST){
                        Log.e(TAG,"103或者104");
                        resultCallback.failure(reMessage);
                    }else{
//					109 : 用户未登陆或者登陆过期
//					112 : 用户已在其他设备登陆
//					113 : 账号状态不正确
                        if (re== TOKEN_INVALID|| re== NOT_LOGIN || re== TOKEN_EXCEPTION) {
                            user.clear();
                            user.setLogin(false);
                            EventBus.getDefault().post(new CommandEvent(CommandEvent.CLEAR_USER,""));
                        }
                        resultCallback.failure(reMessage);
                    }
                }else {
                    resultCallback.failure(" response.code:" + response.code());
                }

            }
        });
    }


    @Override
    public void stop() {
        super.stop();
        user=null;
        mBuilder=null;
    }
}
