package cc.dyjh.www.DiaoYuJiangHu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import cc.dyjh.www.DiaoYuJiangHu.R;
import cc.dyjh.www.DiaoYuJiangHu.app.AppContext;
import cc.dyjh.www.DiaoYuJiangHu.bean.YuChang;
import cc.dyjh.www.DiaoYuJiangHu.util.AppAjaxCallback;
import cc.dyjh.www.DiaoYuJiangHu.util.AppHttpClient;
import dev.mirror.library.android.util.JsonUtils;

/**
 * Created by 王沛栋 on 2016/3/23.
 */
public class UserInfoUpdateActivity extends BaseActivity {
    private TextView mTvPhone,mTvYZ,mTvTS,mTvAddress,mTvPhoto;
    private EditText mEtName,mEtContacts,mEtMJ,mEtDW,mEtSS,mEtDec;

    private YuChang mYuChang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_update);
        setBack();
        setTitleText("编辑渔场资料");
        mEtName = (EditText) findViewById(R.id.name);//渔场姓名
        mEtContacts = (EditText)findViewById(R.id.contacts);//负责人姓名
        mTvPhone = (TextView)findViewById(R.id.phone);//渔场电话
        mTvAddress = (TextView) findViewById(R.id.address);//地址
        mEtMJ = (EditText)findViewById(R.id.fangyu_weight);//渔场面积
        mEtDW = (EditText)findViewById(R.id.dw_count);//钓位个数
        mEtSS = (EditText)findViewById(R.id.shuishen);//水深
        mTvYZ = (TextView)findViewById(R.id.yu_type);//放鱼鱼种
        mTvTS = (TextView)findViewById(R.id.tese);//渔场特色
        mEtDec = (EditText)findViewById(R.id.dec);//渔场描述
        mTvPhoto = (TextView)findViewById(R.id.photo);

        mTvAddress.setOnClickListener(this);
        loadData();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.address:
                startActivity(new Intent(UserInfoUpdateActivity.this,MapSelectActivity.class));
                break;
        }
    }

    private void loadData(){
        Map<String,String> values = new HashMap<>();
        values.put("id", AppContext.user.getId());

        AppHttpClient mHttpClient = new AppHttpClient();
        mHttpClient.postData1(YUNCHANG_INFO, values, new AppAjaxCallback.onResultListener() {

            @Override
            public void onResult(String data, String msg) {
                mYuChang = JsonUtils.parse(data,YuChang.class);

                YuChang.Fishery fishery = mYuChang.getFishery().get(0);
                mTvPhone.setText(fishery.getPhone());
            }

            @Override
            public void onOtherResult(String data, int status) {

            }

            @Override
            public void onError(String msg) {

            }
        });
    }


    /**
     * fishtryid:渔场id,
     lan:经度,
     lat:纬度,
     area:地区,
     position:详细地址,
     fisheryname:渔场名,
     fisherytype:渔场类型,
     fishtype:放鱼种类,
     fisheryage:渔场年限,
     principal:负责人,
     phone:电话,
     seatcount:钓位,
     waterdepth:水深,
     acreage:面积,
     fisheryfeature:特色,
     fdescribe:描述
     */
}
