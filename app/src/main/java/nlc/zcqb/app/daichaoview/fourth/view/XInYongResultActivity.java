package nlc.zcqb.app.daichaoview.fourth.view;


import android.os.Build;
import android.view.View;
import android.widget.Toast;
import ncl.zcqb.app.R;
import nlc.zcqb.app.daichaoview.common.CommonPresenter;
import nlc.zcqb.app.daichaoview.common.CommonView;
import nlc.zcqb.app.daichaoview.common.UserIdBean;
import nlc.zcqb.app.daichaoview.fourth.adapter.ResultAdapter;
import nlc.zcqb.app.daichaoview.fourth.bean.ZhenduanResultBean;
import nlc.zcqb.app.daichaoview.login.viewholder.TitleBarViewHolder;
import nlc.zcqb.app.util.DC;
import nlc.zcqb.baselibrary.baseview.BaseListActivity;

/**
 * Created by lvqiu on 2018/10/14.
 */

public class XInYongResultActivity extends BaseListActivity  implements View.OnClickListener,CommonView{
    private TitleBarViewHolder viewHolder;
    private CommonPresenter<ZhenduanResultBean> resultPresenter;
    private final static int RESULTTYPE=20;
    private UserIdBean userIdBean;

    public int getLayoutId(){
        return R.layout.fragment_third;
    }

    @Override
    public void initTitle(){
        viewHolder =new TitleBarViewHolder(this);

        viewHolder.back.setVisibility(View.VISIBLE);
        viewHolder.back.setImageResource(R.mipmap.back_gray);
        viewHolder.back.setOnClickListener(this);

        viewHolder.titleBg.setImageDrawable(null);
        viewHolder.titlename.setText(R.string.zhenduanresult);
        viewHolder.titlename.setTextColor(getResources().getColor(R.color.black));

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
        userIdBean=new UserIdBean(mUser.getUser_id(),null);
        initTitle();
        setAdapter(new ResultAdapter(this));
        resultPresenter=new CommonPresenter<ZhenduanResultBean>(this){};
        addPresenter(resultPresenter);
        resultPresenter.getCommonData(RESULTTYPE,userIdBean, DC.xinyongchaxun,false);
    }

    @Override
    public void parseIntent() {

    }

    @Override
    public void asyRefreshData() {
        resultPresenter.getCommonData(RESULTTYPE,userIdBean, DC.xinyongchaxun,false);
        startLoadingAnimation();
    }

    @Override
    public void asyLoadingMoreData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back_action:
                this.finish();
                break;
        }
    }

    @Override
    public void success(Object o, int type) {
        stopLoadingAnimation();
        if (type==RESULTTYPE){
            ZhenduanResultBean bean= (ZhenduanResultBean) o;
            getAdapter().updateData(bean);
        }

    }

    @Override
    public void failure(Object o, int type) {
        stopLoadingAnimation();
        Toast.makeText(this,""+o,Toast.LENGTH_SHORT).show();
    }
}
