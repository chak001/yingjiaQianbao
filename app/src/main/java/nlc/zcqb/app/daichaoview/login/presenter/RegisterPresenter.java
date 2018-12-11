package nlc.zcqb.app.daichaoview.login.presenter;

import nlc.zcqb.app.application.MyApplication;
import nlc.zcqb.app.application.User.User;
import nlc.zcqb.app.daichaoview.login.bean.LoginReqBean;
import nlc.zcqb.app.daichaoview.login.bean.RegisterBean;
import nlc.zcqb.app.daichaoview.login.viewcallback.RegisterView;
import nlc.zcqb.app.util.DC;
import nlc.zcqb.baselibrary.basemodel.BaseRequest;
import nlc.zcqb.baselibrary.basemodel.bean.CommonJson;
import nlc.zcqb.baselibrary.basemodel.sharepreferenceRequest.UserPreferencesUtil;
import nlc.zcqb.baselibrary.presenter.BasePresenter;
import nlc.zcqb.baselibrary.util.StringUtils;


/**
 * Created by lvqiu on 2018/10/20.
 */

public class RegisterPresenter extends BasePresenter {
    RegisterView registerView;

    public RegisterPresenter(RegisterView registerView) {
        this.registerView = registerView;
    }

    @Override
    public void attach() {

    }


    public void register(String tel,String code,String pwd){
        if (StringUtils.isNullOrEmpty(tel)){
            registerView.RegisterFailure("请填写电话");
            return;
        }

        if (StringUtils.isNullOrEmpty(code)){
            registerView.RegisterFailure("请填写验证码");
            return;
        }
        if (StringUtils.isNullOrEmpty(pwd)){
            registerView.RegisterFailure("请填写密码");
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
                        registerView.RegisterSuccess();
                    }
                });
            }

            @Override
            public void failure(final String mess) {
                MyApplication.mUser.clear();
                postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        registerView.RegisterFailure(mess);
                    }
                });
            }
        });
        postRequest.Url(DC.zhuce).Heads(false).BodyByKeyValue(registerBean).begin();
    }


    @Override
    public void detach() {
        registerView=null;
        super.detach();
    }
}
