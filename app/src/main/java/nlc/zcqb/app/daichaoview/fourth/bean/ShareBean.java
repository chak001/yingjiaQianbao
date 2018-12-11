package nlc.zcqb.app.daichaoview.fourth.bean;

/**
 * Created by lvqiu on 2018/12/11.
 */

public class ShareBean {
    public String logo;
    public String title;
    public String introd;
    public String url;
    public String localPath;

    public ShareBean(String logo, String title, String introd, String url) {
        this.logo = logo;
        this.title = title;
        this.introd = introd;
        this.url = url;
    }

    public ShareBean() {
    }

    public String getLocalPath() {
        return localPath;
    }

    public void setLocalPath(String localPath) {
        this.localPath = localPath;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntrod() {
        return introd;
    }

    public void setIntrod(String introd) {
        this.introd = introd;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
