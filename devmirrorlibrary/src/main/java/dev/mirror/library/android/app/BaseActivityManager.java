package dev.mirror.library.android.app;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by mirror on 16/1/3.
 */
public class BaseActivityManager {
    private List<Activity> mList = new LinkedList<>();

    private static BaseActivityManager instance;
    public BaseActivityManager(){

    }

    /**
     * 单例
     * @return
     */
    public static BaseActivityManager getInstance(){
        if(null == instance){
            instance = new BaseActivityManager();
        }
        return instance;
    }

    /**
     * 添加到容器
     * @param activity
     */
    public void addActivity(Activity activity){
        mList.add(activity);
    }


    /**
     * 关闭所有activity
     */
    public void exit(){
        for (Activity a:mList){
            a.finish();
        }
        System.exit(0);
    }

}
