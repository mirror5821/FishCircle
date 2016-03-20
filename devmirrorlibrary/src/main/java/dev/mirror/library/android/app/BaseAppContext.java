package dev.mirror.library.android.app;

import android.app.Application;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import org.xutils.image.ImageOptions;
import org.xutils.x;

/**
 * Created by mirror on 16/1/3.
 */
public class BaseAppContext  extends Application{
    private final static String IMG_TOP = "";

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化xutils
        x.Ext.init(this);
        // 是否输出debug日志
        x.Ext.setDebug(false);
    }

    /**
     * 加载网络图片
     * @param v
     * @param url
     */
    public static void displayImage(ImageView v,String url){
        if(!TextUtils.isEmpty(url)){
            if(!url.startsWith("http://")){
                x.image().bind(v,IMG_TOP+url);
            }else{
                x.image().bind(v,url);
            }
        }
    }

    /**
     * 根据ImageOptions加载图片
     * @param v
     * @param url
     * @param options
     */
    public static void displayImageWithImageOptions(ImageView v,String url,ImageOptions options){
        if(!TextUtils.isEmpty(url)){
            x.image().bind(v,url,options);
        }
    }

    /**
     * 清楚图片缓存
     */
    public static void cleanImageCache(){
        x.image().clearCacheFiles();
        x.image().clearCacheFiles();
    }



}
