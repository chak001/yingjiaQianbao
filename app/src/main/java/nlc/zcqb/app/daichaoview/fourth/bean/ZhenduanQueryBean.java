package nlc.zcqb.app.daichaoview.fourth.bean;

/**
 * Created by lvqiu on 2018/10/22.
 */

public class ZhenduanQueryBean {
    public String usid;
    public String name;
    public String iden;
    public String tel;

    public ZhenduanQueryBean(String usid, String name, String iden, String tel) {
        this.usid = usid;
        this.name = name;
        this.iden = iden;
        this.tel = tel;
    }

    public String getUsid() {
        return usid;
    }

    public void setUsid(String usid) {
        this.usid = usid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIden() {
        return iden;
    }

    public void setIden(String iden) {
        this.iden = iden;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
