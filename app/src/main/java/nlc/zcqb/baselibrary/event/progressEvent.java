package nlc.zcqb.baselibrary.event;

/**
 * Created by lvqiu on 2017/11/5.
 */

public class progressEvent {
    String ACTION;
    String absolutepath;
    String name;
    int progress;

    public progressEvent(String name, int progress) {
        this.name = name;
        this.progress = progress;
    }

    public progressEvent(String absolutepath, String name, int progress) {
        this.absolutepath = absolutepath;
        this.name = name;
        this.progress = progress;
    }

    public progressEvent(String ACTION, String absolutepath, String name, int progress) {
        this.ACTION = ACTION;
        this.absolutepath = absolutepath;
        this.name = name;
        this.progress = progress;
    }

    public String getACTION() {
        return ACTION;
    }

    public void setACTION(String ACTION) {
        this.ACTION = ACTION;
    }

    public String getAbsolutepath() {
        return absolutepath;
    }

    public void setAbsolutepath(String absolutepath) {
        this.absolutepath = absolutepath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
