package nlc.zcqb.baselibrary.util.helper;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;



/**
 * Created by lvqiu on 2017/11/11.
 */

public class RequestPermissonHelper {

    public final static int  UPDATE_APP=15556;
    public final static int REQUEST_COARSE_Permission=13485;
    public final static int  REQUEST_GPS_Permission=13222;

    public final static int GETPERMISSION=10211;

    public final static int REQUEST_camera_Permission=11485;

    public final static int REQUEST_Read_Permission=15555;
    public final static int  REQUEST_write_Permission=15552;
    /**
     * 请求定位
     * @param context
     * @return
     */
    public static boolean RequestLocation(Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            int check_COARSE_Permission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION);
            int check_GPS_Permission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
            Log.e("RequestPermission", "请求权限之前");
            if (check_COARSE_Permission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_COARSE_Permission);
                Log.e("RequestPermission", "正在请求网络定位权限！");
            }
            if (check_GPS_Permission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_GPS_Permission);
                Log.e("RequestPermission", "正在请GPS定位权限！");
            }
            if (check_COARSE_Permission == PackageManager.PERMISSION_GRANTED && check_GPS_Permission == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
            return false;
        } else {
            return true;
        }
    }

    /**
     * 请求存储
     * @param context
     * @return
     */
    public static boolean RequestStore(Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            int  Permission_w = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int  Permission_r = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
            Log.e("RequestPermission", "请求权限之前");
            if (Permission_w != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_write_Permission);
                Log.e("RequestPermission", "正在请求权限！");
            }
            if (Permission_r != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_Read_Permission);
                Log.e("RequestPermission", "正在请权限！");
            }
            if (Permission_w == PackageManager.PERMISSION_GRANTED && Permission_r == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
            return false;
        } else {
            return true;
        }
    }




    /**
     * 请求摄像头
     * @param context
     * @return
     */
    public static boolean RequestCamera(Context context) {
        if (Build.VERSION.SDK_INT >= 23) {
            int  Permission_camare = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);

            Log.e("RequestPermission", "请求权限之前");
            if (Permission_camare != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, REQUEST_camera_Permission);
                Log.e("RequestPermission", "正在请求 权限！");
            }

            if (Permission_camare == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
            return false;
        } else {
            return true;
        }
    }
}
