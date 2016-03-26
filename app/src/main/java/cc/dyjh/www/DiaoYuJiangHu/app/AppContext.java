package cc.dyjh.www.DiaoYuJiangHu.app;

import com.baidu.mapapi.SDKInitializer;

import cc.dyjh.www.DiaoYuJiangHu.bean.Index;
import cc.dyjh.www.DiaoYuJiangHu.bean.User;
import dev.mirror.library.android.app.BaseAppContext;

/**
 * Created by dongqian on 16/3/20.
 */
public class AppContext extends BaseAppContext {
    //位置信息
    public static double Longitude =0;
    public static double Latitude = 0;
    public static String Address;
    public static String LOC_CITY = "郑州市";
    public static String LOC_AREA = "金水区";

    public static String USER_HEADER;

    public static User user;
    public static Index index;
    private static AppContext instance;
    public static int ID;

    public static String LOGIN_PHONE;
    public static String LOGIN_PASS;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);

    }

    public static AppContext getInstance(){

        return instance;
    }
}
