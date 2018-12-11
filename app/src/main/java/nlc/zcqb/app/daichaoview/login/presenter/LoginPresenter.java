package nlc.zcqb.app.daichaoview.login.presenter;

import com.example.customview.util;

import nlc.zcqb.app.application.MyApplication;
import nlc.zcqb.app.application.User.User;
import nlc.zcqb.app.daichaoview.login.bean.LoginReqBean;
import nlc.zcqb.app.daichaoview.login.viewcallback.LoginView;
import nlc.zcqb.app.util.DC;
import nlc.zcqb.baselibrary.basemodel.BaseRequest;
import nlc.zcqb.baselibrary.basemodel.bean.CommonJson;
import nlc.zcqb.baselibrary.basemodel.sharepreferenceRequest.UserPreferencesUtil;
import nlc.zcqb.baselibrary.presenter.BasePresenter;
import static nlc.zcqb.app.daichaoview.login.bean.LoginReqBean.CodeType;
import static nlc.zcqb.app.daichaoview.login.bean.LoginReqBean.PwdType;


/**
 * Created by lvqiu on 2017/11/8.
 */

public class LoginPresenter extends BasePresenter{
    private LoginView loginView;

    public LoginPresenter(LoginView loginView) {
        super();
        this.loginView = loginView;
    }

    @Override
    public void attach() {

    }


    public void loginByCode(final String username, final String code){
        if (!util.isMobileNO(username)){
            loginView.LoginFailure("手机号码格式错误！");
            return;
        }
        if (!util.isNumeric(code) || code.length()<2){
            loginView.LoginFailure("验证码格式错误！");
            return;
        }

        LoginReqBean loginBean=new LoginReqBean(username,null,CodeType,Integer.valueOf(code));

        BaseRequest postRequest=getAutoRequest(POST);
        postRequest.setResultCallback(new BaseRequest.ResultCallback() {
            @Override
            public void Success(String mess) {
                final CommonJson<User> userCommonJson= CommonJson.fromJson(mess,User.class);
                initUserData(userCommonJson);
                postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        loginView.LoginSuccess();
                    }
                });
            }

            @Override
            public void failure(final String mess) {
                MyApplication.mUser.clear();
                postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        loginView.LoginFailure(mess);
                    }
                });
            }
        });
        postRequest.Url(DC.denglu).Heads(false).BodyByKeyValue(loginBean).begin();
    }

    public void login(final String username, final String password){
        if (!util.isMobileNO(username)){
            loginView.LoginFailure("手机号码格式错误！");
            return;
        }

        LoginReqBean loginBean=new LoginReqBean(username,password,PwdType,null);

        BaseRequest postRequest=getAutoRequest(POST);
        postRequest.setResultCallback(new BaseRequest.ResultCallback() {
            @Override
            public void Success(String mess) {
                final CommonJson<User> userCommonJson= CommonJson.fromJson(mess,User.class);
                initUserData(userCommonJson);
                postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        loginView.LoginSuccess();
                    }
                });
            }

            @Override
            public void failure(final String mess) {
                postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        loginView.LoginFailure(mess);
                    }
                });
            }
        });
        postRequest.Url(DC.denglu).Heads(false).BodyByKeyValue(loginBean).begin();
    }


    public void LogOut(String username){

    }

    public void TestToken(){

    }


    public void initUserData(CommonJson<User> userCommonJson){
        MyApplication.mUser=userCommonJson.getData();
        User mUser= MyApplication.mUser;
        //之前微易聊数据
        mUser.setLongToken(userCommonJson.getData().getLongToken());
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
    }

    @Override
    public void detach() {
        loginView=null;
        super.detach();
    }

}
