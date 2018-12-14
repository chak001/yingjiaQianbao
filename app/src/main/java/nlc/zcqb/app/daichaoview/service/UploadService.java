package nlc.zcqb.app.daichaoview.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import nlc.zcqb.app.application.MyApplication;
import nlc.zcqb.app.application.User.User;
import nlc.zcqb.app.event.progressEvent;
import nlc.zcqb.app.util.DC;
import nlc.zcqb.baselibrary.basemodel.bean.FileResult;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static nlc.zcqb.baselibrary.util.Constant.DEVICE_ID;
import static nlc.zcqb.baselibrary.util.Constant.TOKEN_ID;
import static nlc.zcqb.baselibrary.util.Constant.USER_ID;

/**
 * Created by lvqiu on 2017/11/5.
 */

public class UploadService  extends IntentService {
    private String url = DC.serverIp+DC.uploadPic;
    private static final String ACTION_UPLOAD= "action.UPLOAD_";
    public static final String EXTRA_PATH = "intentservice.extra.PATH";
    public static final String KEY = "intentservice.extra.KEY";

    private User user= MyApplication.mUser;


    public static void startUpload(Context context, String path,String key)
    {
        Log.d("UploadService",""+path);
        String[] words=path.split(";");
        ArrayList<String> list=  new ArrayList<>(Arrays.asList(words));
        startUpload(context,list,key);
    }



    public static void startUpload(Context context, ArrayList<String> paths, String key)
    {
        Intent intent = new Intent(context, UploadService.class);
        intent.setAction(ACTION_UPLOAD);
        intent.putExtra(EXTRA_PATH, paths);
        intent.putExtra(KEY, key);
        context.startService(intent);

        Log.d("UploadService","多张图片");

    }

    /**
    public UploadService(){
        super("UploadService");
    }
     * 必须需要无参数构造函数，用于初始化线程名
     */

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public UploadService(String name) {
        super(name);
    }
    public UploadService() {
        super("UploadService");
    }
    /**
     *  用于处理异步任务，而且这种异步任务是串行的，同一时间只有一个任务在执行
     * @param intent
     */
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        ArrayList<String> paths= intent.getStringArrayListExtra(EXTRA_PATH);
        String action=intent.getStringExtra(ACTION_UPLOAD);
        String key=intent.getStringExtra(KEY);

        Log.e("TAG","onHandleIntent"+action+paths.get(0));
        uploadMultiFile(paths,key);
    }


    private void uploadMultiFile(final ArrayList<String> paths,final String key) {

        MultipartBody.Builder  builder= new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        for (String temppath: paths) {
            File file = new File(temppath);
            if (file.exists()) {
                RequestBody fileBody = RequestBody.create(MediaType.parse("media/type"), file);
                builder.addFormDataPart("file", file.getName(), fileBody);
            }
        }

        RequestBody requestBody= builder.build();
        Request request = new Request.Builder()
                .url(url)
                .headers(Headers.of(getHeads()))
                .post(requestBody)
                .build();

        final OkHttpClient.Builder httpBuilder = new OkHttpClient.Builder();
        OkHttpClient okHttpClient  = httpBuilder
                //设置超时
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(0, TimeUnit.SECONDS)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("onFailure", "uploadMultiFile() e=" + e);
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Gson gson=new Gson();
                FileResult result=gson.fromJson(response.body().string(),FileResult.class);
                EventBus.getDefault().post(new progressEvent(key,result.getPic(),"",100)); //发布事件
                Log.i("onResponse", "uploadMultiFile() response=" + result.getPic());
            }
        });
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        Log.e("TAG","onCreate");
    }

    @Override
    public void onDestroy()
    {
        Log.e("TAG","onDestroy");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        Log.e("TAG","onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }


    public  Map<String,String> getHeads() {
        Map<String,String> map=new HashMap<>();

        map.put(USER_ID, user.getUsername()+"");
        map.put(DEVICE_ID, user.getDeviceId()+"");
        map.put(TOKEN_ID, user.getLongToken()+"");

        return  map;
    }

}
