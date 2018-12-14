package nlc.zcqb.baselibrary.basemodel.netRequest;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import nlc.zcqb.app.application.MyApplication;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lvqiu on 2016/8/23.
 */
public class DownloadFileRequest {

    OkHttpClient mOkHttpClient;
    Request request ;
    String url;
    Handler mHandler;
    int code=0;
    private float currentTime=0;
    public DownloadFileRequest(Handler mHandler , String url,int code) {
        mOkHttpClient = new OkHttpClient();
        this.mHandler=mHandler;
        this.url=url;
        this.code=code;
        currentTime=System.currentTimeMillis()/1000;
    }

    public void DownLoadFile(){

        request= new  Request.Builder()
                .get()
                .url(url)
                .build();

        okhttp3.Call call = mOkHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                SendFailMessage("OkHttp_Download_请求失败");
                Log.e("OkHttp_Download","请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("onResponse",""+response.code());
                if (response.code() == 200) {
                    InputStream is = null;
                    byte[] buf = new byte[2048];
                    int len = 0;
                    FileOutputStream fos = null;
                    String SDPath = MyApplication.CachePath;
                    try {
                        is = response.body().byteStream();
                        long total = response.body().contentLength();

                        File file1 =new File(SDPath);
                        //如果文件夹不存在则创建
                        if  (!file1 .exists()  && !file1 .isDirectory()) {
                            file1 .mkdirs();
                        }
                        if (new File(SDPath, "XJW.apk").exists()){
                            new File(SDPath, "XJW.apk").delete();
                        }

                        File file = new File(SDPath, "XJW.apk");

                        fos = new FileOutputStream(file);
                        long sum = 0;
                        while ((len = is.read(buf)) != -1) {
                            fos.write(buf, 0, len);
                            sum += len;
                            int progress = (int) ((float) sum * 100  /(float) total) ;
                            Log.d("h_bl", "progress=" + progress);
                            SendProgressMessage(progress);
                        }
                        fos.flush();
                        SendSucessMessage(file.getPath());
                        Log.d("h_bl", "文件下载成功");
                    } catch (Exception e) {
                        SendFailMessage("文件下载失败");
                        Log.d("h_bl", "文件下载失败");
                    } finally {
                        try {
                            if (is != null)
                                is.close();
                        } catch (IOException e) {
                        }
                        try {
                            if (fos != null)
                                fos.close();
                        } catch (IOException e) {
                        }
                    }
                } else {
                    SendFailMessage("错误码:"+response.code());
                }
            }
        });

    }

    public final static int HTTP_FAIL=0;
    public final static int HTTP_PROGRESS = 0Xe3;
    public final static int HTTP_SUCCESS = 0Xe2;

    private void SendFailMessage(String str) {

        Message msg = mHandler.obtainMessage();
        msg.obj = str;
        msg.arg1=code;
        msg.what = HTTP_FAIL;
        msg.sendToTarget();
    }
    private void SendSucessMessage(String filepath) {

        Message msg = mHandler.obtainMessage();
        msg.obj = filepath;
        msg.arg1=code;
        msg.what = HTTP_SUCCESS;
        msg.sendToTarget();
    }

    private void SendProgressMessage(int progress) {
        float tempTime=System.currentTimeMillis()/1000;
        if (Math.abs(tempTime-currentTime)<2){
            return;
        }
        currentTime=tempTime;
        Message msg = mHandler.obtainMessage();
        msg.obj = progress;
        msg.arg1=code;
        msg.what =HTTP_PROGRESS;
        msg.sendToTarget();
    }

}
