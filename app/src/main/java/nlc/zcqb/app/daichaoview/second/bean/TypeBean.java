package nlc.zcqb.app.daichaoview.second.bean;

/**
 * Created by lvqiu on 2018/10/21.
 */

public class TypeBean {


    /**
     * id : 5
     * name : 小额极速借
     */

    private Integer id;
    private String name;

    public TypeBean(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
