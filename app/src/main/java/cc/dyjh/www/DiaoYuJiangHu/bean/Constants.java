package cc.dyjh.www.DiaoYuJiangHu.bean;

/**
 * Created by dongqian on 16/3/20.
 */
public interface Constants {
//    public static final String BASE_URL = "http://m.dyjh.cc/androidi.php?s=GRAPP/";
    //http://m.dyjh.cc/appi.php?s=User/griCheckLogin?phone=18312009596&pwd=96e79218965eb72c92a549dd5a330112  登录
    //http://m.dyjh.cc/appi.php?s=User/griGetImgVerify/rand/D3AQS8G52QJGT8O/QUERY/0  图片验证码
    public final static String INTENT_ID = "INTENT_ID";

    public static final String BASE_IMG_URL = "http://m.dyjh.cc";//头像主url
    public static final String BASE_URL = "http://m.dyjh.cc/appi.php?s=";//主url
    //http://m.dyjh.cc/appi.php?s=Version/griGetVersion

//    public final static String BASE_URL = "http://zmnyw.cn/index.php?s=/Home/Api/";
    public static final String LOGIN = "User/griCheckLogin";//登录
    public static final String GET_V_CODE = "User/griGetImgVerify";//获取图片验证码
    public static final String V_CODE = "User/griCheckImgVerify";//验证图片验证码
    public static final String V_PHONE_CODE = "User/griCheckSMSVerify";//验证手机验证码
    public static final String REGISTER = "User/griRegister";//注册
    public static final String PASS_FIND = "User/griFindPwd";//找回密码
    public static final String GET_PHONE_CODE = "User/griSendSMSCode";//获取手机验证码
    public static final String YUNCHANG_INFO = "Fishery/griGetFishery";//获取渔场资料Fishery/griGetFishery
    public static final String YUNCHANG_UPDATE = "Fishery/griEditFishery";//更新渔场资料/
    public static final String YUNCAHNG_SELECT_INFO2 = "Fisherymsg/griGetFisherymsg";//更新渔场资料  发布渔汛是用的这个
    public static final String YUNCAHNG_INFO_UPDATE= "Fisherymsg/griEditFisherymsg";//编辑渔汛信息
    public static final String YUXUN_LIST= "Fisherymsg/griGetFisherymsgs";//获取渔汛列表
    public static final String USER_NAME_UPDATE = "User/griEidtUserInfo";//编辑用户昵称
    public static final String USER_HEADER_UPDATE = "User/griUploadUserPic";//用户头像编辑
    public static final String YUCHANG_IMG_UPLOAD = "Fishery/griUploadFisheryImgs";//渔场照片上传  Fishery/griUploadFisheryImgs
    public static final String YUXUN_IMG_UPDATE = "Fisherymsg/griUploadFisherymsgImgs";//上传鱼汛照片
    public static final String VERSION_CHECK = "Version/griGetVersion";//检测新版本


    public static final String INDEX = "Index/griIndex";//首页数据
    public static final String USER_INFOMATION = "User/griGetUserInfo";//获取用户数据

    public final static String USER_INFO = "USER_INFO";
    public final static String USER_INFO_PHONE = "USER_INFO_PHONE";
    public final static String USER_INFO_PASS = "USER_INFO_PASS";

    //地址
    public final static int MAP_CODE1 = 3001;
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
