package nlc.zcqb.baselibrary.callback;

/**
 * Created by lvqiu on 2018/12/1.
 */

public interface UpdateView {

    void updateData(Object bean);
    void getData(Object bean);
    int getNullCount();
}
