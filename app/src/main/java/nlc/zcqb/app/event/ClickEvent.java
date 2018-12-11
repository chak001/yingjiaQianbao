package nlc.zcqb.app.event;

/**
 * Created by lvqiu on 2018/3/8.
 */

public class ClickEvent {
    public String path;
    public String key;
    public String destination;

    public ClickEvent(String path, String destination) {
        this.path = path;
        this.destination = destination;
    }

    public ClickEvent(String path, String key, String destination) {
        this.path = path;
        this.key = key;
        this.destination = destination;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
