package cc.dyjh.www.DiaoYuJiangHu.activity;

import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;

import cc.dyjh.www.DiaoYuJiangHu.R;
import cc.dyjh.www.DiaoYuJiangHu.app.AppContext;
import cc.dyjh.www.DiaoYuJiangHu.util.AppAjaxCallback;
import cc.dyjh.www.DiaoYuJiangHu.util.AppHttpClient;
import dev.mirror.library.android.util.MD5Util;

/**
 * Created by 王沛栋 on 2016/3/23.
 */
public class UserInfoActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        setBack();
        setTitleText("我的渔场资料");

        loadData();
    }

    private void loadData(){
        Map<String,String> values = new HashMap<>();
        values.put("id", AppContext.user.getId());

        AppHttpClient mHttpClient = new AppHttpClient();
        mHttpClient.postData1(YUNCHANG_INFO, values, new AppAjaxCallback.onResultListener() {

            @Override
            public void onResult(String data, String msg) {

            }

            @Override
            public void onOtherResult(String data, int status) {

            }

            @Override
            public void onError(String msg) {

            }
        });
    }
}
