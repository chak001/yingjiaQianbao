package nlc.zcqb.app.daichaoview.second.bean;

import nlc.zcqb.baselibrary.util.StringUtils;

/**
 * Created by lvqiu on 2018/10/21.
 */

public class PingTaiDetailBean {

    /**
     * loan_id : 156
     * logo : http://ycmua.com//loan/Public/upload/img2.png
     * succ : 65
     * title : 有借必应
     * keyword : 下款快
     * monrate : 0.03
     * lo_time : 10分钟
     * lo_number : 765
     * peonum : 1518
     * a_line : 秒下款 高通过率 操作简单
     * quali :                           1.芝麻分570分就能下款2 .20-45岁大陆公民3.无不良记录
     * introd : 1、年满20周岁，已实名认证用户可申请；2、手续简单，审核快，24小时在线服务。足不出户便可完成在线申请和审核，操作便利，费用透明；友情提示：请填写真实信息，进行实名认证后，方可操作。
     * type1 : 5
     * type2 : 1
     * sort : 19
     * hot : 2
     * recom : 2
     * you_url : https://at.umeng.com/GXvWjy
     * control : 2
     * create_time : 2018-09-21 16:33:37
     * money : 3000
     * term : 1
     * reMoney : 3027
     */

    private String loan_id;
    private String logo;
    private String succ;
    private String title;
    private String keyword;
    private String monrate;
    private String lo_time;
    private String lo_number;
    private String peonum;
    private String a_line;
    private String quali;
    private String introd;
    private String type1;
    private String type2;
    private String sort;
    private String hot;
    private String recom;
    private String you_url;
    private String control;
    private String create_time;
    private String money;
    private String term;
    private String reMoney;

    public String getLoan_id() {
        return loan_id;
    }

    public void setLoan_id(String loan_id) {
        this.loan_id = loan_id;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getSucc() {
        return succ;
    }

    public void setSucc(String succ) {
        this.succ = succ;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getMonrate() {
        return monrate;
    }

    public void setMonrate(String monrate) {
        this.monrate = monrate;
    }

    public String getLo_time() {
        return lo_time;
    }

    public void setLo_time(String lo_time) {
        this.lo_time = lo_time;
    }

    public String getLo_number() {
        return lo_number;
    }

    public void setLo_number(String lo_number) {
        this.lo_number = lo_number;
    }

    public String getPeonum() {
        return peonum;
    }

    public void setPeonum(String peonum) {
        this.peonum = peonum;
    }

    public String getA_line() {
        String s= StringUtils.getNullString(a_line).trim();
        return s;
    }

    public void setA_line(String a_line) {
        this.a_line = StringUtils.getNullString(a_line).trim();
    }

    public String getQuali() {
        String temp= StringUtils.getNullString(quali).trim();
        return temp;
    }

    public void setQuali(String quali) {
        String temp= StringUtils.getNullString(quali).trim();
        this.quali =temp;
    }

    public String getIntrod() {
        String temp= StringUtils.getNullString(introd).trim();
        return temp;
    }

    public void setIntrod(String introd) {
        this.introd = StringUtils.getNullString(introd).trim();
    }

    public String getType1() {
        return type1;
    }

    public void setType1(String type1) {
        this.type1 = type1;
    }

    public String getType2() {
        return type2;
    }

    public void setType2(String type2) {
        this.type2 = type2;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getHot() {
        return hot;
    }

    public void setHot(String hot) {
        this.hot = hot;
    }

    public String getRecom() {
        return recom;
    }

    public void setRecom(String recom) {
        this.recom = recom;
    }

    public String getYou_url() {
        return you_url;
    }

    public void setYou_url(String you_url) {
        this.you_url = you_url;
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getReMoney() {
        return reMoney;
    }

    public void setReMoney(String reMoney) {
        this.reMoney = reMoney;
    }
}
