package dev.mirror.library.android.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

/**
 * Created by dongqian on 16/1/3.
 */
public class DevBaseActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 获取版本名称
     * @return
     */
    public String getVersionName(){
        PackageManager packageManager = getPackageManager();
        try{
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(),0);
            return packageInfo.versionName;
        }catch (PackageManager.NameNotFoundException e){
            return null;
        }
    }

    /**
     * 获取版本号
     * －1为未获取到版本号
     * @return
     */
    public int getVersionCode(){
        PackageManager packageManager = getPackageManager();
        try{
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(),0);
            return packageInfo.versionCode;
        }catch (PackageManager.NameNotFoundException e){
            return -1;
        }
    }

    /**
     * showToast
     * @param str
     */
    public void showToast(String str){
        Toast.makeText(getApplicationContext(),str,Toast.LENGTH_LONG).show();
    }

    private ProgressDialog mProgressDialog;
    /**
     * 显示加载dialog
     * @param msg
     */
    public void showProgressDialog(String msg){
        if(mProgressDialog == null){
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(true);
        }
        //如果显示的字符为空，则设置默认值
        if(TextUtils.isEmpty(msg)){
            msg = "正在加载数据...";
        }
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }

    /**
     * 显示加载dialog
     * @param msg
     * @param cancelable
     */
    public void showProgressDialog(String msg,boolean cancelable){
        if(mProgressDialog == null){
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(cancelable);
        }
        //如果显示的字符为空，则设置默认值
        if(TextUtils.isEmpty(msg)){
            msg = "正在加载数据...";
        }
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }
    /**
     * 取消加载dialog
     */
    public void cancelProgressDialog(){
        if(mProgressDialog != null){
            mProgressDialog.cancel();
        }
    }

    private AlertDialog.Builder mBuilder;
    /**
     * 弹出提示框
     * @param title
     * @param msg
     * @param btnStr
     * @param l
     */
    public void showNormalDialog(String title,String msg,String btnStr,DialogInterface.OnClickListener l){
        if(mBuilder == null){
            mBuilder = new AlertDialog.Builder(this);
        }
        mBuilder.setTitle(title);
        mBuilder.setMessage(msg);
        mBuilder.setPositiveButton(btnStr, l);
        mBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.dismiss();
            }
        });
        Dialog d = mBuilder.create();
        d.show();
    }

    /**
     * 弹出提示框
     * @param title
     * @param msg
     * @param btnStr1
     * @param l1
     * @param btnStr2
     * @param l2
     */
    public void showNormalDialogByTwoButton(String title,String msg,String btnStr1,DialogInterface.OnClickListener l1,String btnStr2
            ,DialogInterface.OnClickListener l2){
        if(mBuilder == null){
            mBuilder = new AlertDialog.Builder(this);
        }
        mBuilder.setTitle(title);
        mBuilder.setMessage(msg);
        mBuilder.setCancelable(false);
        mBuilder.setPositiveButton(btnStr1, l1);
        mBuilder.setNegativeButton(btnStr2, l2);
        Dialog d = mBuilder.create();
        d.show();
    }


    /**
     * 弹出自定义view
     */
    public void showNormalDialogByCustomerView(View view,DialogIface dialogIface){
        if(mBuilder == null){
            mBuilder = new AlertDialog.Builder(this);
        }
        mBuilder.setView(view);
        Dialog d = mBuilder.create();

        dialogIface.d(d);
        d.show();

    }

    @Override
    public void onClick(View v) {

    }

    public interface DialogIface{
        void d(Dialog dialog);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
