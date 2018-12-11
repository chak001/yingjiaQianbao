package nlc.zcqb.app.daichaoview.common;

/**
 * Created by lvqiu on 2018/10/19.
 */

public class CodeCheckBean {
    private String code;
    private String tel;

    public CodeCheckBean(String code, String tel) {
        this.code = code;
        this.tel = tel;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
