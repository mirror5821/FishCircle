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
    public static final String LOGIN = "User/griCheckLogin";//登录
    public static final String GET_V_CODE = "User/griGetImgVerify";//获取图片验证码
    public static final String V_CODE = "User/griCheckImgVerify";//验证图片验证码
    public static final String V_PHONE_CODE = "User/griCheckSMSVerify";//验证手机验证码
    public static final String REGISTER = "User/griRegister";//注册
    public static final String GET_PHONE_CODE = "User/griSendSMSCode";//获取手机验证码
    public static final String YUNCHANG_INFO = "Fishery/griGetFishery";


    public static final String GET_YUXUN = "getYuxun";

    public final static String USER_INFO = "USER_INFO";
    public final static String USER_INFO_PHONE = "USER_INFO_PHONE";
    public final static String USER_INFO_PASS = "USER_INFO_PASS";

    //地址
    public final static String LAT = "LAT";
    public final static String LNG = "LNG";
    public final static String ADDRESS = "ADDRESS";
    public final static String CITY = "CITY";
    public final static String COUNTRY = "COUNTRY";
    public final static String DISTRICT = "DISTRICT";
    public final static String PROVINCE = "PROVINCE";
    public final static String NAME = "NAME";
    public final static String PHONE = "PHONE";
    public final static String ADDRESS_ID = "ADDRESS_ID";
    public final static String ADR = "ADR";
}
