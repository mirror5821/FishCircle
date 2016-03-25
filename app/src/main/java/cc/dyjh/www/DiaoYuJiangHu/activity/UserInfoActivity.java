package cc.dyjh.www.DiaoYuJiangHu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.dyjh.www.DiaoYuJiangHu.R;
import cc.dyjh.www.DiaoYuJiangHu.app.AppContext;
import cc.dyjh.www.DiaoYuJiangHu.bean.User;
import cc.dyjh.www.DiaoYuJiangHu.bean.YuChang;
import cc.dyjh.www.DiaoYuJiangHu.util.AppAjaxCallback;
import cc.dyjh.www.DiaoYuJiangHu.util.AppHttpClient;
import cc.dyjh.www.DiaoYuJiangHu.util.OptionUtil;
import dev.mirror.library.android.util.JsonUtils;

/**
 * Created by 王沛栋 on 2016/3/23.
 * 用户资料
 */
public class UserInfoActivity extends BaseActivity {
    private TextView mTvPhone,mTvYZ,mTvTS,mTvAddress,mTvType,mTvPhoto;
    private TextView mEtName,mEtContacts,mEtMJ,mEtDW,mEtSS,mEtDec,mEtAddress2,mEtAge;

    private YuChang.Fishery mFishery;//渔场信息

    private YuChang mYuChang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        setBack();
        setTitleText("我的渔场资料");
        setRightTitle("编辑");



        mEtName = (TextView) findViewById(R.id.name);//渔场姓名
        mEtContacts = (TextView)findViewById(R.id.contacts);//负责人姓名
        mTvPhone = (TextView)findViewById(R.id.phone);//渔场电话
        mTvAddress = (TextView) findViewById(R.id.address);//地址
        mEtAddress2 = (TextView)findViewById(R.id.address2);//详细地址
        mEtMJ = (TextView)findViewById(R.id.fangyu_weight);//渔场面积
        mEtDW = (TextView)findViewById(R.id.dw_count);//钓位个数
        mEtSS = (TextView)findViewById(R.id.shuishen);//水深
        mTvYZ = (TextView)findViewById(R.id.yu_type);//放鱼鱼种
        mTvTS = (TextView)findViewById(R.id.tese);//渔场特色
        mEtDec = (TextView)findViewById(R.id.dec);//渔场描述
        mTvPhoto = (TextView)findViewById(R.id.photo);//渔场相片
        mEtAge = (TextView)findViewById(R.id.age);//渔场年限
        mTvType = (TextView)findViewById(R.id.type);//渔场类型

        loadData();
    }

    private void loadData(){
        Map<String,String> values = new HashMap<>();
        values.put("id", AppContext.ID+"");
        AppHttpClient mHttpClient = new AppHttpClient();
        mHttpClient.postData1(YUNCHANG_INFO, values, new AppAjaxCallback.onResultListener() {

            @Override
            public void onResult(String data, String msg) {
                mYuChang = JsonUtils.parse(data, YuChang.class);

                mFishery = mYuChang.getFishery().get(0);
                YuChang.FisheryArea fArea = mYuChang.getFisheryarea();

                mEtName.setText(mFishery.getFisheryname());//渔场姓名
                mEtContacts.setText(mFishery.getPrincipal());//负责人姓名
                mTvPhone.setText(mFishery.getPhone());//渔场电话
                mTvAddress.setText(fArea.getProvince()+fArea.getCity()+fArea.getDistrict());//省份地址
                mEtAddress2.setText(mFishery.getPosition());//详细地址
                mEtMJ.setText(mFishery.getAcreage());//渔场面积
                mEtDW.setText(mFishery.getSeatcount());//钓位个数
                mEtSS.setText(mFishery.getWaterdepth());// 水深
                mTvYZ.setText(OptionUtil.getYu(mYuChang.getYu(),mFishery.getFishtype()));//放鱼鱼种
                mTvTS.setText(OptionUtil.getYu(mYuChang.getFisheryfeature(),mFishery.getFisheryfeature()));//渔场特色
                mEtDec.setText(mFishery.getFdescribe());//渔场描述
                mTvPhoto.setText(mFishery.getAlbum());//渔场相片
                mEtAge.setText(mFishery.getFisheryage());//渔场年限
                mTvType.setText(OptionUtil.getYu(mYuChang.getFisherytype(),mFishery.getFisherytype()));//渔场类型

            }

            @Override
            public void onOtherResult(String data, int status) {
                showToast(status+"");
            }

            @Override
            public void onError(String msg) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.right_text:
                startActivity(new Intent(UserInfoActivity.this,UserInfoUpdateActivity.class));
                break;
        }
    }
}
