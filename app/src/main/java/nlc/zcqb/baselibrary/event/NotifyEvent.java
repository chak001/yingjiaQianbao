package nlc.zcqb.baselibrary.event;

/**
 * Created by lvqiu on 2017/11/13.
 */

public class NotifyEvent {
    private int id;
    private String title;
    private String subtitle;
    private int progress;

    public NotifyEvent(int id, String title, String subtitle, int progress) {
        this.id = id;
        this.title = title;
        this.subtitle = subtitle;
        this.progress = progress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
