package cc.dyjh.www.DiaoYuJiangHu.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengDialogButtonListener;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UpdateStatus;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cc.dyjh.www.DiaoYuJiangHu.R;
import cc.dyjh.www.DiaoYuJiangHu.bean.App;
import cc.dyjh.www.DiaoYuJiangHu.bean.Constants;
import cc.dyjh.www.DiaoYuJiangHu.util.AppAjaxCallback;
import cc.dyjh.www.DiaoYuJiangHu.util.AppHttpClient;
import cc.dyjh.www.DiaoYuJiangHu.util.AppUtil;
import dev.mirror.library.android.activity.DevBaseActivity;
import dev.mirror.library.android.util.JsonUtils;
import dev.mirror.library.android.util.MD5Util;

/**
 * Created by dongqian on 16/3/20.
 */
public class BaseActivity extends DevBaseActivity implements Constants{
    public AppHttpClient mHttpClient = new AppHttpClient();
    private ImageView mImgBack;
    private TextView mTvTitleRight;
    private TextView mTvTitleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);


        //友盟更新
        UmengUpdateAgent.update(this);
        //设置不仅在wifi下更新
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        //		静默下载更新
        UmengUpdateAgent.silentUpdate(this);
        UmengUpdateAgent.setDialogListener(new UmengDialogButtonListener() {

            @Override
            public void onClick(int status) {
                switch (status) {
                    case UpdateStatus.Update:
                        UmengUpdateAgent.update(getApplicationContext());
                        break;
                }
            }
        });

        MobclickAgent.setDebugMode(true);
        //      SDK在统计Fragment时，需要关闭Activity自带的页面统计，
        //		然后在每个页面中重新集成页面统计的代码(包括调用了 onResume 和 onPause 的Activity)。
        MobclickAgent.openActivityDurationTrack(false);
        //		MobclickAgent.setAutoLocation(true);
        //		MobclickAgent.setSessionContinueMillis(1000);

        MobclickAgent.updateOnlineConfig(this);
        checkVersion();

    }

    /**
     * 设置后退事件
     */
    public void setBack(){
        mImgBack = (ImageView)findViewById(R.id.left_icon);
        mImgBack.setImageResource(R.mipmap.ic_back_w);
        mImgBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 设置titlebar文字
     * @param title
     */
    public void setTitleText(String title){
        mTvTitleBar = (TextView)findViewById(R.id.bar_title);
        mTvTitleBar.setText(title);
    }

    /**
     * 设置titlebar右侧文字和点击事件
     * @param str
     */

    public void setRightTitle(String str){
        mTvTitleRight = (TextView)findViewById(R.id.right_text);
        mTvTitleRight.setText(str);
        mTvTitleRight.setOnClickListener(this);
    }

    private App mApp;
    public void checkVersion(){
        Map<String,String> values = new HashMap<>();
        values.put("phone", "hehe");
        mHttpClient.postData1(VERSION_CHECK, values, new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {
                mApp = JsonUtils.parse(data,App.class);

                int versionCode = AppUtil.getAppVersionCode(getApplication());

                /**
                 * 如果当前版本号小于更新版本号
                 */
                if(versionCode<mApp.getVcode()){
                    if(mApp.getIsupdate()==0){
                        showNormalDialog("检测到新版本", "是否更新？", "确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                downLoadApk();
                            }
                        });
                    }else{
                        downLoadApk();
                    }

                }

            }

            @Override
            public void onOtherResult(String data, int status) {

            }

            @Override
            public void onError(String msg) {

            }
        });
    }

    private String mFileStr = Environment.getExternalStorageDirectory().getPath()+"/dyjh.apk";
    public void downLoadApk(){
        showProgressDialog("正在下载新版本");
        final RequestParams params = new RequestParams(mApp.getUpdateurl());
        File f = new File(mFileStr);
        if(f.exists()){
            f.delete();
        }
        params.setSaveFilePath(mFileStr);
        Callback.Cancelable cancelable = x.http().get(params, new Callback.CommonCallback<File>() {

            @Override
            public void onSuccess(File result) {

                /*Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory().getPath() + "/dyjh.apk"),
                        "application/vnd.android.package-archive");
                *//*intent.setDataAndType(Uri.fromFile(new File(Environment
                                .getExternalStorageDirectory(), Config.UPDATE_SAVENAME)),
                        "application/vnd.android.package-archive");*/


                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(android.content.Intent.ACTION_VIEW);

                /* 调用getMIMEType()来取得MimeType */
                String type = "application/vnd.android.package-archive";
                /* 设置intent的file与MimeType */
                intent.setDataAndType(Uri.fromFile(result), type);

                startActivity(intent);

                cancelProgressDialog();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                cancelProgressDialog();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
