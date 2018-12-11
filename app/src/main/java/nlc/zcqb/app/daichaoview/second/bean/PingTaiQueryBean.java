package nlc.zcqb.app.daichaoview.second.bean;

/**
 * Created by lvqiu on 2018/10/21.
 */

public class PingTaiQueryBean {
    public Integer page;
    public Integer Seaval1;
    public Integer Seaval2;
    public Integer Seaval3;

    public PingTaiQueryBean(Integer seaval1, Integer seaval2, Integer seaval3) {
        page = 1;
        Seaval1 = seaval1;
        Seaval2 = seaval2;
        Seaval3 = seaval3;
    }

    public PingTaiQueryBean() {
    }

    public Integer getSeaval1() {
        return Seaval1;
    }

    public void setSeaval1(Integer seaval1) {
        Seaval1 = seaval1;
    }

    public Integer getSeaval2() {
        return Seaval2;
    }

    public void setSeaval2(Integer seaval2) {
        Seaval2 = seaval2;
    }

    public Integer getSeaval3() {
        return Seaval3;
    }

    public void setSeaval3(Integer seaval3) {
        Seaval3 = seaval3;
    }
}

