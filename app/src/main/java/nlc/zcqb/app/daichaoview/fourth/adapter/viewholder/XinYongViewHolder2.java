package nlc.zcqb.app.daichaoview.fourth.adapter.viewholder;

import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.customview.validateview.ValidateView;

import ncl.zcqb.app.R;
import nlc.zcqb.app.application.MyApplication;
import nlc.zcqb.app.daichaoview.fourth.bean.ZhenduanQueryBean;
import nlc.zcqb.baselibrary.util.StringUtils;
import nlc.zcqb.baselibrary.util.Utils;

/**
 * Created by lvqiu on 2018/10/14.
 */

public class XinYongViewHolder2 extends RecyclerView.ViewHolder {
    public EditText name,cardId,telnum;
    public ValidateView checkcode;
    public Button checkbotton;
    public AppCompatCheckBox checkBox;

    public XinYongViewHolder2(View itemView) {
        super(itemView);
        name= (EditText) itemView.findViewById(R.id.name);
        cardId=(EditText) itemView.findViewById(R.id.cardId);
        telnum=(EditText) itemView.findViewById(R.id.telnum);
        checkcode=(ValidateView) itemView.findViewById(R.id.checkcode);
        checkbotton=(Button) itemView.findViewById(R.id.checkbotton);
        checkBox=(AppCompatCheckBox) itemView.findViewById(R.id.checkbox);
    }

    public boolean checkValidate(){
        String s_name=name.getText().toString();
        String s_cardid= cardId.getText().toString();
        String s_tel= telnum.getText().toString();
        String s_code=checkcode.getCode();
        boolean ischeck= checkBox.isChecked();

        if (s_name.length()==0 || !StringUtils.isOnlyChinese(s_name) || !StringUtils.isCard_ID(s_cardid)|| !StringUtils.isMobileNO(s_tel)
                || s_code.length()<3 || !ischeck){
            return false;
        }else {
            return true;
        }
    }

    public ZhenduanQueryBean getBean(){
        String s_name=name.getText().toString();
        String s_cardid= cardId.getText().toString();
        String s_tel= telnum.getText().toString();
        String s_code=checkcode.getCode();
        return new ZhenduanQueryBean(MyApplication.mUser.getUser_id(),s_name,s_cardid,s_tel);
    }
}


