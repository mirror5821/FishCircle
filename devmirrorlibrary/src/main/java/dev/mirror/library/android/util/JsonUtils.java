package dev.mirror.library.android.util;

import android.text.TextUtils;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils{
	/**
	 * 解析JSONObject
	 * @param data
	 * @param class1
	 * @return
	 */
	public static <T> T parse(String data, Class<T> class1){
		return new Gson().fromJson(data, class1);
	}

	/**
	 * 解析JSONArray
	 * @param data
	 * @param class1
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> List<T> parseList(String data, Class<T> class1){
		if (TextUtils.isEmpty(data)) {
			return null;
		}

		try {
			List mList = new ArrayList();
			JSONArray mArray = new JSONArray(data);
			int size = mArray.length();
			for (int i = 0; i < size; i++) {
				Object t = parse(mArray.getJSONObject(i).toString(), class1);
				mList.add(t);
			}
			return mList;
		}
		catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 实体类转bean
	 * beanToString
	 * @param o
	 * @param class1
	 * @return
	 */
	public static <T> String beanToString(Object o,Class<T> class1){
		return new Gson().toJson(o,class1).toString();
	}


	/**
	 * 实体类集合转string(未验证正确性)
	 * @param lists
	 * @param class1
	 * @return
	 */
	public static <T> String listToString(List<T> lists,Class<T> class1){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < lists.size(); i++) {
			sb.append(new Gson().toJson(lists.get(i),class1).toString());
			sb.append(",");
		}
		return "["+sb.toString().substring(0,sb.length()-1)+"]";
	}


}