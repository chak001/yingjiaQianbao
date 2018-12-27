package nlc.zcqb.baselibrary.event;

/**
 * Created by lvqiu on 2017/6/7.
 */

public class DataSynEvent {
    private String currentActivity;

    public DataSynEvent(String currentActivity) {
        this.currentActivity = currentActivity;
    }

    public String getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(String currentActivity) {
        this.currentActivity = currentActivity;
    }
}