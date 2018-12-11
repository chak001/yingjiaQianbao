package nlc.zcqb.app.daichaoview.main;

import android.os.Bundle;
import android.os.Handler;

import java.util.List;

import ncl.zcqb.app.R;
import nlc.zcqb.app.util.ARouter;
import nlc.zcqb.baselibrary.baseview.BaseActivity;
import nlc.zcqb.baselibrary.util.helper.EasyPermissonHelper;

/**
 * Created by lvqiu on 2018/12/1.
 */

public class GuideActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_guodu;
    }

    @Override
    public void InitMenu() {
        changeStatusBarTextColor(true);
    }

    @Override
    public void InitOthers() {
        EasyPermissonHelper.RequestStore(this);
    }

    @Override
    public void parseIntent() {

    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> list) {
        super.onPermissionsGranted(requestCode, list);
        delayJump();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> list) {
        super.onPermissionsDenied(requestCode, list);
        delayJump();
    }

    public void delayJump(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ARouter.jumpIn(GuideActivity.this,new Bundle(),MainActivity.class);
                GuideActivity.this.finish();
            }
        },1000);
    }
}

