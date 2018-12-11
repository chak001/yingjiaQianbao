package nlc.zcqb.baselibrary.baseview.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xinlan.discview.util;

import ncl.zcqb.app.R;

/**
 * Created by lvqiu on 2018/10/27.
 */

public class ChooseTextview extends LinearLayout {
    private TextView title,chooseitem;
    private ImageView expand;
    private RelativeLayout wrapper;
    private String text="",titlename="";

    public ChooseTextview(Context context) {
        super(context);
        initView(null,0);
    }

    public ChooseTextview(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs,0);
    }

    public ChooseTextview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs,defStyleAttr);
    }

    private void initView(AttributeSet attrs, int defStyleAttr){
        this.setOrientation(VERTICAL);

        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.text_view, defStyleAttr, 0);
        text = a.getString(R.styleable.text_view_text);
        titlename = a.getString(R.styleable.text_view_title);

        View view= LayoutInflater.from(getContext()).inflate(R.layout.choose_textview,null);
        LinearLayout.LayoutParams layoutParams=new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.addView(view,layoutParams);

        title=(TextView) view.findViewById(R.id.title);
        chooseitem=(TextView) view.findViewById(R.id.chooseitem);
        expand=(ImageView) view.findViewById(R.id.expand);
        wrapper=(RelativeLayout) view.findViewById(R.id.wrapper);

        title.setText(titlename);
        chooseitem.setText(text);

        View line=new View(getContext());
        line.setBackgroundColor(getResources().getColor(R.color.dialog_line));
        line.setLayoutParams(new LinearLayoutCompat.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, util.dip2px(getContext(),1)));
        this.addView(line);
    }

    public void setTitle(String s){
        title.setText(s+"");
        titlename=s;
    }

    public void setText(String s){
        if (s==null || s.length()==0){
            expand.setVisibility(VISIBLE);
            chooseitem.setVisibility(GONE);
        }else {
            expand.setVisibility(GONE);
            chooseitem.setVisibility(VISIBLE);
        }
        chooseitem.setText(s+"");
        titlename=s+"";
    }

    public String getText(){
        if (chooseitem!=null){
            return chooseitem.getText().toString()+"";
        }
        return "";
    }

    @Override
    public void setOnClickListener(View.OnClickListener listener){
        wrapper.setOnClickListener(listener);
    }
}
