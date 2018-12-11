package nlc.zcqb.app.daichaoview.second.fragment;

import android.annotation.SuppressLint;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.customview.filtermenu.FilterMenu;
import com.example.customview.tabmenu.ClickCallback;
import com.example.customview.tabmenu.TabsMenu;
import ncl.zcqb.app.R;
import nlc.zcqb.app.daichaoview.common.CommonPresenter;
import nlc.zcqb.app.daichaoview.common.CommonView;
import nlc.zcqb.app.daichaoview.second.adapter.SecondAdapter;
import nlc.zcqb.app.daichaoview.second.bean.PingTaiQueryBean;
import nlc.zcqb.app.daichaoview.second.bean.TypeBean;
import nlc.zcqb.app.daichaoview.second.presenter.PingTaiQueryPresenter;
import nlc.zcqb.app.event.ClickEvent;
import nlc.zcqb.app.util.DC;
import nlc.zcqb.baselibrary.baseview.BaseListFragment;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import static com.example.customview.filtermenu.FilterMenu.LEFT;

/**
 * Created by lvqiu on 2017/11/20.
 */

public class Secondfragment extends BaseListFragment  implements CommonView {
    private int TYPETYPE=7;
    public final static int PINGTAITYPE=8,PINGTAIMORETYPE=9;

    ArrayList<TypeBean> leftlist,rightlist,tablist;
    String[] s1={"金额不限","2000以下", "2000-5000", "5000-10000","10000以上"};
    String[] s2={"综合排序", "通过率高", "下款最快","利率最低"};
    TabsMenu tabsMenu;
    FilterMenu filterMenu;
    ImageView back;
    TextView title;
    private CommonPresenter<TypeBean> typePresenter;
    private PingTaiQueryPresenter pingTaiQueryPresenter;
    PingTaiQueryBean queryBean;

    @SuppressLint({"NewApi", "ValidFragment"})
    public Secondfragment() {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ClickEvent event) {

    }

    @SuppressLint({"NewApi", "ValidFragment"})
    public Secondfragment(FragmentManager fragmentManager) {
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_second;
    }

    @Override
    public void initOther() {
        setAdapter(new SecondAdapter(getContext()));

        typePresenter= new CommonPresenter<TypeBean>(this){};
        addPresenter(typePresenter);
        typePresenter.getCommonData(TYPETYPE,null, DC.daikuanleixing,true);

        queryBean=new PingTaiQueryBean(0,0,0);
        pingTaiQueryPresenter=new PingTaiQueryPresenter(this);
        addPresenter(pingTaiQueryPresenter);
    }

    @Override
    public void initViews(View view) {
        initMenus(view);
        initTitleBar(view);
    }

    public void initMenus(View view){
        leftlist=new ArrayList<>();
        rightlist=new ArrayList<>();
        tablist=new ArrayList<>();
        for (int i=0;i<4;i++){
            leftlist.add(new TypeBean(i,s1[i]));
            tablist.add(new TypeBean(i,s2[i]));
        }

        tabsMenu= (TabsMenu) view.findViewById(R.id.tabs_view);
        tabsMenu.initView(s2);
        tabsMenu.setClickCallback(new ClickCallback() {
            @Override
            public void click(int position) {
                queryBean.Seaval1=position;
                asyRefreshData();
            }
        });
        filterMenu=(FilterMenu) view.findViewById(R.id.filter_view);
        filterMenu.setClickCallback(new com.example.customview.filtermenu.ClickCallback() {
            @Override
            public void click(int position,String tag) {
                if (tag.equalsIgnoreCase(LEFT)) {
                    queryBean.Seaval2 = position;
                }else {
                    int id= rightlist.get(position).getId();
                    queryBean.Seaval3 = id;
                }
                asyRefreshData();
            }
        });
    }

    public void initTitleBar(View view){
        back=(ImageView) view.findViewById(R.id.back_action);
        title=(TextView) view.findViewById(R.id.title_name);
        title.setText(getResources().getText(R.string.secondtitle));
    }

    @Override
    public void asyRefreshData() {
        startLoadingAnimation();
        queryBean.page=1;
        pingTaiQueryPresenter.getPingTaiList(queryBean,PINGTAITYPE);
    }

    @Override
    public void asyLoadingMoreData() {
        pingTaiQueryPresenter.getPingTaiList(queryBean,PINGTAIMORETYPE);
    }


    @Override
    public void success(Object o, int type) {
        if (type==TYPETYPE && o!=null){
            rightlist= (ArrayList<TypeBean>) o;
            rightlist.add(0,new TypeBean(0,"类型不限"));
            String[] temp=new String[rightlist.size()];
            for (int i=0;i<rightlist.size();i++) {
                temp[i]=rightlist.get(i).getName();
            }
            filterMenu.initMenu(s1, temp);
            queryBean.Seaval3=rightlist.get(0).getId();
            asyRefreshData();
        }if (type==PINGTAITYPE){
            updateData(o);
            queryBean.page=2;
        }else if (type==PINGTAIMORETYPE){
            loadMore(o);
            queryBean.page++;
        }
        stopLoadingAnimation();
    }

    @Override
    public void failure(Object o, int type) {
        Toast.makeText(getContext(),(String)o,Toast.LENGTH_SHORT).show();
        stopLoadingAnimation();
        if (type==PINGTAITYPE){
            updateData(null);
        }
    }
}
