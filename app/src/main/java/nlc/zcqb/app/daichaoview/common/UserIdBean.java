package nlc.zcqb.app.daichaoview.common;

/**
 * Created by lvqiu on 2018/10/22.
 */

public class UserIdBean {
    private String usid;
    private String id;

    public UserIdBean(String usid, String id) {
        this.usid = usid;
        this.id = id;
    }

    public String getUsid() {
        return usid;
    }

    public void setUsid(String usid) {
        this.usid = usid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
