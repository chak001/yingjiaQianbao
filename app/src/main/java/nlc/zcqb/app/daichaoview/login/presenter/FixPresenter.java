package nlc.zcqb.app.daichaoview.login.presenter;

import android.widget.Toast;

import com.example.customview.util;

import nlc.zcqb.app.application.MyApplication;
import nlc.zcqb.app.application.User.User;
import nlc.zcqb.app.daichaoview.login.bean.RegisterBean;
import nlc.zcqb.app.daichaoview.login.viewcallback.FixView;
import nlc.zcqb.app.daichaoview.login.viewcallback.RegisterView;
import nlc.zcqb.app.util.DC;
import nlc.zcqb.baselibrary.basemodel.BaseRequest;
import nlc.zcqb.baselibrary.basemodel.bean.CommonJson;
import nlc.zcqb.baselibrary.basemodel.sharepreferenceRequest.UserPreferencesUtil;
import nlc.zcqb.baselibrary.presenter.BasePresenter;


/**
 * Created by lvqiu on 2018/10/20.
 */

public class FixPresenter extends BasePresenter {
    FixView fixView;

    public FixPresenter(FixView fixView) {
        this.fixView = fixView;
    }

    @Override
    public void attach() {

    }


    public void fixPassword(String tel,String code,String pwd){
        if (!util.isMobileNO(tel)){
            fixView.FixFailure("电话格式错误!");
            return;
        }

        RegisterBean registerBean=new RegisterBean(tel,pwd,Integer.valueOf(code));

        BaseRequest postRequest=getAutoRequest(POST);
        postRequest.setResultCallback(new BaseRequest.ResultCallback() {
            @Override
            public void Success(String mess) {
                final CommonJson<User> userCommonJson= CommonJson.fromJson(mess,User.class);
                MyApplication.mUser=userCommonJson.getData();
                User mUser= MyApplication.mUser;
                mUser.setLogin(true);
                mUser.setUsername(userCommonJson.getData().getUsername());
                mUser.setPassword(userCommonJson.getData().getPassword());
                mUser.setTime(System.currentTimeMillis()+"");

                //贷超数据
                mUser.setPic(userCommonJson.getData().getPic());
                mUser.setNickname(userCommonJson.getData().getNickname());
                mUser.setTel(userCommonJson.getData().getTel());
                mUser.setUser_id(userCommonJson.getData().getUser_id());
                UserPreferencesUtil.save(mUser);

                UserPreferencesUtil.save(mUser);
                postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        fixView.FixSuccess();
                    }
                });
            }

            @Override
            public void failure(final String mess) {
                MyApplication.mUser.clear();
                postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        fixView.FixFailure(mess);
                    }
                });
            }
        });
        postRequest.Url(DC.xiugaimima).Heads(false).BodyByKeyValue(registerBean).begin();
    }


    @Override
    public void detach() {
        fixView=null;
        super.detach();
    }
}
