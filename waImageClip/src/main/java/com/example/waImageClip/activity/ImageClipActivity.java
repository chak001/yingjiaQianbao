package com.example.waImageClip.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.edmodo.cropper.CropImageView;
import com.v2113723766.yqc.example.waImageClip.R;


import java.io.File;
import java.io.IOException;


public class ImageClipActivity extends AppCompatActivity {
    CropImageView cropImageView;
    private String imagepath="";

    @Override
    protected void onResume() {
        super.onResume();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clipimage);
        imagepath=getIntent().getStringExtra("path");
        if (!new File(imagepath).exists()){
            Toast.makeText(this,"文件不存在!",Toast.LENGTH_SHORT).show();
            this.finish();
        }
        InitView();
    }

    public void InitView(){
        cropImageView=(CropImageView) findViewById(R.id.clip_ImageView);
        cropImageView.setAspectRatio(5, 10);
        cropImageView.setFixedAspectRatio(false);
        cropImageView.setGuidelines(1);
        requestBitmap();
    }


    public void requestBitmap(){

        Bitmap bitmap= null;
       {
            bitmap = BitmapFactory.decodeFile(imagepath);
        }
        cropImageView.setImageBitmap(bitmap);

    }


    public void rotate(View view){

        cropImageView.rotateImage(90);
    }

    public void getImage(View view){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap croppedImage = cropImageView.getCroppedImage();
                Bitmap temp=util.getZoomImage(croppedImage,200 );
                final String path= util.saveBitmap(ImageClipActivity.this,temp);
                ImageClipActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent=new Intent();
                        intent.putExtra("path",path);
                        ImageClipActivity.this.setResult(RESULT_OK,intent);
                        ImageClipActivity.this.finish();
                    }
                });
            }
        }).start();
    }

    public void goback(View view){

        Intent intent=new Intent();
        ImageClipActivity.this.setResult(RESULT_CANCELED,intent);
        this.finish();
    }
}
