package nlc.zcqb.baselibrary.basemodel.netRequest;

import android.app.Activity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import nlc.zcqb.app.util.DC;


/**
 * 作者：LiChangXin
 * Created by haiyang on 2018/1/25.
 * 简介：
 * 1.自定义参数(传参时，只要传入相对应的map)
 * 2.支持接口回调，在回调方法内写结果处理逻辑
 * 3.简单的线程控制，开发者不需要手动实现线程切换
 * 4.简单易用的API
 */

public class HttpConnectUtil extends Thread{

    /**
     * 通过构造函数
     * 赋值：
     * url - 接口地址
     * form - 请求参数(map)
     * xykjHttpCall - 回调接口
     * mactivity - 基于activity的runOnUiThread()方法的线程控制
     */
    private String url = "";
    private Map<String,String> form;
    private XykjHttpCall xykjHttpCall = null;

    //下面这些 与之前的例子相同
    private HttpURLConnection connection = null;
    private PrintWriter pw = null;
    private BufferedReader bufferedReader = null;
    private String line = null;
    private StringBuilder response_cache = new StringBuilder();
    private String response = null;
    private String parameter = null;
    //构造函数
    public HttpConnectUtil(XykjHttpCall xykjHttpCall,String url_p, Map<String,String> form_p ){
        url = url_p;
        form = form_p;
        this.xykjHttpCall = xykjHttpCall;

    }

    @Override
    public void run() {
        Log.e("HttpConnectUtil","XYKJHTTPPOST : " + "Thread@run() 方法开始执行");
        try{
            if(url.equals("") || url == null){
                //若url为空，结束执行
                Log.e("HttpConnectUtil","XYKJHTTPPOST : " + "无请求参数");
                return;
            }
            URL url_path = new URL(url.trim());
            connection = (HttpURLConnection) url_path.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setRequestMethod("POST");
            //获取连接
            connection.connect();
            if( form != null && !form.isEmpty() ){
                //获取连接输出流，并写入表单参数
                pw = new PrintWriter(connection.getOutputStream());
                parameter = formDataConnect(form);
                pw.print(parameter);
                pw.flush();
            }
            //获取响应 输入流
            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while ((line = bufferedReader.readLine()) != null){
                response_cache.append(line);
            }
            response = response_cache.toString().trim();
            Log.e("HttpConnectUtil","XYKJHTTPPOST : RESPONSE -> " + response);
            if (response==null || response.length()==0){
                xykjHttpCall.error("服务器返回为空！");
                Log.e("HttpConnectUtil","服务器返回为空"+url);
            }
            xykjHttpCall.success(response);
        }catch (Exception e){
            xykjHttpCall.error("网络请求/解析异常:");
            Log.e("HttpConnectUtil","XYKJHTTPPOST : 网络请求/解析异常"+url);
            e.printStackTrace();
        }finally {
            try{
                if(pw != null){
                    pw.close();
                }
                if(bufferedReader != null){
                    bufferedReader.close();
                }
                if(connection != null){
                    connection.disconnect();
                }
            }catch (Exception e){
                Log.e("HttpConnectUtil","XYKJHTTPPOST : 关闭连接/流异常");
                e.printStackTrace();
            }
        }
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
    /**
     *  定义回调接口
     */
    public interface XykjHttpCall {
        void success(String response);
        void error(String error_message);
    }
    /**
     * 线程启动
     * 执行请求
     */
    public void request(){
        this.start();
    }
}