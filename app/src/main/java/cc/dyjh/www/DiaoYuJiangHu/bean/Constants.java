package cc.dyjh.www.DiaoYuJiangHu.bean;

/**
 * Created by dongqian on 16/3/20.
 */
public interface Constants {
//    public static final String BASE_URL = "http://m.dyjh.cc/androidi.php?s=GRAPP/";
    //http://m.dyjh.cc/appi.php?s=User/griCheckLogin?phone=18312009596&pwd=96e79218965eb72c92a549dd5a330112  登录
    //http://m.dyjh.cc/appi.php?s=User/griGetImgVerify/rand/D3AQS8G52QJGT8O/QUERY/0  图片验证码

    public static final String BASE_IMG_URL = "http://m.dyjh.cc";//头像主url
    public static final String BASE_URL = "http://m.dyjh.cc/appi.php?s=";//主url

//    public final static String BASE_URL = "http://zmnyw.cn/index.php?s=/Home/Api/";
    public static final String LOGIN = "User/griCheckLogin";
    public static final String GET_V_CODE = "User/griGetImgVerify";
    public static final String GET_YUXUN = "getYuxun";

    public final static String USER_INFO = "USER_INFO";
    public final static String USER_INFO_PHONE = "USER_INFO_PHONE";
    public final static String USER_INFO_PASS = "USER_INFO_PASS";
}
