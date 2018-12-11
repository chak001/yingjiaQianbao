package nlc.zcqb.app.daichaoview.login.bean;

/**
 * Created by lvqiu on 2018/10/20.
 */

public class RegisterBean {
    private String acc;
    private String pass;
    private Integer code;
    private Integer inid; //可以为空，第三方推荐id

    public RegisterBean(String acc, String pass, Integer code) {
        this.acc = acc;
        this.pass = pass;
        this.code = code;
    }

    public RegisterBean() {
    }

    public String getAcc() {
        return acc;
    }

    public void setAcc(String acc) {
        this.acc = acc;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getInid() {
        return inid;
    }

    public void setInid(Integer inid) {
        this.inid = inid;
    }
}
