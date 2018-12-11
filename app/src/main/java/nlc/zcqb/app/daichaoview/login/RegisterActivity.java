package nlc.zcqb.app.daichaoview.login;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.customview.validateview.Codecallback;
import com.example.customview.validateview.GetCodeFactory;
import com.example.customview.validateview.GetTel;
import com.example.customview.validateview.ValidateView;

import ncl.zcqb.app.R;
import nlc.zcqb.app.daichaoview.common.GetCodePresenter;
import nlc.zcqb.app.daichaoview.login.presenter.RegisterPresenter;
import nlc.zcqb.app.daichaoview.login.viewcallback.RegisterView;
import nlc.zcqb.app.daichaoview.login.viewholder.TitleBarViewHolder;
import nlc.zcqb.app.daichaoview.main.MainActivity;
import nlc.zcqb.app.util.ARouter;
import nlc.zcqb.app.util.CheckBoxUtil;
import nlc.zcqb.baselibrary.baseview.BaseActivity;
import nlc.zcqb.baselibrary.util.Constant;

/**
 * Created by lvqiu on 2018/10/14.
 */

public class RegisterActivity extends BaseActivity implements  View.OnClickListener,Codecallback,RegisterView {
    private TitleBarViewHolder viewHolder;
    //code页面视图
    ValidateView codeview;
    EditText telnum,password;
    Button checkbotton;
    private GetCodePresenter codePresenter;
    private RegisterPresenter registerPresenter;
    private AppCompatCheckBox checkbox;

    @Override
    public int getLayoutId() {
        return R.layout.activity_dc_register;
    }

    @Override
    public void InitMenu() {
        viewHolder=new TitleBarViewHolder(this);
        viewHolder.titlename.setTextColor(getResources().getColor(R.color.black));
        viewHolder.titleBg.setImageDrawable(null);

        viewHolder.titleBg.setBackgroundResource(R.color.white);
        viewHolder.back.setVisibility(View.VISIBLE);
        viewHolder.back.setImageResource(R.mipmap.back_gray);
        viewHolder.back.setOnClickListener(this);
        viewHolder.titlename.setText(getResources().getText(R.string.register));

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            changeStatusBarTextColor(true);
        }else {
            viewHolder.title_layout.setBackgroundColor(getResources().getColor(R.color.white));
            setStatusColor(R.color.mosttransparentgray);
        }
    }
    @Override
    public void InitOthers() {
        registerPresenter=new RegisterPresenter(this);
        addPresenter(registerPresenter);


        checkbox=(AppCompatCheckBox) findViewById(R.id.checkbox);
        CheckBoxUtil.initView(checkbox);
        telnum= (EditText) findViewById(R.id.edit_username);
        codeview=(ValidateView) findViewById(R.id.Check_code);
        checkbotton=(Button) findViewById(R.id.checkbotton);
        checkbotton.setOnClickListener(this);
        password=(EditText) findViewById(R.id.edit_password);

        codeview.setGetTel(new GetTel() {
            @Override
            public String getTel() {
                return telnum.getText().toString();
            }
        });
        codeview.setFactory(new GetCodeFactory() {
            @Override
            public void getCode(String tel, String imei, String sim) {
                codePresenter=new GetCodePresenter(RegisterActivity.this);
                codePresenter.getCode(tel);
                addPresenter(codePresenter);
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
                this.finish();
                break;
            case R.id.checkbotton:
                toRegister();
                break;
        }
    }

    public void toRegister(){
        String tel= telnum.getText().toString();
        String code=codeview.getCode();
        String pwd= password.getText().toString();
        registerPresenter.register(tel,code,pwd);
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
    public void RegisterFailure(String mess) {
        Toast.makeText(this,mess,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void RegisterSuccess() {
        Bundle bundle=new Bundle();
        bundle.putInt(Constant.resultcode,Constant.REGISTER_SUCCESS);
        ARouter.jumpIn(this,bundle, MainActivity.class);
        Toast.makeText(this,"注册成功！",Toast.LENGTH_SHORT).show();
    }
}
