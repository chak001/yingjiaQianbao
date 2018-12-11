package nlc.zcqb.app.daichaoview.login;

import android.os.Build;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.customview.validateview.Codecallback;
import com.example.customview.validateview.GetCodeFactory;
import com.example.customview.validateview.GetTel;
import com.example.customview.validateview.ValidateView;

import ncl.zcqb.app.R;

import nlc.zcqb.app.application.MyApplication;
import nlc.zcqb.app.daichaoview.common.GetCodePresenter;
import nlc.zcqb.app.daichaoview.login.presenter.FixPresenter;
import nlc.zcqb.app.daichaoview.login.viewcallback.FixView;
import nlc.zcqb.app.daichaoview.login.viewholder.TitleBarViewHolder;
import nlc.zcqb.app.util.CheckBoxUtil;
import nlc.zcqb.baselibrary.baseview.BaseActivity;

/**
 * Created by lvqiu on 2018/10/14.
 */

public class FixPwdActivity extends BaseActivity implements View.OnClickListener,Codecallback,FixView {
    private TitleBarViewHolder viewHolder;
    private EditText telnum ,newpwd;
    private ValidateView codeview;
    private Button fixbutton;
    private GetCodePresenter codePresenter;
    private FixPresenter fixPresenter;
    private AppCompatCheckBox checkbox;

    @Override
    public int getLayoutId() {
        return R.layout.activity_dc_fixpwd;
    }

    @Override
    public void InitMenu() {
        viewHolder=new TitleBarViewHolder(this);

        viewHolder.titleBg.setImageDrawable(null);

        viewHolder.titleBg.setBackgroundResource(R.color.white);
        viewHolder.back.setVisibility(View.VISIBLE);
        viewHolder.back.setImageResource(R.mipmap.back_gray);
        viewHolder.back.setOnClickListener(this);

        viewHolder.titlename.setText(R.string.fixpwd);
        viewHolder.titlename.setTextColor(getResources().getColor(R.color.black));


        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            changeStatusBarTextColor(true);
        }else {
            viewHolder.title_layout.setBackgroundColor(getResources().getColor(R.color.white));
            setStatusColor(R.color.mosttransparentgray);
        }
    }

    @Override
    public void InitOthers() {
        fixPresenter=new FixPresenter(this);
        addPresenter(fixPresenter);

        checkbox=(AppCompatCheckBox) findViewById(R.id.checkbox);
        CheckBoxUtil.initView(checkbox);
        fixbutton=(Button) findViewById(R.id.checkbotton);
        fixbutton.setOnClickListener(this);
        telnum=(EditText) findViewById(R.id.edit_username);
        codeview=(ValidateView) findViewById(R.id.Check_code);
        newpwd=(EditText) findViewById(R.id.edit_password);
        codeview.setGetTel(new GetTel() {
            @Override
            public String getTel() {
                return telnum.getText().toString();
            }
        });
        codeview.setFactory(new GetCodeFactory() {
            @Override
            public void getCode(String tel, String imei, String sim) {
                codePresenter=new GetCodePresenter(FixPwdActivity.this);
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
                String tel= telnum.getText().toString();
                String code= codeview.getCode();
                String password= newpwd.getText().toString();
                fixPresenter.fixPassword(tel,code,password);
                break;

        }
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
    public void FixFailure(String mess) {
        Toast.makeText(this,"修改失败！"+mess,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void FixSuccess() {
        Toast.makeText(this,"修改成功！",Toast.LENGTH_SHORT).show();
        MyApplication.mUser.setLogin(false);
        MyApplication.mUser.setUser_id(null);
        this.finish();
    }
}
