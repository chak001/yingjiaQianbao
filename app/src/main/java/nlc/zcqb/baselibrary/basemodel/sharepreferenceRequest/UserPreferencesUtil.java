package nlc.zcqb.baselibrary.basemodel.sharepreferenceRequest;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import nlc.zcqb.app.application.MyApplication;
import nlc.zcqb.app.application.User.User;
import com.google.gson.Gson;

import nlc.zcqb.baselibrary.util.Constant;

public class UserPreferencesUtil {

    /**
     * 保存参数
     */
    public static void save(User user) {
        Gson gson = new Gson();
        String json = gson.toJson(user);
        Log.e("PreferencesUtil","save:"+json);
        SharedPreferences preferences = MyApplication.application.getSharedPreferences("user", Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString(Constant.USER_ID,json);
        editor.apply();
    }
    /**
     * 获取各项配置参数
     * @return
     */
    public static User getPreferences(){
        User user = null;
        SharedPreferences preferences = MyApplication.application.getSharedPreferences("user", Context.MODE_PRIVATE);
        String json=preferences.getString(Constant.USER_ID,"");
        Log.e("PreferencesUtil","get:"+json);
        if (json.equals("")){
            return null;
        }
        Gson gson = new Gson();
        user = gson.fromJson(json, User.class);//对于javabean直接给出class实例
        return user;
    }

    public static void clear(){
        Log.e("PreferencesUtil","clear:");
        SharedPreferences preferences = MyApplication.application.getSharedPreferences("user", Context.MODE_PRIVATE);
        Editor editor = preferences.edit();
        editor.putString(Constant.USER_ID,"");
        editor.apply();
    }
}
