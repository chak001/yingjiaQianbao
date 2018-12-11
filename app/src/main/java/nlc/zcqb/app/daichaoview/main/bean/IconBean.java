package nlc.zcqb.app.daichaoview.main.bean;

/**
 * Created by lvqiu on 2018/12/3.
 */

public class IconBean {
    public String usid;
    public String pic;

    public IconBean(String usid, String pic) {
        this.usid = usid;
        this.pic = pic;
    }

    public String getUsid() {
        return usid;
    }

    public void setUsid(String usid) {
        this.usid = usid;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
