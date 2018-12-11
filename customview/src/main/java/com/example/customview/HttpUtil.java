package com.example.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;



public class HttpUtil {

	public static Bitmap getLocalVideoThumbnail(String path){

		Bitmap bitmap=null;
		MediaMetadataRetriever retriever= new MediaMetadataRetriever();

		retriever.setDataSource(path);
		bitmap=retriever.getFrameAtTime();
		retriever.release();
		return bitmap;
	}


	/**使用第三方库加载图片
	 * flag 变量是：是否设置加载图片过程可以控制停止开始
	 */
	public static   void loadimage(Uri uri, ImageView image, Context context ){

		Glide
				.with(context)
				.load(uri)
				.crossFade(200)
				.error(R.mipmap.icon_error)
				.into(image );
	}

	/**使用第三方库加载图片
	 * flag 变量是：是否设置加载图片过程可以控制停止开始
	 */
	public static   void loadimage(String url, ImageView image, Context context){
 		Glide
				.with(context)
				.load(url)
				.asBitmap()
				.centerCrop()
				.placeholder(R.mipmap.defaultpic)
				.error(R.mipmap.icon_error)
				.into(image );
 	}


	public static void Glide_loadRoundimage(final String url, final ImageView imageView, final Context context ){
		Glide.with(context).load(url).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView) {
			@Override
			public void onLoadStarted(Drawable placeholder) {
				super.onLoadStarted(placeholder);
				imageView.setImageResource(R.mipmap.user_defaulticon);
			}

			@Override
			public void onLoadFailed(Exception e, Drawable errorDrawable) {
				super.onLoadFailed(e, errorDrawable);
				imageView.setImageResource(R.mipmap.user_defaulticon);
			}

			@Override
			protected void setResource(Bitmap resource) {

				RoundedBitmapDrawable circularBitmapDrawable =
						RoundedBitmapDrawableFactory.create(context.getResources(), resource);
				circularBitmapDrawable.setCircular(true);
				if (circularBitmapDrawable!=null)
					imageView.setImageDrawable(circularBitmapDrawable);
				else {
					imageView.setImageResource(R.mipmap.user_defaulticon);
				}
			}
		});
	}


	public static void  loadImage_default(String url, ImageView imageView, Context context,int defaultimage){
			if (url!=null && !url.equals("")) {
				Glide
						.with(context)
						.load(url)
						.thumbnail(0.1f)
						.centerCrop()
						.error(R.mipmap.icon_error)
						.into(imageView);
			} else {
				imageView.setImageResource(defaultimage);
			}
	}
}
