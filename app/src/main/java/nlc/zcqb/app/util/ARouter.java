package nlc.zcqb.app.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import nlc.zcqb.app.application.MyApplication;
import nlc.zcqb.app.daichaoview.fourth.view.HistoryActivity;
import nlc.zcqb.app.daichaoview.fourth.view.PayNoticeListActivity;
import nlc.zcqb.app.daichaoview.fourth.view.PersonalActivity;
import nlc.zcqb.app.daichaoview.fourth.view.ShouCangActivity;
import nlc.zcqb.app.daichaoview.login.LoginActivity;


/**
 * Created by lvqiu on 2018/10/14.
 */

public class ARouter {
    public static final String BEAN="BEAN";
    public static final String KEY="KEY";
    public static final String BUNDLE="BUNDLE";

    public static void jumpIn(Context context, Bundle bundle,Class activity){
        Intent intent= new Intent(context,activity);
        if (interceptor(activity)){
            if (!isLogin())  {
                intent=new Intent(context, LoginActivity.class);
                Toast.makeText(context,"未登录，请先登录再使用功能！",Toast.LENGTH_SHORT).show();
            }
        }
        intent.putExtras(bundle);
        context.startActivity(intent);
    }


    public static void jumpForResult(Context context, Bundle bundle,Class activity,int requestcode){
        Intent intent=new Intent(context,activity);
        intent.putExtras(bundle);
        ((Activity)context).startActivityForResult(intent,requestcode);
    }

    public static void jumpToQQ(Context context, String qqNum){
        if (checkApkExist(context, "com.tencent.mobileqq")){
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("mqqwpa://im/chat?chat_type=wpa&uin="+qqNum+"&version=1")));
        }else{
            Toast.makeText(context,"本机未安装QQ应用", Toast.LENGTH_SHORT).show();
        }
    }


    public static boolean checkApkExist(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static boolean isLogin(){
        if (MyApplication.mUser.getUser_id()!=null && MyApplication.mUser.isLogin()){
            return true;
        }
        return false;
    }

    public static boolean interceptor(Class activity) {
        if (activity.equals(HistoryActivity.class) ||
                activity.equals(ShouCangActivity.class) ||
                activity.equals(PayNoticeListActivity.class) ||
                activity.equals(PersonalActivity.class) ) {
            return true;
        }
        return false;
    }
}
