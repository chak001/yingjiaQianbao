package nlc.zcqb.app.daichaoview.login.bean;

/**
 * Created by lvqiu on 2018/10/19.
 */

public class LoginReqBean {

    public final static Integer PwdType=1;
    public final static Integer CodeType=2;

    private String acc;
    private String pass;
    private Integer state;
    private Integer code;

    public LoginReqBean() {
    }

    public LoginReqBean(String acc, String pass, Integer state, Integer code) {
        this.acc = acc;
        this.pass = pass;
        this.state = state;
        this.code = code;
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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
