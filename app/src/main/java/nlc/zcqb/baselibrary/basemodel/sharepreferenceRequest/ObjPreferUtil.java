package nlc.zcqb.baselibrary.basemodel.sharepreferenceRequest;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import nlc.zcqb.app.application.MyApplication;
import com.google.gson.Gson;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by lvqiu on 2017/11/10.
 */

public class ObjPreferUtil<T> implements Serializable{
    private Class<T> tClass;


    public ObjPreferUtil(Class<T> tClass ) {
        this.tClass = tClass;
    }

    /**
     * 保存参数
     */
    public   void save(T object, String key ) {
        Gson gson = new Gson();
        String json=toJson(tClass,object);

        Log.e("ObjPreferUtil","save:"+json);
        SharedPreferences preferences = MyApplication.application.getSharedPreferences(tClass.getName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,json);
        editor.apply();
    }
    /**
     * 获取各项配置参数
     * @return
     */
    public   T  getPreferences( String key ){

        SharedPreferences preferences = MyApplication.application.getSharedPreferences(tClass.getName(), Context.MODE_PRIVATE);
        String json=preferences.getString(key,"");
        Log.e("ObjPreferUtil","get:"+json);
        if (json.equals("")){
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(json, tClass);//对于javabean直接给出class实例
    }

    public   void clear( String key ){
        Log.e("ObjPreferUtil","clear:");
        SharedPreferences preferences = MyApplication.application.getSharedPreferences(tClass.getName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,"");
        editor.apply();
    }

    private   T  fromJson(String json, Class<T> clazz) {
        Gson gson = new Gson();
        Type objectType = type(clazz, clazz);
        return gson.fromJson(json, objectType);
    }

    private String toJson(Class<T> clazz, T object) {
        Gson gson = new Gson();
        Type objectType = type(clazz, clazz);
        return gson.toJson(object, objectType);
    }

    private ParameterizedType type(final Class raw, final Type... args) {
        return new ParameterizedType() {
            public Type getRawType() {
                return raw;
            }

            public Type[] getActualTypeArguments() {
                return args;
            }

            public Type getOwnerType() {
                return null;
            }
        };
    }
}
