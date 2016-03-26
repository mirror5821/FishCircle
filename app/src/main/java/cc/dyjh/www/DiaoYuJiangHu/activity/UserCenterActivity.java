package cc.dyjh.www.DiaoYuJiangHu.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.dyjh.www.DiaoYuJiangHu.R;
import cc.dyjh.www.DiaoYuJiangHu.app.AppContext;
import cc.dyjh.www.DiaoYuJiangHu.bean.User;
import cc.dyjh.www.DiaoYuJiangHu.util.AppAjaxCallback;
import dev.mirror.library.android.activity.MultiImageSelectorActivity;
import dev.mirror.library.android.util.ImageTools;

/**
 * Created by mirror on 16/3/24.
 * 个人资料
 */
public class UserCenterActivity extends BaseActivity {
    private ImageView mImgHeader;
    private TextView mTvName,mTvPhone;
    private TextView mTvName1,mTvPhone1;

    private ImageTools mImageTools;
    private User mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        mImageTools = new ImageTools(this);
        mList = new ArrayList<>();
        mImgHeader = (ImageView)findViewById(R.id.header);
        mTvName = (TextView)findViewById(R.id.name);
        mTvPhone = (TextView)findViewById(R.id.phone);
        mTvName1 = (TextView)findViewById(R.id.name1);
        mTvPhone1 = (TextView)findViewById(R.id.phone1);

        mUser = AppContext.user;
        AppContext.displayHeaderImage(mImgHeader, BASE_IMG_URL + mUser.getPic());
        mTvPhone.setText(mUser.getPhone());
        mTvName.setText(TextUtils.isEmpty(mUser.getName())?"未设置昵称":mUser.getName());
//        mTvName.setText(mUser.getName());
        mTvPhone1.setText(mUser.getPhone());
//        mTvName1.setText(mUser.getName());
        mTvName1.setText(TextUtils.isEmpty(mUser.getName())?"未设置昵称":mUser.getName());

        mTvName1.setOnClickListener(this);

        mImgHeader.setOnClickListener(this);
    }

    private List<String> mList;
    private ArrayList<String> mSelectPath;
    private static final int REQUEST_IMAGE = 2;
    private void openImage(){
        int selectedMode = MultiImageSelectorActivity.MODE_MULTI;
//        selectedMode = MultiImageSelectorActivity.MODE_SINGLE;


        int maxNum = 1;
        Intent intent = new Intent(UserCenterActivity.this, MultiImageSelectorActivity.class);
        // 是否显示拍摄图片
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        // 最大可选择图片数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, maxNum);
        // 选择模式
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, selectedMode);
        // 默认选择
//        if (mSelectPath != null && mSelectPath.size() > 0) {
//            intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
//        }
        if (mList != null && mList.size() > 1) {
            mList.remove(mList.size() - 1);
            intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, (ArrayList) mList);
        }
        startActivityForResult(intent, REQUEST_IMAGE);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.name1:
                startActivityForResult(new Intent(UserCenterActivity.this,EditSingleTextActivity.class).
                        putExtra(INTENT_ID,mUser.getName()),2001);
                break;
            case R.id.header:
                openImage();
                break;
        }
    }

    private String mName;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            switch (requestCode) {
                case 2001:
                    Uri nameData = data.getData();
                    mName = nameData.toString();
                    update();
                    break;

                case REQUEST_IMAGE:
                    mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    upload();
                    break;
            }

        }
    }

    private void update(){
        showProgressDialog("正在提交数据");
        Map<String,String> values = new HashMap<>();
        values.put("id", AppContext.ID+"");
        values.put("name", mName);


        mHttpClient.postData1(USER_NAME_UPDATE, values, new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {
                AppContext.USER_NAME = mName;
                mTvName.setText(mName);
                mTvName1.setText(mName);

                showToast("操作成功");
                cancelProgressDialog();
            }

            @Override
            public void onOtherResult(String data, int status) {
                switch (status) {
                    case 101:

                        finish();
                        break;
                    case 103:

                        break;
                    default:

                        break;

                }
                cancelProgressDialog();
                showToast("操作失败");
            }

            @Override
            public void onError(String msg) {
                cancelProgressDialog();
                showToast("操作失败");
            }
        });

    }

    private void upload(){
        showProgressDialog("正在提交数据");
        Map<String,String> values = new HashMap<>();
        values.put("id", AppContext.ID+"");
        values.put("imageData[]", mImageTools.filePathToString(mSelectPath.get(0)));//（照片1的流,照片2的流）
        values.put("imageType", "jpeg");

        mHttpClient.postData1(USER_HEADER_UPDATE, values, new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {

                showToast("操作成功");
                cancelProgressDialog();
                AppContext.USER_HEADER = BASE_IMG_URL+data;
                AppContext.displayImage(mImgHeader,AppContext.USER_HEADER);
            }

            @Override
            public void onOtherResult(String data, int status) {
                switch (status){
                    case 101:

                        finish();
                        break;
                    case 103:

                        break;
                    default:

                        break;

                }
                cancelProgressDialog();
                showToast("操作失败");
            }

            @Override
            public void onError(String msg) {
                cancelProgressDialog();
                showToast("操作失败");
            }
        });

    }
}
