package nlc.zcqb.app.application;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.provider.Settings;
import android.support.multidex.MultiDex;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import nlc.zcqb.app.application.User.ScreenParams;
import nlc.zcqb.app.application.User.User;
import nlc.zcqb.baselibrary.basemodel.sharepreferenceRequest.ObjPreferUtil;
import nlc.zcqb.baselibrary.basemodel.sharepreferenceRequest.UserPreferencesUtil;
import java.io.File;


/**
 * Created by lvqiu on 2017/10/26.
 */

public class MyApplication extends Application {
    public static Application application;
    public static User mUser;
    public static ScreenParams screenParams;
    public static String CachePath;
    private static String protocalUrl="";

    public static Application getApp() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application=this;

        MultiDex.install(this);
        application = getApp();
        mUser = UserPreferencesUtil.getPreferences();
        ObjPreferUtil<String> objPreferUtil=new ObjPreferUtil<>(String.class);
        protocalUrl=objPreferUtil.getPreferences("protocalUrl");
        if (mUser == null) {
            mUser = new User("","");
            mUser.setUser_id("");
            mUser.setLogin(false);
            UserPreferencesUtil.save(mUser);
        }
        mUser.setDeviceId(getDeviceId());
        screenParams = getScreenParams();
        CachePath = getApp().getBaseContext().getCacheDir().getParent() + File.separator + "file" + File.separator;
    }


    public ScreenParams getScreenParams() {
        Resources resources = getApp().getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        float density = dm.density;
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        ScreenParams screenParams = new ScreenParams(width, height,density);
        return screenParams;
    }

    @SuppressLint("ServiceCast")
    public String getDeviceId() {
        String id = "";
        int currentapiVersion = Build.VERSION.SDK_INT;
        if (currentapiVersion >= Build.VERSION_CODES.M) {     // Do something for froyo and above versions
            String m_szAndroidID = Settings.Secure.getString(getApp().getContentResolver(), Settings.Secure.ANDROID_ID);
            id = m_szAndroidID;
        } else {     // do something for phones running an SDK before froyo
            if (getSystemService(Context.TELEPHONY_SERVICE) instanceof String) {
                return (String) getSystemService(Context.TELEPHONY_SERVICE);
            } else {
                TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    id = tm.getDeviceId();
                }
            }
        }
        return id;
    }

    public static String getProtocalUrl() {
        return protocalUrl;
    }

    public static void setProtocalUrl(String protocalUrl) {
        MyApplication.protocalUrl = protocalUrl;
        ObjPreferUtil<String> objPreferUtil=new ObjPreferUtil<>(String.class);
        objPreferUtil.save(protocalUrl,"protocalUrl");
    }

}
