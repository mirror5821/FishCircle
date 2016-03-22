package cc.dyjh.www.DiaoYuJiangHu.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.HashMap;
import java.util.Map;

import cc.dyjh.www.DiaoYuJiangHu.R;
import cc.dyjh.www.DiaoYuJiangHu.util.AppAjaxCallback;
import cc.dyjh.www.DiaoYuJiangHu.util.AppHttpClient;

/**
 * Created by dongqian on 16/3/22.
 */
public class RegisterActivity extends BaseActivity {
    private EditText mEtPhone,mEtVCode,mEtCode,mEtPass;
    private Button mBtnCode,mBtn;
    private ImageView mImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setBack();
        setTitleText("注册");

        mEtCode = (EditText)findViewById(R.id.code);
        mEtVCode = (EditText)findViewById(R.id.v_code);
        mEtCode = (EditText)findViewById(R.id.phone);
        mEtPass = (EditText)findViewById(R.id.pass);

        mBtnCode = (Button)findViewById(R.id.btn_code);
        mBtn = (Button)findViewById(R.id.btn);

        mImg = (ImageView)findViewById(R.id.img);

        mBtnCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_code:
                loadVImg();
                break;
        }
    }

    private void loadVImg(){
        Map<String,String> values = new HashMap<>();
        values.put("rand", "fs323455");
        values.put("QUERY", "0");
        /*RequestParams params = new RequestParams(BASE_URL+LOGIN);
        params.addParameter("phone", phone);
        params.addParameter("pwd", MD5Util.stringToMd5(pass));*/

        AppHttpClient mHttpClient = new AppHttpClient();
        mHttpClient.postData1(GET_V_CODE, values, new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {
                showToast(msg);
            }

            @Override
            public void onError(String msg) {
                showToast("err-------" + msg);
            }
        });

    }
}
