package dev.mirror.library.android.util;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

/**
 * Created by 王沛栋 on 2016/1/13.
 */
public class PhoneUtil {
    /**
     * cpu类型
     */
    public static String CPU_ABI= Build.CPU_ABI;
    public static String Product= Build.PRODUCT;
    /**
     * 手机型号
     */
    public static String MODEL= Build.MODEL;

    /**
     * 手机sdk版本号
     */
    public static int SDK= Build.VERSION.SDK_INT;
    public static String VERSION_RELEASE= Build.VERSION.RELEASE;
    public static String DISPLAY= Build.DEVICE;
    public static String BRAND= Build.BRAND;
    public static String BOARD= Build.BOARD;
    public static String FINGERPRINT= android.os.Build.FINGERPRINT;
    public static String ID= android.os.Build.ID;
    /**
     * 制造厂商
     */
    public static String MANUFACTURER= Build.MANUFACTURER;
    public static String USER= Build.USER;

    private static TelephonyManager mManager;
    private static void telephonyManagerInstance(Context context){
        if(mManager == null){
            mManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        }
    }

    /**
     * 获取电话状态
     * 1.tm.CALL_STATE_IDLE=0     无活动
     * 2.tm.CALL_STATE_RINGING=1  响铃
     * 3.tm.CALL_STATE_OFFHOOK=2  摘机
     * @param context
     * @return
     */
    public static int getCallState(Context context){
        telephonyManagerInstance(context);
        return mManager.getCallState();
    }

    /**
     * 获取设备id
     * GSM手机的IMEI CDMA手机的MEID
     * @param context
     * @return
     */
    public static String getDeviceId(Context context){
        telephonyManagerInstance(context);
        return mManager.getDeviceId();
    }

    /**
      * 设备的软件版本号：
      * 例如：the IMEI/SV(software version) for GSM phones.
      * Return null if the software version is not available.
      */
    public static String getDeviceSoftwareVersion(Context context){
        telephonyManagerInstance(context);
        return mManager.getDeviceSoftwareVersion();
    }


    /**
      * 手机号：
      * GSM手机的 MSISDN.
      * Return null if it is unavailable.
      */
    public static String getLine1Number(Context context){
          telephonyManagerInstance(context);
          return mManager.getLine1Number();
    }

    /**
     * Returns the MCC+MNC (mobile country code + mobile network code) of the provider of the SIM. 5 or 6 decimal digits.
     * 获取SIM卡提供的移动国家码和移动网络码.5或6位的十进制数字.
     * SIM卡的状态必须是 SIM_STATE_READY(使用getSimState()判断).
     * @return
     */
    public static String getSimOperator(Context context){
        telephonyManagerInstance(context);
        return mManager.getSimOperator();
    }

    /**
     * 服务商名称：
     * 例如：中国移动、联通
     * SIM卡的状态必须是 SIM_STATE_READY(使用getSimState()判断).
     * @param context
     * @return
     */
    public static String getSimOperatorName(Context context){
        telephonyManagerInstance(context);
        return mManager.getSimOperatorName();
    }
    /**
     * SIM的状态信息：
     * SIM_STATE_UNKNOWN          未知状态 0
     * SIM_STATE_ABSENT           没插卡 1
     * SIM_STATE_PIN_REQUIRED     锁定状态，需要用户的PIN码解锁 2
     * SIM_STATE_PUK_REQUIRED     锁定状态，需要用户的PUK码解锁 3
     * SIM_STATE_NETWORK_LOCKED   锁定状态，需要网络的PIN码解锁 4
     * SIM_STATE_READY            就绪状态 5
     * @param context
     * @return
     */
    public static int getSimState(Context context){
        telephonyManagerInstance(context);
        return mManager.getSimState();
    }

    /**
     * 唯一的用户ID：
     * 例如：IMSI(国际移动用户识别码) for a GSM phone.
     * 需要权限：READ_PHONE_STATE
     * @param context
     * @return
     */
    public static String getSubscriberId(Context context){
        telephonyManagerInstance(context);
        return mManager.getSubscriberId();
    }

    /**
     * 获取语音邮件号码：
     * 需要权限：READ_PHONE_STATE
     * @param context
     * @return
     */
    public static String getVoiceMailNumber(Context context){
        telephonyManagerInstance(context);
        return mManager.getVoiceMailNumber();
    }

    /**
     * ICC卡是否存在
     * @param context
     * @return
     */
    public static boolean hasIccCard(Context context){
        telephonyManagerInstance(context);
        return mManager.hasIccCard();
    }

    /**
     * 是否漫游
     * @param context
     * @return
     */
    public static boolean isNetworkRoaming(Context context){
        telephonyManagerInstance(context);
        return mManager.isNetworkRoaming();
    }

}
