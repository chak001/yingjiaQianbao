package nlc.zcqb.baselibrary.basemodel.netRequest;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import nlc.zcqb.baselibrary.basemodel.BaseRequest;
import nlc.zcqb.baselibrary.util.Constant;
import nlc.zcqb.baselibrary.util.ReflectionUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by lvqiu on 2017/11/7.
 */

public class SimpleRequest extends BaseRequest {
    Request.Builder mBuilder;


    public SimpleRequest() {
        super();
        mBuilder = new Request.Builder();
    }

    @Override
    public BaseRequest Url(String url) {
        Log.e("Post--->Url",""+url);
        mBuilder.url(url).header(Constant.DEVICE_ID,user.getDeviceId()).get();
        return this;
    }

    @Override
    public BaseRequest Heads(Map<String, String> heads) {
        mBuilder.headers(Headers.of(heads));
        return this;
    }

    @Override
    public BaseRequest Heads(boolean needhead) {
        Map<String,String> map=new HashMap<>();

        if (!needhead) {
            map.put(Constant.DEVICE_ID, (user.getDeviceId()==null)?"":user.getDeviceId());
        } else {
            map.put(Constant.USER_ID, user.getUsername()+"");
            map.put(Constant.DEVICE_ID, user.getDeviceId()+"");
            map.put(Constant.TOKEN_ID, user.getLongToken()+"");
        }
        Log.e("BaseRequest","USER_ID"+user.getUsername());
        Log.e("BaseRequest","DEVICE_ID"+user.getDeviceId());
        Log.e("BaseRequest","TOKEN_ID"+user.getLongToken());
        mBuilder.headers(Headers.of(map ));
        return this;
    }

    @Override
    public BaseRequest Body(Object object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);

        if (json==null){
            json="";
        }

        final MediaType JSON_Type = MediaType.parse("application/json;charset=utf-8");
        RequestBody body = RequestBody.create(JSON_Type, json);
        mBuilder.post(body);
        return this;
    }

    @Override
    public BaseRequest BodyByKeyValue(HashMap<String,String> map) {
        String keyvalues=formDataConnect(map);
        final MediaType JSON_Type = MediaType.parse("application/x-www-form-urlencoded;charset=utf-8");
        RequestBody body = RequestBody.create(JSON_Type, keyvalues);
        mBuilder.post(body);
        return this;
    }

    @Override
    public BaseRequest BodyByKeyValue(Object object) {
        if (object==null){
            return this;
        }
        Map<String,String> map= null;
        try {
            map = ReflectionUtils.objectToMap(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String keyvalues=formDataConnect(map);
        final MediaType JSON_Type = MediaType.parse("application/x-www-form-urlencoded;charset=utf-8");
        RequestBody body = RequestBody.create(JSON_Type, keyvalues);
        mBuilder.post(body);
        return this;
    }


    @Override
    public void begin() {

        mCall = okHttpClient.newCall(mBuilder.build());
        mCall.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("OkHttp_Post","请求失败");
                resultCallback.failure(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.code()==RESPONCE_SUCCESS){
                    String result = response.body().string();
                    Log.e("OkHttp_Post ","请求成功: "+result);
                    JSONObject json = null;
                    int re=0 ;
                    String reMessage=null;
                    try {
                        json = new JSONObject(result);
                        re = Integer.valueOf(json.getString(Constant.RESULTCODE));
                        reMessage = json.getString(Constant.RESULTMESSAGE);
                        Log.e("SimpleRequest","code:"+re+"，message："+reMessage);
                    } catch (JSONException e) {
                        resultCallback.failure("error:json数据解析错误");
                        e.printStackTrace();
                    }
                    resultCallback.Success(result);
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
