package cc.dyjh.www.DiaoYuJiangHu.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.xutils.common.Callback;
import org.xutils.x;

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
 * Created by dongqian on 16/3/22.
 */
public class RegisterActivity extends BaseActivity {
    private EditText mEtPhone,mEtVCode,mEtCode,mEtPass;
    private Button mBtnCode,mBtn;
    private ImageView mImg;

    private String mUUId;
    private boolean isVCode = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setBack();
        setTitleText("注册");

        mEtCode = (EditText)findViewById(R.id.code);
        mEtVCode = (EditText)findViewById(R.id.v_code);
        mEtPhone = (EditText)findViewById(R.id.phone);
        mEtPass = (EditText)findViewById(R.id.pass);

        mBtnCode = (Button)findViewById(R.id.btn_code);
        mBtn = (Button)findViewById(R.id.btn);

        mImg = (ImageView)findViewById(R.id.img);

        mImg.setOnClickListener(this);
        mBtnCode.setOnClickListener(this);
        mBtn.setOnClickListener(this);


        mEtVCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //如果等于四位数则验证验证码
                if(mEtVCode.getText().toString().length() == 4){
                    vCode();
                }
            }
        });

        //加载验证码
        loadVImg();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.img:
                loadVImg();
                break;
            case R.id.btn_code:
                getPhoneCode();
                break;
            case R.id.btn:
                vPhoneCode();
                break;
        }
    }

    /**
     * 获取验证码
     */
    private void loadVImg(){
        mUUId= java.util.UUID.randomUUID().toString().replaceAll("-","").substring(0,15).toUpperCase();

        x.image().bind(mImg, "http://m.dyjh.cc/appi.php?s=User/griGetImgVerify/rand/" + mUUId + "/QUERY/0", null, new Callback.CommonCallback<Drawable>() {
            @Override
            public void onSuccess(Drawable result) {
                mImg.setImageDrawable(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                showToast("获取验证码失败");
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 验证验证码
     */
    private void vCode(){
        String vCode = mEtVCode.getText().toString().toLowerCase();
        if(TextUtils.isEmpty(vCode)){
            showToast(getString(R.string.input_v_code));
            return;
        }
        //参数 rand:标识,verify:用户输入验证码
        Map<String,String> values = new HashMap<>();
        values.put("verify", vCode);
        values.put("rand",mUUId);


        mHttpClient.postData1(V_CODE, values, new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {
                isVCode = true;
            }

            @Override
            public void onOtherResult(String data, int status) {

            }

            @Override
            public void onError(String msg) {
                isVCode = false;
            }
        });


    }

    /**
     * 获取验证码
     */
    private void getPhoneCode(){
        String phone = mEtPhone.getText().toString();
        if(TextUtils.isEmpty(phone)){
            showToast(getString(R.string.input_phone));
            return;
        }


        Map<String,String> values = new HashMap<>();
        values.put("phone", phone);
        values.put("rand",mUUId);


        mHttpClient.postData1(GET_PHONE_CODE, values, new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {
                showToast("获取成功");
            }

            @Override
            public void onOtherResult(String data, int status) {

            }

            @Override
            public void onError(String msg) {
                showToast("操作失败");
            }
        });
    }

    private void vPhoneCode(){

        String code = mEtCode.getText().toString().toLowerCase();
        if(TextUtils.isEmpty(code)){
            showToast(getString(R.string.input_code));
            return;
        }
        showProgressDialog("正在注册");
        //参数 rand:标识,verify:
        Map<String,String> values = new HashMap<>();
        values.put("verify", code);
        values.put("rand",mUUId);


        mHttpClient.postData1(V_PHONE_CODE, values, new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {
                isVCode = true;
                sub();
            }

            @Override
            public void onOtherResult(String data, int status) {
                cancelProgressDialog();
            }

            @Override
            public void onError(String msg) {
                cancelProgressDialog();
                isVCode = false;
            }
        });
    }

    private void sub(){
        String phone = mEtPhone.getText().toString();
        String pass = mEtPass.getText().toString();
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

        if(!isVCode){
            showToast("请验证手机验证码");
            cancelProgressDialog();
            return;
        }
        //参数 rand:标识,phone:手机号,pwd:密码
        Map<String,String> values = new HashMap<>();
        values.put("phone", phone);
        values.put("pwd", pass);
        values.put("rand",mUUId);


        mHttpClient.postData1(REGISTER, values, new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {
                login();
                showToast("注册成功,正在登录");
                cancelProgressDialog();
                showProgressDialog("正在登录");
            }

            @Override
            public void onOtherResult(String data, int status) {
                cancelProgressDialog();
            }

            @Override
            public void onError(String msg) {
                cancelProgressDialog();
                showToast("注册失败，请重新操作");
            }
        });
    }

    private void login(){
        final String phone = mEtPhone.getText().toString();
        final String pass = mEtPass.getText().toString();

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

                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            }

            @Override
            public void onOtherResult(String data, int status) {
                switch (status){
                    case 101:
                        AppContext.user = JsonUtils.parse(data,User.class);
                        SharePreferencesUtil.saveLoginInfo(getApplicationContext(), phone, pass);
                        startActivity(new Intent(RegisterActivity.this, UserSelectActivity.class));
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
                showToast("err-------" + msg);
            }
        });
    }

}
