package nlc.zcqb.app.daichaoview.fourth.view;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.customview.tabmenu.ClickCallback;
import com.example.customview.tabmenu.TabsMenu;
import com.example.customview.util;

import org.greenrobot.eventbus.EventBus;

import ncl.zcqb.app.R;
import nlc.zcqb.app.application.MyApplication;
import nlc.zcqb.app.daichaoview.common.BeanPresenter;
import nlc.zcqb.app.daichaoview.common.CommonSimplePresenter;
import nlc.zcqb.app.daichaoview.common.CommonView;
import nlc.zcqb.app.daichaoview.common.UserIdBean;
import nlc.zcqb.app.daichaoview.fourth.adapter.viewholder.PersonViewHolder1;
import nlc.zcqb.app.daichaoview.fourth.adapter.viewholder.PersonViewHolder2;
import nlc.zcqb.app.daichaoview.fourth.adapter.viewholder.PersonViewHolder3;
import nlc.zcqb.baselibrary.event.CommandEvent;
import nlc.zcqb.baselibrary.callback.UpdateView;
import nlc.zcqb.app.daichaoview.fourth.bean.PersonBean;
import nlc.zcqb.app.daichaoview.fourth.bean.UploadPersonBean;
import nlc.zcqb.app.daichaoview.login.viewholder.TitleBarViewHolder;
import nlc.zcqb.app.util.DC;
import nlc.zcqb.baselibrary.baseview.BaseActivity;

/**
 * Created by lvqiu on 2018/10/15.
 */

public class PersonalActivity extends BaseActivity implements View.OnClickListener,CommonView{
    private BeanPresenter presenter;
    private CommonSimplePresenter uploadPresenter;
    public final static int GETUSERINFO=11112,UPLOAD=11114,UPLOADICON=11113;
    private TitleBarViewHolder viewHolder;
    private TextView tips;
    Button submit;
    int currentpage=0;
    TabsMenu tabsMenu;
    ViewPager viewPager;
    View[] personWrap=new View[3];
    UpdateView[] p=new UpdateView[3];
    String userid="";
    TextView process;
    ProgressBar progressBar;


    @Override
    public int getLayoutId() {
        return R.layout.activity_dc_personal;
    }

    @Override
    public void InitMenu() {
        tips=(TextView) findViewById(R.id.tips);
        Drawable drawable= getResources().getDrawable(R.mipmap.yes);
        drawable.setBounds(0,0, util.dip2px( this,14),util.dip2px(this,14));//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        tips.setCompoundDrawables(drawable,null,null,null);//只放左边
        submit=(Button) findViewById(R.id.checkbotton);
        submit.setText(R.string.next);
        submit.setOnClickListener(this);

        viewHolder=new TitleBarViewHolder(this);
        viewHolder.titlename.setTextColor(getResources().getColor(R.color.black));
        viewHolder.titleBg.setImageDrawable(null);
        viewHolder.back.setVisibility(View.VISIBLE);
        viewHolder.back.setImageResource(R.mipmap.back_gray);
        viewHolder.back.setOnClickListener(this);
        viewHolder.titlename.setText(getResources().getText(R.string.person_msg));

        viewHolder.titleBg.setBackgroundResource(R.color.white);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            changeStatusBarTextColor(true);
        }else {
            viewHolder.title_layout.setBackgroundColor(getResources().getColor(R.color.white));
            setStatusColor(R.color.mosttransparentgray);
        }
    }

    @Override
    public void InitOthers() {

        process=(TextView) findViewById(R.id.process);
        progressBar=(ProgressBar) findViewById(R.id.process_bar);

        presenter=new BeanPresenter<PersonBean>(this){};
        presenter.getCommonData(GETUSERINFO,new UserIdBean(userid,""), DC.yonghuxinxi );
        addPresenter(presenter);
        uploadPresenter=new CommonSimplePresenter(this);
        addPresenter(uploadPresenter);

        tabsMenu=(TabsMenu) findViewById(R.id.tabs_view);
        tabsMenu.initView(new String[]{getResources().getString(R.string.personaltitle1),getResources().getString(R.string.personaltitle2),getResources().getString(R.string.personaltitle3)});
        tabsMenu.setCurrentPosition(0);
        currentpage=0;

        View view1= LayoutInflater.from(this).inflate(R.layout.personal_item1,null);
        personWrap[0]=view1;
        p[0]=new PersonViewHolder1(view1);
        View view2= LayoutInflater.from(this).inflate(R.layout.personal_item2,null);
        personWrap[1]=view2;
        p[1]=new PersonViewHolder2(view2);
        View view3= LayoutInflater.from(this).inflate(R.layout.personal_item3,null);
        personWrap[2]=view3;
        p[2]=new PersonViewHolder3(view3);

        viewPager=(ViewPager) findViewById(R.id.Login_viewpager);
        viewPager.setCurrentItem(0);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return personWrap.length;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                if (personWrap!=null && container.equals(personWrap[position].getParent())){
                    container.removeView(personWrap[position]);
                }
                container.addView(personWrap[position],new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                return personWrap[position];
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                if (personWrap[position].equals(object)){
                    container.removeView((View) object);
                    object=null;
                }
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                if (view.equals(object)){
                    return true;
                }
                return false;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabsMenu.setCurrentPosition(position);
                submit.setText(R.string.next);
                if (position==2){
                    submit.setText(R.string.submit);
                }
                currentpage=position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabsMenu.setClickCallback(new ClickCallback() {
            @Override
            public void click(int position) {
                viewPager.setCurrentItem(position);
            }
        });
    }

    @Override
    public void parseIntent() {
        userid=MyApplication.mUser.getUser_id();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_action:
                this.finish();
                break;
            case R.id.checkbotton:
                if (submit.getText().toString().equals(getResources().getString(R.string.next))){
                    viewPager.setCurrentItem(currentpage+1);
                }else {
                    submit.setEnabled(false);
                    uploadPresenter.getCommonData(UPLOAD,getPersonData(),DC.xiugaiziliao,"message");
                }
        }
    }

    PersonBean personBean=new PersonBean();
    @Override
    public void success(Object o, int type) {
        if (type==GETUSERINFO){
            personBean= (PersonBean) o;
            updatePersonView(personBean);
        }else if (type==UPLOAD){
            Toast(""+o);
            EventBus.getDefault().post(new CommandEvent(CommandEvent.LOGIN_SUCCESS,getRunningActivityName()));
            this.finish();
        }
        submit.setEnabled(true);
    }

    @Override
    public void failure(Object o, int type) {
        Toast((String) o);
        submit.setEnabled(true);
    }

    public void updatePersonView(PersonBean bean){
        if (bean==null){
            bean=new PersonBean();
        }
        int count=0;
        for (UpdateView v : p) {
            v.updateData(bean);
            count+=v.getNullCount();
        }
        updateProcess(19-count);
    }

    public void updateProcess(int count){
        int perscent= (int) (count*1.00f/19*100);
        progressBar.setProgress(perscent);
        process.setText(""+perscent+"%");
    }

    public UploadPersonBean getPersonData(){
        UploadPersonBean bean=new UploadPersonBean();
        bean.setUser_id(userid);
        for (UpdateView v : p) {
            v.getData(bean);
        }
        if (bean.getUser_id()==null || bean.getUser_id().length()==0){
            bean=new UploadPersonBean();
        }
        return bean;
    }

}
