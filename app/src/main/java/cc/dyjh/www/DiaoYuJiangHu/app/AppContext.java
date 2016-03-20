package cc.dyjh.www.DiaoYuJiangHu.app;

import cc.dyjh.www.DiaoYuJiangHu.bean.User;
import dev.mirror.library.android.app.BaseAppContext;

/**
 * Created by dongqian on 16/3/20.
 */
public class AppContext extends BaseAppContext {
    public static User user;
    private static AppContext instance;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static AppContext getInstance(){

        return instance;
    }
}
