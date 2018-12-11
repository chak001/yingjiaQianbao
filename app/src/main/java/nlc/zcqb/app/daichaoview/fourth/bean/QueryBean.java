package nlc.zcqb.app.daichaoview.fourth.bean;

/**
 * Created by lvqiu on 2018/10/22.
 */

public class QueryBean {
    public Integer page;
    public String usid;

    public QueryBean(Integer page, String usid) {
        this.page = page;
        this.usid = usid;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getUsid() {
        return usid;
    }

    public void setUsid(String usid) {
        this.usid = usid;
    }
}
