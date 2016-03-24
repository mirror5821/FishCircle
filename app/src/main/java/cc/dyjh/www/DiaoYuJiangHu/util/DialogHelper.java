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

        mTimeBuilder.setTitle("请选择配送时间!");
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

}
