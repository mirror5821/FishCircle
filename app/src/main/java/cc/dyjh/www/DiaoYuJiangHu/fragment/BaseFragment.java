package cc.dyjh.www.DiaoYuJiangHu.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cc.dyjh.www.DiaoYuJiangHu.bean.App;
import cc.dyjh.www.DiaoYuJiangHu.bean.Constants;
import cc.dyjh.www.DiaoYuJiangHu.util.AppAjaxCallback;
import cc.dyjh.www.DiaoYuJiangHu.util.AppHttpClient;
import cc.dyjh.www.DiaoYuJiangHu.util.AppUtil;
import dev.mirror.library.android.fragment.DevBaseFragment;
import dev.mirror.library.android.util.JsonUtils;

/**
 * Created by dongqian on 16/3/20.
 */
public class BaseFragment extends DevBaseFragment implements Constants{
    public AppHttpClient mHttpClient = new AppHttpClient();

    private App mApp;
    public void checkVersion(){
        Map<String,String> values = new HashMap<>();
        values.put("phone", "hehe");
        mHttpClient.postData1(VERSION_CHECK, values, new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {
                mApp = JsonUtils.parse(data,App.class);

                int versionCode = AppUtil.getAppVersionCode(getActivity());

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

                }else{
                    showToast("您的是最新的版本!");
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
