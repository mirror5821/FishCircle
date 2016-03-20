package dev.mirror.library.android.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {
	public static String EMAIL = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";
	public static final String SPECIAL_CHARS = " `~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？";

	public static void closeIME(Context context) {
//		try {
//			InputMethodManager inputMethodManager = (InputMethodManager)context
//					.getSystemService("input_method");
//
//			inputMethodManager.hideSoftInputFromWindow(((Activity)context)
//					.getCurrentFocus().getWindowToken(),
//					2);
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
	}


	public static boolean isEmail(String email){
		Pattern regex = Pattern.compile(EMAIL);
		Matcher matcher = regex.matcher(email);
		return matcher.matches();
	}


	public static boolean isMobileNO(String mobiles)
	{
		Pattern p = 
				Pattern.compile("^0?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}

	public static boolean isInvalidPassWord(String password)
	{
		char[] chars = " `~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？".toCharArray();
		for (char c : chars) {
			if (password.contains(String.valueOf(c))) {
				return true;
			}
		}
		return false;
	}

	public static String hideRealPhone(String realPhone)
	{
		if (TextUtils.isEmpty(realPhone)) {
			return realPhone;
		}
		String temp = realPhone.substring(3, 7);
		return realPhone.replace(temp, "****");
	}

	public static String hideRealAccount(String realAccount)
	{
		if (TextUtils.isEmpty(realAccount)) {
			return realAccount;
		}
		String temp = realAccount.substring(6, 14);
		return realAccount.replace(temp, "********");
	}

	public static String hideRealEmail(String realEmail)
	{
		if (TextUtils.isEmpty(realEmail)) {
			return realEmail;
		}
		String temp = realEmail.substring(1, 3);
		return realEmail.replace(temp, "**");
	}

	public static boolean checkNet(Context context)
	{
		try {
			ConnectivityManager connectivity = (ConnectivityManager)context.getSystemService("connectivity");
//					.getSystemService("connectivity");
			if (connectivity != null)
			{
				NetworkInfo info = connectivity.getActiveNetworkInfo();
				if ((info != null) && (info.isConnected()))
				{
					if (info.getState() == NetworkInfo.State.CONNECTED)
						return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
