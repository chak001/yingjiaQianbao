package nlc.zcqb.baselibrary.baseview;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.example.customview.pickerwidget.PickTimeView;
import com.example.customview.pickerwidget.PickValueView;
import com.xinlan.discview.util;

import ncl.zcqb.app.R;
import nlc.zcqb.app.application.MyApplication;
import nlc.zcqb.app.application.User.ScreenParams;
import nlc.zcqb.app.application.User.User;
import nlc.zcqb.app.daichaoview.login.viewholder.TitleBarViewHolder;
import nlc.zcqb.app.event.DataSynEvent;
import nlc.zcqb.baselibrary.callback.ClickCallback;
import nlc.zcqb.baselibrary.callback.TimeClickCallback;
import nlc.zcqb.baselibrary.presenter.BasePresenter;
import nlc.zcqb.baselibrary.util.Constant;
import nlc.zcqb.baselibrary.util.Utils;
import nlc.zcqb.baselibrary.util.helper.EasyPermissonHelper;
import nlc.zcqb.baselibrary.util.helper.StatusBarUtils;
import pub.devrel.easypermissions.EasyPermissions;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public abstract class BaseActivity  extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {

    public final static int  UPDATE_APP=15556;
    public final static int REQUEST_COARSE_Permission=13485;
    public final static int  REQUEST_GPS_Permission=13222;
    public final static int GETPERMISSION=10211;
    public final static int REQUEST_camera_Permission=11485;
    public final static int REQUEST_Read_Permission=15555;
    public final static int  REQUEST_write_Permission=15552;
    public final static int RESULT_DOWNLOADCOMPLETE=14444;
    public final static int LEFT=1,RIGHT=3,TOP=2,BOTTOM=4;
    public TitleBarViewHolder viewHolder;
    public User mUser;
    private int layoutResID;
    private boolean isfirst=false;
    private IntentFilter intentFilter=new IntentFilter(Constant.NetFilter);
    private NetListener netlistener;
    public ScreenParams screenParams= MyApplication.screenParams;
    private ArrayList<BasePresenter> basePresenters=new ArrayList<>();
    private  StatusBarUtils statusBarUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        parseIntent();

        mUser= MyApplication.mUser;
        isfirst=true;
        this.registerReceiver(broadcastReceiver,intentFilter);

        //沉浸式状态栏
        statusBarUtils=StatusBarUtils.with(this);
        statusBarUtils.init();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        EventBus.getDefault().register(this);//订阅 ,不能反复注册eventbus

        layoutResID=getLayoutId();
        setContentView(layoutResID);

        initTitle();
        InitMenu();
        InitOthers();

    }

    public void setStatusColor(int color){
        statusBarUtils.setColor(color);
        statusBarUtils.addStatusViewWithColor(this,color);
    }

    public void changeStatusBarTextColor(boolean isBlack) {
        StatusBarUtils.changeStatusTextColor(this,isBlack);
    }


    public void initTitle(){

    }

    public abstract int getLayoutId();

    public abstract  void  InitMenu();

    public abstract void InitOthers();

    public abstract void parseIntent();

    @Override
    public void onResume(){
        super.onResume();
        Glide.with(this).resumeRequests();
    }

    @Override
    public void onStop(){
        super.onStop();
        isfirst=false;
        Glide.with(this).pauseRequests();
    }

    public void addPresenter(BasePresenter basePresenter){
        basePresenters.add(basePresenter);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if (basePresenters!=null)
        for (BasePresenter b: basePresenters) {
            if (b!=null){
                b.detach();
            }
        }
        EventBus.getDefault().unregister(this);//订阅
        this.unregisterReceiver(broadcastReceiver);
    }


    BroadcastReceiver broadcastReceiver=new  BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            if (netlistener==null){
                return;
            }
            // TODO Auto-generated method stub
            Log.e("onReceive","网络状态变化！");
            //如果无网络连接activeInfo为null
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mobileInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo activeInfo = manager.getActiveNetworkInfo();
            if(activeInfo==null || (!mobileInfo.isConnected() && !wifiInfo.isConnected())){
                netlistener.NoNet();
            }else {
                netlistener.NetWell();
            }
         }
    };

    public interface NetListener{
        public void NoNet();
        public void NetWell();
    }


    protected String getRunningActivityName() {
        String contextString = this.toString();
        return contextString.substring(contextString.lastIndexOf(".") + 1, contextString.indexOf("@"));
    }


    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(DataSynEvent event) {
        Log.e("onDataSynEvent", "event---->路径" + event.getCurrentActivity());
    }



    public boolean isfirst() {
        return isfirst;
    }

    public void setIsfirst(boolean isfirst) {
        this.isfirst = isfirst;
    }

    public NetListener getNetlistener() {
        return netlistener;
    }

    public void setNetlistener(NetListener netlistener) {
        this.netlistener = netlistener;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (EasyPermissonHelper.contain(requestCode)){
            // 把执行结果的操作给EasyPermissions
            EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
            return;
        }

        switch (requestCode) {
            case REQUEST_COARSE_Permission:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendMessage(null,GETPERMISSION);
                } else {
                    // Permission Denied
                    Toast.makeText(this, "没有打开权限，请打开网络定位权限！", Toast.LENGTH_SHORT).show();
                }
                break;
            case REQUEST_GPS_Permission:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendMessage(null,GETPERMISSION);
                } else {
                    // Permission Denied
                    Toast.makeText(this, "没有打开权限，请打开GPS定位权限！", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == UPDATE_APP) {

            }
            if ( msg.what == GETPERMISSION) {
                Toast.makeText(getBaseContext(),"获得权限！",Toast.LENGTH_SHORT).show();
            }
        }
    };


    public void getFileManager(){

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //intent.setType(“image/*”);//选择图片
        //intent.setType(“audio/*”); //选择音频
        //intent.setType(“video/*”); //选择视频 （mp4 3gp 是android支持的视频格式）
        //intent.setType(“video/*;image/*”);//同时选择视频和图片
        intent.setType("image/*");//无类型限制
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(intent, Constant.GETFILE);
    }

    public void sendMessage(Object object,int flag){
        Message message = handler.obtainMessage();
        message.obj=object;
        message.what = flag;
        handler.sendMessage(message);
    }

    private  View dialogview;
    private DialogItemAdapter dialogItemAdapter;
    private ListView listview;
    private AlertDialog dialog;
    private  TextView title;
    private int[] size;

    public void showChooseDialog(String titlename, final ArrayList<String> items, final ClickCallback clickCallback){
        if (items!=null && items.size()>0) {
            if (dialogview == null) {
                //实例化布局
                dialogview = LayoutInflater.from(this).inflate(R.layout.dialog_listview, null);
                dialogItemAdapter = new DialogItemAdapter(this, items);
                title=(TextView) dialogview.findViewById(R.id.title);
                title.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        size=new int[2];
                        size[0]= title.getMeasuredWidth();
                        size[1]= title.getMeasuredHeight();
                        fixPosition(items.size());
                        title.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
                //找到并对自定义布局中的控件进行操作的示例
                listview = (ListView) dialogview.findViewById(R.id.listview);
                listview.setAdapter(dialogItemAdapter);
            } else {
                if (dialogview.getParent() != null) {
                    ((ViewGroup) dialogview.getParent()).removeView(dialogview);
                }
                dialogItemAdapter.updateDate(items);
            }
            dialogItemAdapter.setClickCallback(new ClickCallback() {
                @Override
                public void click(int position) {
                    if (clickCallback != null) {
                        clickCallback.click(position);
                    }
                    dialog.dismiss();
                }
            });

            //创建对话框
            dialog = new AlertDialog.Builder(this).create();
//            dialog.setTitle(titlename);//设置标题
            title.setText(titlename);
            dialog.setView(dialogview);//添加布局
            dialog.show();
        }
    }



    private void fixPosition(int count){
        if (count>4){
            count=4;
        }
        ViewGroup.LayoutParams layoutParams=dialogview.getLayoutParams(); // 高度)
        layoutParams.height=  size[1]+count* util.dip2px(this,44)+util.dip2px(this,24);
        dialogview.setLayoutParams(layoutParams);
        dialogview.requestLayout();
    }

    public JSONObject getJO(String str){
        try {
            JSONObject jsonObject=new JSONObject(str);
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }

    public String getStrByKey(String json,String key){
        JSONObject jsonObject= getJO(json);
        try {
            return (String) jsonObject.get(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @param DrawID
     * @param dir  0l,1t,2r,3b
     * @param size width=height
     * @param textView
     */
    public void setTextDraw(int DrawID,int dir,int size,TextView textView){
        Drawable drawable= getResources().getDrawable(DrawID);
        drawable.setBounds(0,0, com.example.customview.util.dip2px( this,size), com.example.customview.util.dip2px(this,size));//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        switch (dir){
            case LEFT:
                textView.setCompoundDrawables(drawable,null,null,null);
                break;
            case TOP:
                textView.setCompoundDrawables(null,drawable,null,null);
                break;
            case RIGHT:
                textView.setCompoundDrawables(null,null,drawable,null);
                break;
            case BOTTOM:
                textView.setCompoundDrawables(null,null,null,drawable);
                break;
        }
    }

    public final static int MINU_TIME= PickTimeView.TYPE_PICK_TIME;
    public final static int YEAR_DATE= PickTimeView.TYPE_PICK_DATE;
    private String currentTime="";

    public void showPicker(final int type, final TimeClickCallback clickCallback){
        if (  type!=(MINU_TIME) &&   type!=(YEAR_DATE)){
            return;
        }

        //实例化布局
        View  dialog_pickview = LayoutInflater.from(this).inflate(R.layout.dialog_timepicker, null);
        final PickTimeView pickTimeView= (PickTimeView)dialog_pickview.findViewById(R.id.time_picker);
        TextView title= (TextView) dialog_pickview.findViewById(R.id.title);
        if (type==MINU_TIME){
            title.setText("时间");
        }else {
            title.setText("日期");
        }
        TextView sure=(TextView) dialog_pickview.findViewById(R.id.sure);

        pickTimeView.setViewType(type);
        pickTimeView.setOnSelectedChangeListener(new PickTimeView.onSelectedChangeListener() {
            @Override
            public void onSelected(PickTimeView view, long timeMillis) {
                String temp="";
                if (type==MINU_TIME) {
                    SimpleDateFormat sdfTime = new SimpleDateFormat("MM-dd EEE HH:mm");
                    String str = sdfTime.format(timeMillis);
                    temp=str.substring("MM-dd EEE ".length());
                }else {
                    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
                    temp = sdfDate.format(timeMillis);
                }
                currentTime=temp;
            }
        });


        //创建对话框
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setView(dialog_pickview);//添加布局
        dialog.show();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                currentTime="";
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickCallback!=null){
                    clickCallback.click(currentTime);
                    currentTime="";
                }
                dialog.dismiss();
            }
        });

    }

    private String currentString="";
    public void showValuePicker(final String titlename, String[] values,final TimeClickCallback clickCallback){

        //实例化布局
        View  dialog_pickview = LayoutInflater.from(this).inflate(R.layout.dialog_valuepicker, null);
        final PickValueView pickValueView= (PickValueView)dialog_pickview.findViewById(R.id.value_picker);
        TextView title= (TextView) dialog_pickview.findViewById(R.id.title);
        title.setText(titlename);

        TextView sure=(TextView) dialog_pickview.findViewById(R.id.sure);

        pickValueView.setValueData(values, values[0]);
        pickValueView.setOnSelectedChangeListener(new PickValueView.onSelectedChangeListener() {
            @Override
            public void onSelected(PickValueView view, Object leftValue, Object middleValue, Object rightValue) {
                currentString= (String) leftValue;
            }
        });
        //创建对话框
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setView(dialog_pickview);//添加布局
        dialog.show();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                currentTime="";
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickCallback!=null){
                    clickCallback.click(currentString);
                    currentString="";
                }
                dialog.dismiss();
            }
        });
    }

    public void Toast(String s){
        Toast.makeText(this,""+s,Toast.LENGTH_SHORT).show();
    }



    @Override //申请成功时调用
    public void onPermissionsGranted(int requestCode, List<String> list) {
        //请求成功执行相应的操作比如，举个例子

        switch (requestCode){
            case EasyPermissonHelper.REQUEST_write_Read_Permission:
                Toast.makeText(this, "已获取读写权限", Toast.LENGTH_SHORT).show();
                break;
            case EasyPermissonHelper.REQUEST_Location_Permission:
                Toast.makeText(this, "已获取定位权限", Toast.LENGTH_SHORT).show();
                break;
            case EasyPermissonHelper.REQUEST_camera_Permission:
                Toast.makeText(this, "已获取摄影权限", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override //申请失败时调用
    public void onPermissionsDenied(int requestCode, List<String> list) {
        // 请求失败，执行相应操作
        Toast.makeText(this, "拒绝权限，会导致不能使用相关功能！", Toast.LENGTH_SHORT).show();
    }

}
