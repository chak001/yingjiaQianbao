package com.example.waImageClip.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

/**
 * Created by lvqiu on 2018/12/3.
 */

public class MediaPickHelper {
    private final static int CLIPIMAGE=1112;
    private final static int GETIMAGE=1122;

    public static void  handleActivityResult(final Activity activity, int requestCode, int resultCode, Intent data, final ResultCallback callback) {
        if (resultCode== Activity.RESULT_OK){
            if (requestCode==GETIMAGE){
                Uri uri=data.getData();
                String path= util.getRealPathFromUri(activity,uri);
                // 意图实现activity的跳转
                Intent intent = new Intent(activity, ImageClipActivity.class);
                intent.putExtra("path",path);
                activity.startActivityForResult(intent,CLIPIMAGE);
            }else if(requestCode==CLIPIMAGE) {
                final String path = data.getStringExtra("path");
                if (callback!=null){
                    if (!new File(path).exists()){
                        callback.fail("无法选取图片");
                    }else {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                callback.success(path);
                            }
                        });

                    }
                }
            }
        }else if (resultCode==Activity.RESULT_CANCELED){
            Toast.makeText(activity,"取消选择图片！",Toast.LENGTH_SHORT).show();
        }
    }

    public static void pickImage(Activity activity){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        photoPickerIntent.setType("image/*");
        activity.startActivityForResult(photoPickerIntent, GETIMAGE);
    }


}
