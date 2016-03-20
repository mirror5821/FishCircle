package cc.dyjh.www.DiaoYuJiangHu.util;

import org.xutils.http.RequestParams;

import dev.mirror.library.android.util.PhoneUtil;

/**
 * Created by dongqian on 16/3/20.
 */
public class AppAjaxParam extends RequestParams {
    public AppAjaxParam(String url){
        super(url, null, null, null);
    }


    public AppAjaxParam(String fName,String url){
        super(url, null, null, null);
        String appid = "999999";
        String chnl = "10086";
        String dev = PhoneUtil.MANUFACTURER+" "+PhoneUtil.MODEL;
        String m = fName;
        String os = "android";
        String t = "122545450";
        String ver = android.os.Build.VERSION.RELEASE;


        this.addHeader("Connection", "keep-alive");
        this.addHeader("Content-Type", "application/json");//multipart/form-data
        this.addHeader("appid", appid);
        this.addHeader("chnl", chnl);
        this.addHeader("dev", dev);
        this.addHeader("os", os);
        this.addHeader("os_ver", PhoneUtil.VERSION_RELEASE);
        this.addHeader("t", t);
        this.addHeader("ver", ver);
        this.addHeader("token", "userid");
        this.addHeader("m", fName);

    }

}
