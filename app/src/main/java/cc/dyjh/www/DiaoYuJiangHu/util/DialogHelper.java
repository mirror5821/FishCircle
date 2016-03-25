package cc.dyjh.www.DiaoYuJiangHu.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import cc.dyjh.www.DiaoYuJiangHu.R;
import cc.dyjh.www.DiaoYuJiangHu.bean.ServiceArea;
import cc.dyjh.www.DiaoYuJiangHu.iface.AreaInterface;
import cc.dyjh.www.DiaoYuJiangHu.iface.TimeInterface;
import dev.mirror.library.android.view.WheelView;

/**
 * Created by 王沛栋 on 2016/3/24.
 */
public class DialogHelper {


    /**
     * 好吧 请接收的简化这个方法
     * @param activity
     */
    private static String mTimeStr;
    private static String mHourStr = null;
    public static void initSelectTime(Activity activity, final TimeInterface iface){
        //日期的集合
        List<String> mListDate = new ArrayList<String>();
        mListDate.clear();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");

        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String strNow = formatter.format(curDate);

        Date dateTime = new Date();

        for(int i=0;i<30;i++){
            try {
                dateTime = formatter.parse(strNow);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Calendar calendar = new GregorianCalendar();
            calendar.setTime(dateTime);

            calendar.add(calendar.DATE,i);//把日期往后增加一天.整数往后推,负数往前移动
            dateTime=calendar.getTime(); //这个时间就是日期往后推一天的结果

            mListDate.add(formatter.format(dateTime));
        }

        View outerView = LayoutInflater.from(activity).inflate(R.layout.view_wheelview, null);

        //日期wheel
        WheelView mWheelDate = (WheelView) outerView.findViewById(R.id.wheel1);
        mWheelDate.setItems(mListDate);

        //时间wheel
        final WheelView mWheelTime= (WheelView) outerView.findViewById(R.id.wheel2);

        //默认加载第一次的时间
        List<String> t = new ArrayList<String>();
        for(int j=0;j<25;j++){
            if(j<10){
                t.add("0"+j+"时");
            }else{
                t.add(j+"时");
            }
        }

        //设置默认时间
        mTimeStr = strNow;
        //设置时间
        mWheelTime.setItems(t);

        mWheelDate.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                mTimeStr = item.toString();

            }
        });

        mHourStr = "00时";
        mWheelTime.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                mHourStr = item;
            }
        });


        AlertDialog.Builder mTimeBuilder = new AlertDialog.Builder(activity);

        mTimeBuilder.setTitle("请选择时间!");
        mTimeBuilder.setView(outerView);
        mTimeBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                iface.getData(mTimeStr+mHourStr);//2016年03月24日09时
                iface.getHour(mHourStr);
                return;

            }
        });
        mTimeBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        Dialog mTimeDialog = mTimeBuilder.create();

        if(mTimeDialog.isShowing()){
            mTimeDialog.dismiss();
            mTimeDialog.cancel();
        }else{
            mTimeDialog.show();
        }

    }


    static List<ServiceArea> provinceList = new ArrayList<>();
    static List<ServiceArea.City> cityList= new ArrayList<>();
    static List<ServiceArea.City.District> districtList= new ArrayList<>();
    static int provinceId;
    static int cityId;
    static int districtId;

    static String provinceName;
    static String cityName;
    static String districtName;

    public static void initArea(Activity activity, final AreaInterface iface){
        provinceList.clear();
        cityList.clear();
        districtList.clear();
        provinceList.addAll(ServiceAreaUtil.getCityList(activity));
        cityList.addAll( provinceList.get(0).getCity());
        districtList.addAll(cityList.get(0).getDistrict());

        provinceId = provinceList.get(0).getProvince_id();
        cityId = cityList.get(0).getCity_id();
        districtId = districtList.get(0).getDistrict_id();

        provinceName = provinceList.get(0).getProvince_name();
        cityName = cityList.get(0).getCity_name();
        districtName = districtList.get(0).getDistrict_name();

        final List<String> mListProvince = new ArrayList<String>();

        for(int i=0;i<provinceList.size();i++){
            mListProvince.add(provinceList.get(i).getProvince_name());
        }

        View outerView = LayoutInflater.from(activity).inflate(R.layout.view_wheelview_area, null);
        //日期wheel
        WheelView mWheelProvince = (WheelView) outerView.findViewById(R.id.wheel1);
        mWheelProvince.setItems(mListProvince);

        //时间wheel
        final WheelView mWheelCity= (WheelView) outerView.findViewById(R.id.wheel2);

        //默认加载第一次的时间
        final List<String> mListCity = new ArrayList<String>();
        for(int j=0;j<cityList.size();j++){
           mListCity.add(cityList.get(j).getCity_name());
        }
        mWheelCity.setItems(mListCity);


        //时间wheel
        final WheelView mWheelDistrict= (WheelView) outerView.findViewById(R.id.wheel3);

        //默认加载第一次的时间
        final List<String> mListDistrict = new ArrayList<String>();
        for(int j=0;j<districtList.size();j++){
            mListDistrict.add(districtList.get(j).getDistrict_name());
        }
        mWheelDistrict.setItems(mListDistrict);


        mWheelProvince.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                System.out.println("--------------!!!!"+selectedIndex);

                cityList.clear();
                districtList.clear();

                mListCity.clear();
                mListDistrict.clear();

                cityList.addAll(provinceList.get(selectedIndex-1).getCity());
                districtList.addAll(cityList.get(0).getDistrict());
                for(int j=0;j<cityList.size();j++){
                    mListCity.add(cityList.get(j).getCity_name());
                }
                mWheelCity.setItems(mListCity);

                for(int j=0;j<districtList.size();j++){
                    mListDistrict.add(districtList.get(j).getDistrict_name());
                }
                mWheelDistrict.setItems(mListDistrict);

                try {
                    provinceId = provinceList.get(selectedIndex-1).getProvince_id();
                    cityId = cityList.get(0).getCity_id();
                    districtId = districtList.get(0).getDistrict_id();

                    provinceName = provinceList.get(selectedIndex-1).getProvince_name();
                    cityName = cityList.get(0).getCity_name();
                    districtName = districtList.get(0).getDistrict_name();
                }catch (Exception e){

                }

            }
        });

        mWheelCity.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                districtList.clear();
                mListDistrict.clear();

                System.out.println("---------------"+selectedIndex);

                districtList.addAll(cityList.get(selectedIndex-1).getDistrict());
                for(int j=0;j<districtList.size();j++){
                    mListDistrict.add(districtList.get(j).getDistrict_name());
                }
                mWheelDistrict.setItems(mListDistrict);

                try{
                    cityId = cityList.get(selectedIndex-1).getCity_id();
                    districtId = districtList.get(0).getDistrict_id();

                    cityName = cityList.get(selectedIndex-1).getCity_name();
                    districtName = districtList.get(0).getDistrict_name();
                }catch (Exception e){

                }


            }
        });


        mWheelDistrict.setOnWheelViewListener(new WheelView.OnWheelViewListener(){
            @Override
            public void onSelected(int selectedIndex, String item) {
                super.onSelected(selectedIndex, item);
                try {
                    districtId = districtList.get(selectedIndex-1).getDistrict_id();
                    districtName = districtList.get(selectedIndex-1).getDistrict_name();
                }catch (Exception e){

                }

            }
        });

        AlertDialog.Builder mTimeBuilder = new AlertDialog.Builder(activity);

        mTimeBuilder.setTitle("请选择地区!");
        mTimeBuilder.setView(outerView);
        mTimeBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String data = provinceId+","+cityId+","+districtId+";"+provinceName+","+cityName+","+districtName;
                iface.getAllData(data);

                return;

            }
        });
        mTimeBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        Dialog mTimeDialog = mTimeBuilder.create();

        if(mTimeDialog.isShowing()){
            mTimeDialog.dismiss();
            mTimeDialog.cancel();
        }else{
            mTimeDialog.show();
        }

    }

}
