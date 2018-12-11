package nlc.zcqb.app.daichaoview.second.bean;

/**
 * Created by lvqiu on 2018/10/24.
 */

public class PayMoneyQueryBean {
    public String money;
    public String number;
    public String  inter;

    public PayMoneyQueryBean(String money, String number, String inter) {
        this.money = money;
        this.number = number;
        this.inter = inter;
    }

    public PayMoneyQueryBean() {
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getInter() {
        return inter;
    }

    public void setInter(String inter) {
        this.inter = inter;
    }
}
