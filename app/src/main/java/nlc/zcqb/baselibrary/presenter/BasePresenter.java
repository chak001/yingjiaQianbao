package nlc.zcqb.baselibrary.presenter;


import android.os.Handler;

import nlc.zcqb.app.application.MyApplication;
import nlc.zcqb.baselibrary.basemodel.BaseRequest;
import nlc.zcqb.baselibrary.basemodel.netRequest.GetRequest;
import nlc.zcqb.baselibrary.basemodel.netRequest.PostRequest;
import nlc.zcqb.baselibrary.basemodel.netRequest.SimpleRequest;
import nlc.zcqb.baselibrary.util.Constant;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lvqiu on 2017/6/7.
 */

public abstract  class BasePresenter {
    public final static int NOT_EXIST=103;
    public final static int ERROR_PARAMATER=104;
    public final static int LESS_PARAMATER=105;
    public final static int NOT_LOGIN =106;
    public final static int TOKEN_INVALID =107;

    public final static int GET =1;
    public final static int POST =2;
    public final static int SIMPLE =3;

    public final static int SUCCESS =200;
    protected Handler handler;
    protected ArrayList<BaseRequest> list;

    public BasePresenter() {
        list=new ArrayList<>();
        handler=new Handler();
    }

    public abstract void attach();

    protected void postRunnable(Runnable runnable){
        if (handler!=null){
            handler.post(runnable);
        }
    }

    protected BaseRequest getAutoRequest(int type){
        BaseRequest baseRequest;
        if (type==GET){
            baseRequest=new GetRequest();
        }else if (type==POST){
            baseRequest=new PostRequest();
        }else {
            baseRequest=new SimpleRequest();
        }
        list.add(baseRequest);
        clearList(false);
        return baseRequest;
    }

    public  void detach(){
        if (handler!=null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
        clearList(true);
    }

    public void clearList(boolean force){
        if (list!=null){
            List<BaseRequest> temp=new ArrayList<>();
            for (BaseRequest r:temp) {
                if (force) {
                    r.stop();
                    temp.add(r);
                }else if (r.isDone()){
                    r.stop();
                    temp.add(r);
                }
            }
            list.removeAll(temp);
        }
        if (force){
            list.clear();
            list=null;
        }
    }

    public static Map<String,String> getHeaders(){
        Map<String,String> map=new HashMap<>();
		map.put(Constant.USER_ID, MyApplication.mUser.getUsername()+"");
		map.put(Constant.DEVICE_ID, MyApplication.mUser.getDeviceId()+"");
		map.put(Constant.TOKEN_ID, MyApplication.mUser.getLongToken()+"");
        return map;
    }
}
