package nlc.zcqb.app.event;

/**
 * Created by lvqiu on 2017/11/6.
 */

public class CommandEvent {
    public final static String CLEAR_USER ="CLEAR_USER";
    public final static String GOTO_LOGIN ="GOTO_LOGIN";
    public final static String GOTO_REGISTER ="GOTO_Register";
    public final static String CHANGEPAGE ="VIEWPAGE_CHANGE";
    public final static String LOGIN_SUCCESS ="LOGIN_SUCCESS";
    public final static String UPLOAD_ICON ="UPLOAD_ICON";
    public final static String DOWN_APP ="DOWN_APP";
    String Type;
    String command;

    public CommandEvent(String type, String command) {
        Type = type;
        this.command = command;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
