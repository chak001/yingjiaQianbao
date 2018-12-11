package nlc.zcqb.app.daichaoview.common;

import java.lang.reflect.ParameterizedType;

import nlc.zcqb.baselibrary.basemodel.BaseRequest;
import nlc.zcqb.baselibrary.basemodel.bean.CommonJson;
import nlc.zcqb.baselibrary.basemodel.bean.CommonJson4List;
import nlc.zcqb.baselibrary.presenter.BasePresenter;


/**
 * Created by lvqiu on 2018/10/21.
 */

public class CommonPresenter<T> extends BasePresenter{
    CommonView commonView;

    public CommonPresenter(CommonView commonView) {
        this.commonView = commonView;
    }

    @Override
    public void attach() {

    }

    /**
     *
     * @param type
     * @param object
     * @param url
     * @param isList
     */
    public void getCommonData(final int type, Object object,String url, final boolean isList){
        BaseRequest postRequest=getAutoRequest(POST);
        postRequest.setResultCallback(new BaseRequest.ResultCallback() {
            @Override
            public void Success(final String mess) {
                if (isList){
                    final CommonJson4List<T> commonJson= CommonJson4List.fromJson(mess,getTClass());
                    postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            commonView.success(commonJson.getData(),type);
                        }
                    });
                }else {
                    final CommonJson<T> commonJson= CommonJson.fromJson(mess,getTClass());
                    postRunnable(new Runnable() {
                        @Override
                        public void run() {
                            commonView.success(commonJson.getData(),type);
                        }
                    });
                }
            }

            @Override
            public void failure(final String mess) {
                postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        commonView.failure(mess,type);
                    }
                });
            }
        });
        postRequest.Url(url).Heads(false).BodyByKeyValue(object).begin();
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
