package nlc.zcqb.app.daichaoview.login;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.customview.tabmenu.ClickCallback;
import com.example.customview.tabmenu.TabsMenu;
import com.example.customview.validateview.Codecallback;
import com.example.customview.validateview.GetCodeFactory;
import com.example.customview.validateview.GetTel;
import com.example.customview.validateview.ValidateView;

import org.greenrobot.eventbus.EventBus;

import ncl.zcqb.app.R;
import nlc.zcqb.app.daichaoview.common.GetCodePresenter;
import nlc.zcqb.app.daichaoview.login.presenter.LoginPresenter;
import nlc.zcqb.app.daichaoview.login.viewcallback.LoginView;
import nlc.zcqb.app.daichaoview.login.viewholder.TitleBarViewHolder;
import nlc.zcqb.baselibrary.event.CommandEvent;
import nlc.zcqb.app.util.ARouter;
import nlc.zcqb.app.util.CheckBoxUtil;
import nlc.zcqb.baselibrary.baseview.BaseActivity;


/**
 * Created by lvqiu on 2018/10/14.
 */

public class LoginActivity extends BaseActivity  implements View.OnClickListener ,Codecallback,LoginView {
    TabsMenu tabsMenu;
    ViewPager viewPager;
    TitleBarViewHolder viewHolder;
    View[] loginWraps=new View[2];
    private TextView register;
    private TextView fixpwd;
    private Button checkbotton;
    private int position=0;
    private GetCodePresenter codePresenter;
    private LoginPresenter loginPresenter;

    //code页面视图
    ValidateView codeview;
    EditText telnum1;
    //密码页面视图
    EditText telnum2;
    EditText pwd;
    AppCompatCheckBox checkbox;


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_dc_login;
    }

    @Override
    public void InitMenu() {
        viewHolder=new TitleBarViewHolder(this);
        viewHolder.titlename.setTextColor(getResources().getColor(R.color.black));
        viewHolder.back.setImageResource(R.mipmap.back_gray);
        viewHolder.back.setVisibility(View.VISIBLE);
        viewHolder.titleBg.setImageDrawable(null);

        viewHolder.titleBg.setBackgroundResource(R.color.white);
        viewHolder.back.setOnClickListener(this);
        viewHolder.titlename.setText(getResources().getText(R.string.login));

        viewHolder.title_layout.setBackgroundColor(getResources().getColor(R.color.white));
        viewHolder.rootview.setBackgroundColor(getResources().getColor(R.color.hintcolor));

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            changeStatusBarTextColor(true);
        }else {
            viewHolder.title_layout.setBackgroundResource(R.color.white);
            setStatusColor(R.color.mosttransparentgray);
        }

    }


    @Override
    public void InitOthers() {
        loginPresenter=new LoginPresenter(this);
        addPresenter(loginPresenter);

        checkbox=(AppCompatCheckBox) findViewById(R.id.checkbox);
        CheckBoxUtil.initView(checkbox);
        register=(TextView) findViewById(R.id.register);
        register.setOnClickListener(this);
        checkbotton=(Button)findViewById(R.id.checkbotton);
        checkbotton.setOnClickListener(this);

        tabsMenu=(TabsMenu) findViewById(R.id.tabs_view);
        tabsMenu.initView(new String[]{getResources().getString(R.string.codelogin),getResources().getString(R.string.pwdlogin)});
        tabsMenu.setCurrentPosition(0);

        View view1= LayoutInflater.from(this).inflate(R.layout.login_item1,null);
        loginWraps[0]=view1;
        codeview=(ValidateView) view1.findViewById(R.id.edit_code);
        telnum1=(EditText) view1.findViewById(R.id.edit_username);
        codeview.setGetTel(new GetTel() {
            @Override
            public String getTel() {
                return telnum1.getText().toString();
            }
        });
        codeview.setFactory(new GetCodeFactory() {
            @Override
            public void getCode(String tel, String imei, String sim) {
                codePresenter=new GetCodePresenter(LoginActivity.this);
                codePresenter.getCode(tel);
                addPresenter(codePresenter);
            }
        });

        View view2= LayoutInflater.from(this).inflate(R.layout.login_item2,null);
        loginWraps[1]=view2;
        telnum2=(EditText) view2.findViewById(R.id.username);
        pwd=(EditText) view2.findViewById(R.id.password);
        fixpwd=(TextView) view2.findViewById(R.id.fixpwd);
        fixpwd.setOnClickListener(this);

        viewPager=(ViewPager) findViewById(R.id.Login_viewpager);
        viewPager.setCurrentItem(0);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return loginWraps.length;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                if (loginWraps!=null && container.equals(loginWraps[position].getParent())){
                    container.removeView(loginWraps[position]);
                }
                container.addView(loginWraps[position],new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                return loginWraps[position];
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                if (view.equals(object)){
                    return true;
                }
                return false;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabsMenu.setCurrentPosition(position);
                LoginActivity.this.position=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabsMenu.setClickCallback(new ClickCallback() {
            @Override
            public void click(int position) {
                viewPager.setCurrentItem(position);
            }
        });
    }

    @Override
    public void parseIntent() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_action:
                doFinish(RESULT_CANCELED);
                break;
            case R.id.register:
                ARouter.jumpIn(this,new Bundle(),RegisterActivity.class);
                break;
            case R.id.fixpwd:
                ARouter.jumpIn(this,new Bundle(),FixPwdActivity.class);
                break;
            case R.id.checkbotton:
                goToLogin();
                break;
        }
    }

    public void goToLogin(){
        if (position==0){
            String tel= telnum1.getText().toString();
            String cod= codeview.getCode();
            loginPresenter.loginByCode(tel,cod);
        }else {
            String tel= telnum2.getText().toString();
            String password= pwd.getText().toString();
            loginPresenter.login(tel,password);
        }
    }

    public void doFinish(int resultCode){
        //setResult(resultCode, data);第一个参数表示结果返回码，一般只要大于1就可以，但是
        setResult(resultCode, new Intent());
        finish(); //结束当前的activity的生命周期
    }

    @Override
    public void success(String key) {
        Toast.makeText(this,"获取验证码为"+key,Toast.LENGTH_SHORT).show();
        codeview.setCode(key);
    }

    @Override
    public void failure(String key) {
        Toast.makeText(this,"获取验证码失败！",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void LoginSuccess() {
        doFinish(RESULT_OK);
        EventBus.getDefault().post(new CommandEvent(CommandEvent.LOGIN_SUCCESS,getRunningActivityName()));
    }

    @Override
    public void LoginFailure(String mess) {
        Toast.makeText(this,"登陆失败："+mess,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void clear() {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        doFinish(RESULT_CANCELED);
    }
}
