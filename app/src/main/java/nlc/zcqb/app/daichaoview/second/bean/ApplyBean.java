package nlc.zcqb.app.daichaoview.second.bean;

/**
 * Created by lvqiu on 2018/12/14.
 */

public class ApplyBean {
    public String usid;
    public String money;
    public String stage;
    public String id;

    public ApplyBean() {
    }

    public ApplyBean(String money, String stage, String id) {
        this.money = money;
        this.stage = stage;
        this.id = id;
    }

    public ApplyBean(String usid, String money, String stage, String id) {
        this.usid = usid;
        this.money = money;
        this.stage = stage;
        this.id = id;
    }

    public String getUsid() {
        return usid;
    }

    public void setUsid(String usid) {
        this.usid = usid;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
