package nlc.zcqb.baselibrary.baseview;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import org.greenrobot.eventbus.EventBus;

/**
 * Created by lvqiu on 2017/11/13.
 */

public class BaseService extends Service{
    private String EVENTACTION="";

    public static final String EXTRA_PATH = "intentservice.extra.PATH";
    public static final String KEY = "intentservice.extra.KEY";
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
        EVENTACTION=this.getClass().getName();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public String getClassName() {
        return EVENTACTION;
    }

}
