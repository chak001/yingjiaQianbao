package nlc.zcqb.app.daichaoview.service;


import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import ncl.zcqb.app.R;
import nlc.zcqb.app.event.progressEvent;
import nlc.zcqb.baselibrary.basemodel.netRequest.DownloadFileRequest;

import static nlc.zcqb.app.daichaoview.service.UploadService.EXTRA_PATH;
import static nlc.zcqb.app.daichaoview.service.UploadService.KEY;
import static nlc.zcqb.app.event.CommandEvent.DOWN_APP;
import static nlc.zcqb.baselibrary.basemodel.netRequest.DownloadFileRequest.HTTP_SUCCESS;


public class UpdateService extends Service {

	public static final String ACTION_DOWNAPP="action.DOWN_APP";
	private NotificationCompat.Builder builder;
	private NotificationManager manager;
	private int count=0;
	private Handler mHandler;
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}



	public static void startDownLoad(Context context, String path, String key)
	{
		Intent intent = new Intent(context, UpdateService.class);
		intent.setAction(ACTION_DOWNAPP);
		intent.putExtra(EXTRA_PATH, path);
		intent.putExtra(KEY, key);
		context.startService(intent);

		Log.d("startDownLoad","资源："+path);

	}
	/**
	 * Called by the system to notify a Service that it is no longer used and is being removed.  The
	 * service should clean up any resources it holds (threads, registered
	 * receivers, etc) at this point.  Upon return, there will be no more calls
	 * in to this Service object and it is effectively dead.  Do not call this method directly.
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();
		if (manager!=null)
			manager.cancelAll();
		if (mHandler!=null)
			mHandler.removeCallbacksAndMessages(null);
		mHandler=null;
	}

	/**
	 * Called by the system when the service is first created.  Do not call this method directly.
	 */
	@Override
	public void onCreate() {
		super.onCreate();
		mHandler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				 switch (msg.what){
					 case DownloadFileRequest.HTTP_FAIL:
					 	break;
					case DownloadFileRequest.HTTP_PROGRESS:
						int progress= (int) msg.obj;
						int code= msg.arg1;
						updateStatus(progress,code);
						break;
					 case HTTP_SUCCESS:
						 updateStatus(100, msg.arg1);
						 EventBus.getDefault().post(new progressEvent(DOWN_APP,(String) msg.obj,"",100));
					 	break;
				 }
			}
		};
		String channelId = "download";
		String channelName = "下载app";
		int importance = NotificationManager.IMPORTANCE_HIGH;
	    createNotificationChannel(this, channelId, channelName, importance);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent != null) {
			try {

				String action=intent.getStringExtra(ACTION_DOWNAPP);
				String key=intent.getStringExtra(KEY);
				String down_url = intent.getStringExtra(EXTRA_PATH);
  				// 创建通知
				int code=createNotification();
				// 开始下载
				downloadUpdateFile(down_url,code);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return super.onStartCommand(intent, flags, startId);
	}


	public synchronized int createNotification() {
		count++;
		manager = (NotificationManager) this.getSystemService(this.NOTIFICATION_SERVICE);
		Notification notification=sendSubscribeMsg();
		manager.notify(count,notification);
		return count;
	}

	public void updateStatus(int progress,int code){
		String title="下载文件更新包";
		String content="";
		if (progress==100){
			content="下载完成，点击安装！";
		}else {
			content="下载进度：" + progress + "%";
		}

 		Notification notification=null;
 		if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
			notification = new android.support.v4.app.NotificationCompat.Builder(this, "download")
					.setContentTitle(title)
					.setContentText(content)
					.setSmallIcon(R.mipmap.app_icon)
					.setProgress(100, progress, false)
					.setWhen(System.currentTimeMillis())
					.setAutoCancel(false)
					.setOngoing(true)
					.build();
		}else {
			builder = new NotificationCompat.Builder(this);
			builder.setContentTitle(title);
			builder.setContentText(content);
			builder.setProgress(100, progress, false);
			builder.setAutoCancel(false);
			builder.setSmallIcon(R.mipmap.app_icon);
			//设置为不可清除模式
			builder.setOngoing(true);
			notification=builder.build();
		}
		manager.notify(code,notification);
	}

	public  Notification sendSubscribeMsg() {
		Notification notification=null;
		if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
			notification = new android.support.v4.app.NotificationCompat.Builder(this, "download")
					.setContentTitle("下载文件更新包")
					.setContentText("下载进度！")
					.setWhen(System.currentTimeMillis())
					.setSmallIcon(R.mipmap.app_icon)
					.setAutoCancel(true)
					.build();
		}else {
			builder = new NotificationCompat.Builder(this);
			builder.setAutoCancel(false);
			//设置为不可清除模式
			builder.setOngoing(true);
			builder.setSmallIcon(R.mipmap.min_appicon);
			builder.setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.min_appicon));
			builder.setContentTitle("下载文件更新包");
			builder.setContentText("下载进度");
			notification=builder.build();
		}

		return notification;
	}

	@TargetApi(Build.VERSION_CODES.O)
	public static void createNotificationChannel(Context context,String channelId, String channelName, int importance) {

		if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
			NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
			NotificationManager notificationManager = (NotificationManager) context.getSystemService(
					context.NOTIFICATION_SERVICE);
			notificationManager.createNotificationChannel(channel);
		}

	}

	/***
	 * 下载文件
	 */
	public void downloadUpdateFile(final String down_url,int code) throws Exception {
		 DownloadFileRequest request=new DownloadFileRequest(mHandler,down_url,code);
		 request.DownLoadFile();
	}

}