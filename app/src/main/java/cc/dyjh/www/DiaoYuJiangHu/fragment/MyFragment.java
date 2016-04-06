package cc.dyjh.www.DiaoYuJiangHu.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import cc.dyjh.www.DiaoYuJiangHu.R;
import cc.dyjh.www.DiaoYuJiangHu.activity.LoginActivity;
import cc.dyjh.www.DiaoYuJiangHu.activity.UserCenterActivity;
import cc.dyjh.www.DiaoYuJiangHu.activity.UserInfoActivity;
import cc.dyjh.www.DiaoYuJiangHu.app.AppContext;
import cc.dyjh.www.DiaoYuJiangHu.bean.User;
import cc.dyjh.www.DiaoYuJiangHu.util.AppAjaxCallback;
import cc.dyjh.www.DiaoYuJiangHu.util.SharePreferencesUtil;
import dev.mirror.library.android.util.JsonUtils;
import dev.mirror.library.android.util.UIHelper;

/**
 * Created by mirror on 16/3/22.
 *
 */
public class MyFragment extends BaseFragment {
    private ImageView mImgHeader;
    private TextView mTvName,mTvPhone;
    private TextView mTvMy,mTvUser,mTvFeedBack,mTvLogout,mTvVersion;
    @Override
    public int setLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mImgHeader = (ImageView)view.findViewById(R.id.header);
        mTvName = (TextView)view.findViewById(R.id.name);
        mTvPhone = (TextView)view.findViewById(R.id.phone);
        mTvLogout = (TextView)view.findViewById(R.id.tv_logout);
        mTvVersion = (TextView)view.findViewById(R.id.tv_version);
        mTvVersion.setOnClickListener(this);

        mTvMy = (TextView)view.findViewById(R.id.tv_my);
        mTvUser = (TextView)view.findViewById(R.id.tv_user);
        mTvFeedBack = (TextView)view.findViewById(R.id.tv_feedback);
        mTvMy.setOnClickListener(this);
        mTvUser.setOnClickListener(this);
        mTvFeedBack.setOnClickListener(this);
        mImgHeader.setOnClickListener(this);
        mTvLogout.setOnClickListener(this);

        initView();

    }

    private void initView(){
        if(AppContext.user == null){
            loadData();
        }else{
            mUser = AppContext.user;
            if(!TextUtils.isEmpty(mUser.getPic())){
                AppContext.displayHeaderImage(mImgHeader, AppContext.USER_HEADER);
            }

            mTvPhone.setText(mUser.getPhone());
            mTvName.setText(TextUtils.isEmpty(AppContext.USER_NAME)?"无名人士":AppContext.USER_NAME);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv_my:
                startActivity(new Intent(getActivity(), UserInfoActivity.class));
                break;
            case R.id.tv_user:
                startActivity(new Intent(getActivity(), UserCenterActivity.class));
                break;
            case R.id.tv_feedback:
                UIHelper.makePhoneCall(getActivity(),getString(R.string.service_phone));
                break;
            case R.id.header:
                startActivity(new Intent(getActivity(), UserCenterActivity.class));
                break;
            case R.id.tv_logout:
                showNormalDialog("退出", "确定退出?", "确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        SharePreferencesUtil.deleteInfo(getActivity());
                        getActivity().finish();
                    }
                });

                break;
            case R.id.tv_version:
                checkVersion();
                break;
        }
    }

    private User mUser;
    private void loadData(){
        final Map<String,String> values = new HashMap<>();
        values.put("id", AppContext.ID+"");

        mHttpClient.postData1(USER_INFOMATION, values, new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {
                mUser = JsonUtils.parse(data, User.class);
                AppContext.user = mUser;
                if(!TextUtils.isEmpty(mUser.getPic())){
                    AppContext.USER_HEADER =  BASE_IMG_URL + mUser.getPic();
                    AppContext.displayHeaderImage(mImgHeader, AppContext.USER_HEADER);
                }
                mTvPhone.setText(mUser.getPhone());
                mTvName.setText(mUser.getName());
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
