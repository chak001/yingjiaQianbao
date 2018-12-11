package nlc.zcqb.app.daichaoview.common;

import android.util.Log;
import com.google.gson.Gson;
import java.lang.reflect.ParameterizedType;
import java.util.Map;
import nlc.zcqb.app.daichaoview.fourth.bean.PersonBean;
import nlc.zcqb.baselibrary.basemodel.netRequest.HttpConnectUtil;
import nlc.zcqb.baselibrary.presenter.BasePresenter;
import nlc.zcqb.baselibrary.util.ReflectionUtils;


/**
 * Created by lvqiu on 2018/10/21.
 */

public class BeanPresenter<T> extends BasePresenter{
    CommonView commonView;

    public BeanPresenter(CommonView commonView) {
        this.commonView = commonView;
    }

    @Override
    public void attach() {

    }


    public void getCommonData(final int type, Object object, String url){
        Map<String,String> map= null;
        try {
            map = ReflectionUtils.objectToMap(object);
        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpConnectUtil util=new HttpConnectUtil(new HttpConnectUtil.XykjHttpCall() {
            @Override
            public void success(final String mess) {

                Log.e("HttpConnectUtil","结果是："+mess);

                Gson gson = new Gson();
                final PersonBean bean= gson.fromJson(mess, PersonBean.class);
                postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        commonView.success(bean,type);
                    }
                });
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


    public Class<T> getTClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }


    @Override
    public void detach() {
        commonView=null;
        super.detach();
    }


}
