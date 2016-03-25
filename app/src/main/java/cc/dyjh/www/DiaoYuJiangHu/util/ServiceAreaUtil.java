package cc.dyjh.www.DiaoYuJiangHu.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cc.dyjh.www.DiaoYuJiangHu.bean.ServiceArea;
import dev.mirror.library.android.util.JsonUtils;

/**
 * Created by 王沛栋 on 2016/3/25.
 */
public class ServiceAreaUtil {

    private static List<ServiceArea> mCityData;
    public static List<ServiceArea> getCityList(Context context){
        if (mCityData == null){
            mCityData = new ArrayList<>();

            try {
                InputStreamReader inputStreamReader = new InputStreamReader(context.getAssets().open("service_area_data.json"), "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                StringBuilder stringBuilder = new StringBuilder();
                while((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                bufferedReader.close();
                inputStreamReader.close();

                mCityData = JsonUtils.parseList(stringBuilder.toString(),ServiceArea.class);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  mCityData;
    }
}
