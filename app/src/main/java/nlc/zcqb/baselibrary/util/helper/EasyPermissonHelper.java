package nlc.zcqb.baselibrary.util.helper;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import pub.devrel.easypermissions.EasyPermissions;


/**
 * Created by lvqiu on 2017/11/11.
 */

public class EasyPermissonHelper {
    public final static String[] permsStore = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    public final static String[] permsLocation = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    public final static String[] permsCamera = {Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO};


    public final static int REQUEST_Location_Permission=13485;
    public final static int REQUEST_camera_Permission=11485;
    public final static int REQUEST_write_Read_Permission=15555;

    public static boolean contain(int code){
        if (code!=REQUEST_camera_Permission
                && code!=REQUEST_Location_Permission
                && code!= REQUEST_write_Read_Permission){
            return false;
        }else {
            return true;
        }
    }

    public static boolean hasStorePermission(Activity activity){
        return EasyPermissions.hasPermissions(activity,EasyPermissonHelper.permsStore);
    }

    public static boolean hasLocationPermission(Activity activity){
        return EasyPermissions.hasPermissions(activity,EasyPermissonHelper.permsLocation);
    }

    public static boolean hasCameraPermission(Activity activity){
        return EasyPermissions.hasPermissions(activity,EasyPermissonHelper.permsCamera);
    }

    /**
     * 请求定位
     * @param context
     * @return
     */
    public static void RequestLocation(Context context) {
        EasyPermissions.requestPermissions(
                (Activity) context,
                "申请权限",
                REQUEST_Location_Permission,
                permsLocation[0],
                permsLocation[1]);
    }

    /**
     * 请求存储
     * @param context
     * @return
     */
    public static void RequestStore(Context context) {
        EasyPermissions.requestPermissions(
                (Activity) context,
                "申请权限",
                REQUEST_write_Read_Permission,
                permsStore[0],
                permsStore[1]);
    }




    /**
     * 请求摄像头
     * @param context
     * @return
     */
    public static void RequestCamera(Context context) {
        EasyPermissions.requestPermissions(
                (Activity) context,
                "申请权限",
                REQUEST_camera_Permission,
                permsCamera[0],
                permsCamera[1]);
    }
}
