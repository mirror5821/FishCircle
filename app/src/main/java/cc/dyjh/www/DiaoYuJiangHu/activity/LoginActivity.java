package cc.dyjh.www.DiaoYuJiangHu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashMap;
import java.util.Map;

import cc.dyjh.www.DiaoYuJiangHu.R;
import cc.dyjh.www.DiaoYuJiangHu.app.AppContext;
import cc.dyjh.www.DiaoYuJiangHu.bean.User;
import cc.dyjh.www.DiaoYuJiangHu.util.AppAjaxCallback;
import cc.dyjh.www.DiaoYuJiangHu.util.SharePreferencesUtil;
import dev.mirror.library.android.util.JsonUtils;
import dev.mirror.library.android.util.MD5Util;

/**
 * Created by dongqian on 16/3/20.
 */
public class LoginActivity extends BaseActivity {
    private Button mBtnLogin,mBtnRegister;
    private EditText mEtPhone;
    private EditText mEtPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        mEtPhone = (EditText)findViewById(R.id.name);
        mEtPass = (EditText)findViewById(R.id.pass);

        mEtPhone.setText("18837145615");//18312009596
        mEtPass.setText("111111");
        mBtnLogin = (Button)findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(this);

        mBtnRegister = (Button)findViewById(R.id.btn);
        mBtnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.btn_login:
                login();
                break;
            case R.id.btn:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                break;
        }
    }


    private void login(){
        final String phone = mEtPhone.getText().toString();
        final String pass = mEtPass.getText().toString();

        if(TextUtils.isEmpty(phone)){
            showToast(getString(R.string.input_phone));
            cancelProgressDialog();
            return;
        }

        if(TextUtils.isEmpty(pass)){
            showToast(getString(R.string.input_pass));
            cancelProgressDialog();
            return;
        }

        showProgressDialog("正在登录");
        Map<String,String> values = new HashMap<>();
        values.put("phone", phone);
        values.put("pwd", MD5Util.stringToMd5(pass));

        mHttpClient.postData1(LOGIN, values, new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {
                AppContext.user = JsonUtils.parse(data,User.class);
                showToast("登录成功");
                SharePreferencesUtil.saveLoginInfo(getApplicationContext(), phone, pass);
                SharePreferencesUtil.saveUserInfo(getApplicationContext(), data);

                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                cancelProgressDialog();
                finish();
            }

            @Override
            public void onOtherResult(String data, int status) {
                switch (status){
                    case 101:
                        AppContext.user = JsonUtils.parse(data,User.class);
                        SharePreferencesUtil.saveLoginInfo(getApplicationContext(), phone, pass);
                        SharePreferencesUtil.saveUserInfo(getApplicationContext(), data);

                        startActivity(new Intent(LoginActivity.this, UserSelectActivity.class));
                        cancelProgressDialog();
                        finish();
                        break;
                    case 103:
                        cancelProgressDialog();
                        showToast("用户不存在");
                        break;
                    default:
                        cancelProgressDialog();
                        showToast("登录");
                        break;
                }
            }

            @Override
            public void onError(String msg) {
                showToast("登录失败");
                cancelProgressDialog();
            }
        });



    }
}
