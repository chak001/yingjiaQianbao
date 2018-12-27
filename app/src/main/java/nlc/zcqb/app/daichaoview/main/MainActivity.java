package nlc.zcqb.app.daichaoview.main;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.waImageClip.activity.MediaPickHelper;
import com.example.waImageClip.activity.ResultCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import ncl.zcqb.app.R;
import nlc.zcqb.app.application.MyApplication;
import nlc.zcqb.app.daichaoview.common.BeanPresenter;
import nlc.zcqb.app.daichaoview.common.CommonSimplePresenter;
import nlc.zcqb.app.daichaoview.common.CommonView;
import nlc.zcqb.app.daichaoview.common.UserIdBean;
import nlc.zcqb.app.daichaoview.fourth.bean.PersonBean;
import nlc.zcqb.app.daichaoview.fourth.view.PersonalActivity;
import nlc.zcqb.app.daichaoview.main.bean.IconBean;
import nlc.zcqb.app.daichaoview.main.bean.versionBean;
import nlc.zcqb.app.daichaoview.service.UpdateService;
import nlc.zcqb.baselibrary.event.CommandEvent;
import nlc.zcqb.baselibrary.event.progressEvent;
import nlc.zcqb.app.util.ARouter;
import nlc.zcqb.app.util.DC;
import nlc.zcqb.app.daichaoview.service.UploadService;
import nlc.zcqb.app.util.PermissionUtil;
import nlc.zcqb.baselibrary.basemodel.sharepreferenceRequest.UserPreferencesUtil;
import nlc.zcqb.baselibrary.util.AppVersionUtil;
import nlc.zcqb.baselibrary.util.helper.EasyPermissonHelper;
import nlc.zcqb.baselibrary.util.helper.RequestPermissonHelper;
import nlc.zcqb.baselibrary.baseview.BaseActivity;
import nlc.zcqb.app.daichaoview.first.fragment.Firstfragment;
import nlc.zcqb.app.daichaoview.fourth.fragment.Fourthfragment;
import nlc.zcqb.app.daichaoview.second.fragment.Secondfragment;
import nlc.zcqb.app.daichaoview.third.fragment.Thirdfragment;
import nlc.zcqb.baselibrary.util.Constant;
import nlc.zcqb.baselibrary.util.Utils;

import java.io.File;
import java.util.ArrayList;

import static nlc.zcqb.app.daichaoview.fourth.view.PersonalActivity.GETUSERINFO;
import static nlc.zcqb.app.daichaoview.fourth.view.PersonalActivity.UPLOADICON;
import static nlc.zcqb.baselibrary.event.CommandEvent.CHANGEPAGE;
import static nlc.zcqb.baselibrary.event.CommandEvent.DOWN_APP;
import static nlc.zcqb.baselibrary.event.CommandEvent.UPLOAD_ICON;


/**
 * Created by lvqiu on 2017/11/10.
 */

public class MainActivity extends BaseActivity implements BaseActivity.NetListener,View.OnClickListener,CommonView{

    private TextView net_status;
    private LinearLayout linearLayout;
    private ViewPager mViewPager;
    private ArrayList<Fragment> mTabs;
    private FragmentPagerAdapter mTabAdapters;
    private TextView[] menus;
    private int Textviewid[]=new int[]{R.id.first, R.id.second,R.id.third,R.id.fourth};
    private int drawableid[]=new int[]{R.drawable.first,R.drawable.second,R.drawable.third,R.drawable.fourth};
    private int drawableFocusedid[]=new int[]{R.drawable.first_focused,R.drawable.second_focused,R.drawable.third_focused,R.drawable.fourth_focused};
    private int currentposition=0;
    private String[] text=new String[]{"首页","平台","攻略","个人"};
    private CommonSimplePresenter commonSimplePresenter;
    private final static int PROTOCALTYPE=111,FIXUSERICON=112,GETAPPVERSION=1114;
    private BeanPresenter presenter;
    private CommonSimplePresenter iconpresenter;
    public final static int requestNotifyCode= 124;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RequestPermissonHelper.RequestStore(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void InitMenu() {

        menus=new TextView[4];
        setNetlistener(this);
        net_status=(TextView) findViewById(R.id.net_status);
        Drawable drawable = getResources().getDrawable(R.drawable.sad);
        drawable.setBounds(0, 0, (int) (screenParams.getScale() * 70), (int) (screenParams.getScale() * 70));
        net_status.setCompoundDrawables(drawable, null, null, null);
        for (int i=0;i<menus.length;i++){
            menus[i]=(TextView)findViewById(Textviewid[i]);
            menus[i].setOnClickListener(this);
            setMenuStatus(i,false);
        }
        setMenuStatus(0,true);
        requestNotifyPermission();
    }

    @Override
    public void InitOthers() {
        versionBean versionbean=new versionBean(AppVersionUtil.getVerName(this));
        iconpresenter=new CommonSimplePresenter(this);
        presenter=new BeanPresenter<PersonBean>(this);
        commonSimplePresenter=new CommonSimplePresenter(this);
        commonSimplePresenter.getCommonData(PROTOCALTYPE,null,DC.protocol,"content");
        commonSimplePresenter.getCommonData(GETAPPVERSION,versionbean,DC.getversion,null);
        addPresenter(presenter);
        addPresenter(commonSimplePresenter);

        mViewPager= (ViewPager) findViewById(R.id.viewpage);

        mTabs=new ArrayList<>();

        Fragment[] homePageFragment = new Fragment[menus.length];
        for (int i=0;i<menus.length;i++){
            if (i==0){
                homePageFragment[i]=new Firstfragment();
            }else if (i==1){
                homePageFragment[i]=new Secondfragment();
            }else if (i==2){
                homePageFragment[i]=new Thirdfragment();
            }else {
                homePageFragment[i]=new Fourthfragment();
            }
            mTabs.add(homePageFragment[i]);
        }

        mTabAdapters = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mTabs.get(position);
            }

            @Override
            public int getCount() {
                return mTabs == null ? 0 : mTabs.size();
            }
        };
        mViewPager.setOffscreenPageLimit(3);
        mViewPager.setAdapter(mTabAdapters);
        mViewPager.setCurrentItem(0);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeMenuStatus(currentposition,position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void parseIntent() {

    }

    @Override
    public void onResume() {
        super.onResume();
        //设置app是否处于后台状态
        Utils.setBackground(false);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    @Override
    public void onPause() {
        super.onPause();
        Utils.setBackground(true);
    }

    @Override
    public void NoNet() {
        net_status.setVisibility(View.VISIBLE);
    }

    @Override
    public void NetWell() {
        net_status.setVisibility(View.GONE);
    }



    @Override
    public void onClick(View v) {
        int id=v.getId();
        for (int i=0;i<menus.length;i++){
            if (id== Textviewid[i]){
                changeMenuStatus(currentposition,i);
                mViewPager.setCurrentItem(i);
            }
        }
    }

    public void setCurrentposition(int possition){
        if (possition<0 || possition>3){
            return;
        }
        menus[possition].performClick();
    }

    public void changeMenuStatus(int before,int current){
        if (before==current){
            return;
        }
        setMenuStatus(before,false);
        setMenuStatus(current,true);
        currentposition=current;
    }


    public void setMenuStatus( int current ,boolean flag){
        Drawable drawable=null;
        if (flag){
            drawable = getResources().getDrawable(drawableFocusedid[current]);
            drawable.setBounds(0, 0, (int) (screenParams.getScale() * 70), (int) (screenParams.getScale() * 70));
            menus[current].setCompoundDrawables(null, drawable, null, null);
            menus[current].setTextColor(getResources().getColor(R.color.dot_color));
            menus[current].setText(text[current]);
        }else {
            drawable = getResources().getDrawable(drawableid[current]);
            drawable.setBounds(0, 0, (int) (screenParams.getScale() * 70), (int) (screenParams.getScale() * 70));
            menus[current].setCompoundDrawables(null, drawable, null, null);
            menus[current].setTextColor(getResources().getColor(R.color.menu_grey));
            menus[current].setText(text[current]);
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(CommandEvent commandEvent) {
        if (commandEvent.getType().equalsIgnoreCase(CHANGEPAGE)){
            setCurrentposition(Integer.valueOf(commandEvent.getCommand()));
        }else if (commandEvent.getType().equals(CommandEvent.LOGIN_SUCCESS)){
            presenter.getCommonData(GETUSERINFO,new UserIdBean(MyApplication.mUser.getUser_id(),""), DC.yonghuxinxi );
        }else if (commandEvent.getType().equals(CommandEvent.CLEAR_USER)){
            //刷新第四个fragment
            Fourthfragment fragment= (Fourthfragment) mTabs.get(3);
            fragment.refreshHead();
        }else if ( commandEvent.getType().equals(CommandEvent.UPLOAD_ICON)){
            if (EasyPermissonHelper.hasStorePermission(this)){
                MediaPickHelper.pickImage(this);
            }else {
                EasyPermissonHelper.RequestStore(this);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onProgressChange(progressEvent progress){
        if (progress.getProgress()==100  && progress.getACTION().equals(UPLOAD_ICON)){
            if (MyApplication.mUser.isLogin()){
                IconBean bean=new IconBean(MyApplication.mUser.getUser_id(),progress.getAbsolutepath());
                iconpresenter.getCommonData(FIXUSERICON,bean,DC.fixIcon,"code");
            }else {
                Toast("还没登陆！");
            }
        }else if (progress.getProgress()==100 && progress.getACTION().equals(DOWN_APP)){
            Toast("最新app下载完成！");
            File file=new File(progress.getAbsolutepath());
            if (file.exists())
                AppVersionUtil.installApp(file,this);
        }
    }


    /**
     * 该函数实在Activity是singleTask模式下才会调用的，用于在此启动的时候传递参数
     */
    @Override
    protected void onNewIntent(Intent intent) {
        if (intent != null) {
            Bundle bundle=intent.getExtras();
            int resultcode = bundle.getInt(Constant.resultcode, 0);
            if (resultcode==Constant.REGISTER_SUCCESS){
                ARouter.jumpIn(this,new Bundle(), PersonalActivity.class);
            }
        }
        super.onNewIntent(intent);
    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            Hook();
            return true;
        }else
            return super.onKeyDown(keyCode, event);
    }

    private void Hook() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        builder.setNeutralButton("退出程序", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                mUser.setLogin(false);
                UserPreferencesUtil.save(mUser);
                int pid = android.os.Process.myPid();
                android.os.Process.killProcess(pid);
            }
        });
        Log.e("Hook", "Hook展示");
        builder.show();
    }


    @Override
    public void success(Object o, int type) {
        if (type==PROTOCALTYPE){
            MyApplication.setProtocalUrl((String)o);
        }else if (type==GETUSERINFO||type==UPLOADICON){
            PersonBean personBean= (PersonBean) o;
            MyApplication.mUser.saveUser(personBean);
            //刷新第四个fragment
            Fourthfragment fragment= (Fourthfragment) mTabs.get(3);
            fragment.refreshHead();
        }else if (type==FIXUSERICON ){
            EventBus.getDefault().post(new CommandEvent(CommandEvent.LOGIN_SUCCESS,getRunningActivityName()));
        }else if (type==GETAPPVERSION){
            try {
                JSONObject jsonObject= new JSONObject((String) o);
                int code=  jsonObject.getInt("code");
                if (code==100){
                    String temp= jsonObject.getString("data");
                    JSONObject object= new JSONObject(temp);
                    String downUrl= object.getString("and");
                    String edition=object.getString("edition");
                    showUpdateDiaLog(edition,downUrl);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public void requestNotifyPermission(){
        if (!PermissionUtil.isNotificationEnabled(this)){
            Toast.makeText(this,"请打开通知栏权限！",Toast.LENGTH_SHORT).show();
            PermissionUtil.gotoNotificationSetting( this,requestNotifyCode);
            return  ;
        }
    }

    public void showUpdateDiaLog(String edition, final String url){
        requestNotifyPermission();
        AlertDialog.Builder builder  = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("app更新" ) ;
        builder.setMessage("有最新的"+edition+"版本app，是否现在下载？" ) ;
        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UpdateService.startDownLoad(MainActivity.this,url,"");
            }
        });
        builder.setNegativeButton("否", null);
        builder.show();
    }


    @Override
    public void failure(Object o, int type) {
        Toast(""+o);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== requestNotifyCode){
            if (resultCode==Activity.RESULT_CANCELED){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    if (!PermissionUtil.isNotificationEnabled(this)){
                        PermissionUtil.gotoNotificationSetting(this, requestNotifyCode);
                        return;
                    }
                }
            }
        }
        MediaPickHelper.handleActivityResult(this, requestCode, resultCode, data, new ResultCallback() {
            @Override
            public void success(String path) {
                UploadService.startUpload(MainActivity.this,path,UPLOAD_ICON);
            }

            @Override
            public void fail(String mess) {
                Toast(mess+"");
            }
        });
    }

}
