package nlc.zcqb.app.daichaoview.fourth.bean;

import java.io.Serializable;

import nlc.zcqb.app.util.DC;
import nlc.zcqb.baselibrary.util.StringUtils;

/**
 * Created by lvqiu on 2018/12/1.
 */

public class PersonBean implements Serializable {

    /**
     * user_id : 1
     * pic : /upload/pic.png
     * name : null
     * nickname : 昵称
     * acc : 13027215267
     * tel : 13027215267
     * pass : 25f9e794323b453885f5181f1b624d0b
     * credit_score : 41
     * loan_id : 167,171,176,177,175,174,161,152,180,165
     * city : null
     * iden : null
     * marr : null
     * cul : null
     * occu : null
     * inform : null
     * monin : null
     * com_type : null
     * life : null
     * fund : null
     * socse : null
     * crecard : null
     * tb : null
     * houpro : null
     * carpro : null
     * loan : null
     * credit : null
     * create_time : 2018-10-26 20:47:59
     * log_time : null
     * entran : null
     * see_push : 0
     */

    private String user_id;
    private String pic;
    private String name;
    private String nickname;
    private String acc;
    private String tel;
    private String pass;
    private String credit_score;
    private String loan_id;
    private String city;
    private String iden;
    private String marr;
    private String cul;
    private String occu;
    private String inform;
    private String monin;
    private String com_type;
    private String life;
    private String fund;
    private String socse;
    private String crecard;
    private String tb;
    private String houpro;
    private String carpro;
    private String loan;
    private String credit;
    private String create_time;
    private String log_time;
    private String entran;
    private String see_push;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getCredit_score() {
        return credit_score;
    }

    public void setCredit_score(String credit_score) {
        this.credit_score = credit_score;
    }

    public String getLoan_id() {
        return loan_id;
    }

    public void setLoan_id(String loan_id) {
        this.loan_id = loan_id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIden() {
        return iden;
    }

    public void setIden(String iden) {
        this.iden = iden;
    }

    public String getMarr() {
        return marr;
    }

    public void setMarr(String marr) {
        this.marr = marr;
    }

    public String getCul() {
        return cul;
    }

    public void setCul(String cul) {
        this.cul = cul;
    }

    public String getOccu() {
        return occu;
    }

    public void setOccu(String occu) {
        this.occu = occu;
    }

    public String getInform() {
        return inform;
    }

    public void setInform(String inform) {
        this.inform = inform;
    }

    public String getMonin() {
        return monin;
    }

    public void setMonin(String monin) {
        this.monin = monin;
    }

    public String getCom_type() {
        return com_type;
    }

    public void setCom_type(String com_type) {
        this.com_type = com_type;
    }

    public String getLife() {
        return life;
    }

    public void setLife(String life) {
        this.life = life;
    }

    public String getFund() {
        return fund;
    }

    public void setFund(String fund) {
        this.fund = fund;
    }

    public String getSocse() {
        return socse;
    }

    public void setSocse(String socse) {
        this.socse = socse;
    }

    public String getCrecard() {
        return crecard;
    }

    public void setCrecard(String crecard) {
        this.crecard = crecard;
    }

    public String getTb() {
        return tb;
    }

    public void setTb(String tb) {
        this.tb = tb;
    }

    public String getHoupro() {
        return houpro;
    }

    public void setHoupro(String houpro) {
        this.houpro = houpro;
    }

    public String getCarpro() {
        return carpro;
    }

    public void setCarpro(String carpro) {
        this.carpro = carpro;
    }

    public String getLoan() {
        return loan;
    }

    public void setLoan(String loan) {
        this.loan = loan;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getLog_time() {
        return log_time;
    }

    public void setLog_time(String log_time) {
        this.log_time = log_time;
    }

    public String getEntran() {
        return entran;
    }

    public void setEntran(String entran) {
        this.entran = entran;
    }

    public String getSee_push() {
        return see_push;
    }

    public void setSee_push(String see_push) {
        this.see_push = see_push;
    }

    public static int getID(String name,String[] array){
        int r=0;
        if (StringUtils.getNullString(name).equals("")){
            return r;
        }
        for (int i=0;i<array.length;i++) {
            if (array[i].equals(name)){
                return i+1;
            }
        }
        return r;
    }

    public static String getName(int id,String[] array){
        if (id<1 || id>array.length){
            return "";
        }
        return array[id-1];
    }

}
