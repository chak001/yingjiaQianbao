package nlc.zcqb.wxlibrary.view;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.bumptech.glide.Glide;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXAppExtendObject;

import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;

import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.concurrent.ExecutionException;

import ncl.zcqb.app.R;
import nlc.zcqb.app.daichaoview.fourth.bean.ShareBean;
import nlc.zcqb.baselibrary.basemodel.sharepreferenceRequest.ObjPreferUtil;
import nlc.zcqb.wxlibrary.tool.BmpUtil;
import nlc.zcqb.wxlibrary.tool.Constants;
import nlc.zcqb.wxlibrary.uikit.CameraUtil;
import nlc.zcqb.wxlibrary.uikit.MMAlert;


public class SendToWXActivity extends  AppCompatActivity {

	private static final int THUMB_SIZE = 150;

	private static final String SDCARD_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();
	
	private IWXAPI api;
	private static final int MMAlertSelect1  =  0;
	private static final int MMAlertSelect2  =  1;
	private static final int MMAlertSelect3  =  2;
	private LinearLayout friendzone,weixin;
	private int mTargetScene = SendMessageToWX.Req.WXSceneSession;
	private ShareBean bean;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,  WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.send_to_wx);

		ObjPreferUtil<ShareBean> objPreferUtil=new ObjPreferUtil<>(ShareBean.class);
		bean= objPreferUtil.getPreferences("GETSHAREMESSAGE");

		initView();
	}

 	private void initView() {

		api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
		findViewById(R.id.wrap).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				SendToWXActivity.this.finish();
			}
		});
		weixin=(LinearLayout) findViewById(R.id.weixin);
		weixin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (bean==null){
					bean=new ShareBean("","盈家钱包","盈家钱包","http://www.baidu.com");
					sendWeixin(null);
				}else {
					getImage(bean.getLocalPath(), SendMessageToWX.Req.WXSceneTimeline);
				}
			}
		});
		friendzone=(LinearLayout) findViewById(R.id.friendzone);
		friendzone.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (bean==null){
					bean=new ShareBean("","盈家钱包","盈家钱包","http://www.baidu.com");
					sendFriendZone(null);
				}else {
					getImage(bean.getLocalPath(), SendMessageToWX.Req.WXSceneSession);
				}
			}
		});

  	}


  	public void getImage(final String path, final int type){
		new AsyncTask<String,Integer,Bitmap>(){

			@Override
			protected void onPostExecute(Bitmap bitmap) {
				if (SendMessageToWX.Req.WXSceneSession==type){
					sendFriendZone(bitmap);
				}else if (SendMessageToWX.Req.WXSceneTimeline==type){
					sendWeixin(bitmap);
				}
			}

			@Override
			protected Bitmap doInBackground(String... strings) {
				Bitmap bitmap=null;
				try {
					bitmap= Glide.with(SendToWXActivity.this)
							.load(path)
							.asBitmap()
							.into(200, 200)
							.get();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
				return bitmap;
			}
		}.execute();
	}
	public void sendFriendZone(Bitmap bitmap){
		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = bean.getUrl();
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title =  bean.getTitle();
		msg.description = bean.getIntrod();
		if(bitmap==null) {
			Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.app_icon);
			Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
			bmp.recycle();
			msg.thumbData = BmpUtil.bmpToByteArray(thumbBmp, true);
		}else {
			msg.thumbData = BmpUtil.bmpToByteArray(bitmap, true);
		}
		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("webpage");
		req.message = msg;
		req.scene =  SendMessageToWX.Req.WXSceneSession;
		api.sendReq(req);
		finish();
	}

  	public void sendWeixin(Bitmap bitmap){

		WXWebpageObject webpage = new WXWebpageObject();
		webpage.webpageUrl = bean.getUrl();
		WXMediaMessage msg = new WXMediaMessage(webpage);
		msg.title = bean.getTitle();
		msg.description =bean.getIntrod();
		if (bitmap==null) {
			Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.app_icon);
			Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
			bmp.recycle();
			msg.thumbData = BmpUtil.bmpToByteArray(thumbBmp, true);
		}else {
			msg.thumbData = BmpUtil.bmpToByteArray(bitmap, true);
		}

		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("webpage");
		req.message = msg;
		req.scene =   SendMessageToWX.Req.WXSceneTimeline;
		api.sendReq(req);
		finish();
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		switch (requestCode) {

		case 0x101: {
			if (resultCode == RESULT_OK) {
				final WXAppExtendObject appdata = new WXAppExtendObject();
				final String path = CameraUtil.getResultPhotoPath(this, data, SDCARD_ROOT + "/tencent/");
				appdata.filePath = path;
				appdata.extInfo = "this is ext info";

				final WXMediaMessage msg = new WXMediaMessage();
				msg.setThumbImage(BmpUtil.extractThumbNail(path, 150, 150, true));
				msg.title = "this is title";
				msg.description = "this is description";
				msg.mediaObject = appdata;

				SendMessageToWX.Req req = new SendMessageToWX.Req();
				req.transaction = buildTransaction("appdata");
				req.message = msg;
				req.scene = mTargetScene;
				api.sendReq(req);

				finish();
			}
			break;
		}
		default:
			break;
		}
	}

	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
	}
	
	public void onRadioButtonClicked(View view) {
		if (!(view instanceof RadioButton)) {
			return;
		}

		boolean checked = ((RadioButton) view).isChecked();

		switch (view.getId()) {
//		case R.id.target_scene_session:
//			if (checked) {
//				mTargetScene = SendMessageToWX.Req.WXSceneSession;
//			}
//			break;
//		case R.id.target_scene_timeline:
//			if (checked) {
//				mTargetScene = SendMessageToWX.Req.WXSceneTimeline;
//			}
//			break;
//		case R.id.target_scene_favorite:
//			if (checked) {
//				mTargetScene = SendMessageToWX.Req.WXSceneFavorite;
//			}
//			break;
		}
	}
}
