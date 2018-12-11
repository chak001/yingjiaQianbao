package nlc.zcqb.baselibrary.util;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by lvqiu on 2017/6/11.
 */

public class Copyutil {

    public static boolean copyApkFromAssets(Context context, String fileName ) {
        boolean copyIsFinish = false;
        String  path= context.getExternalCacheDir() + File.separator + "skin.apk";
        try {
            InputStream is = context.getAssets().open(fileName);
            File file = new File(path);
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            byte[] temp = new byte[1024];
            int i = 0;
            while ((i = is.read(temp)) > 0) {
                fos.write(temp, 0, i);
            }
            fos.close();
            is.close();
            copyIsFinish = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return copyIsFinish;
    }

    public static boolean isExistFile(String path){
        File f=new File(path);
        return f.exists();
    }
}
