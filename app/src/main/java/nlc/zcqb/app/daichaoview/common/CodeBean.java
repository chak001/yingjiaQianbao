package nlc.zcqb.app.daichaoview.common;

/**
 * Created by lvqiu on 2018/10/19.
 */

public class CodeBean {
    private int code;
    private String message;
    public String yzm;

    public CodeBean(String yzm) {
        this.yzm = yzm;
    }

    public String getYzm() {
        return yzm;
    }

    public void setYzm(String yzm) {
        this.yzm = yzm;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {

        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
