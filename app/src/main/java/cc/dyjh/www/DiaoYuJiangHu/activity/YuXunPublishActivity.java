package cc.dyjh.www.DiaoYuJiangHu.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.dyjh.www.DiaoYuJiangHu.R;
import cc.dyjh.www.DiaoYuJiangHu.app.AppContext;
import cc.dyjh.www.DiaoYuJiangHu.bean.DialogInterface;
import cc.dyjh.www.DiaoYuJiangHu.bean.YuChang;
import cc.dyjh.www.DiaoYuJiangHu.bean.YuXun;
import cc.dyjh.www.DiaoYuJiangHu.iface.TimeInterface;
import cc.dyjh.www.DiaoYuJiangHu.util.AppAjaxCallback;
import cc.dyjh.www.DiaoYuJiangHu.util.AppHttpClient;
import cc.dyjh.www.DiaoYuJiangHu.util.DialogHelper;
import cc.dyjh.www.DiaoYuJiangHu.util.OptionUtil;
import cc.dyjh.www.DiaoYuJiangHu.util.UIUtil;
import dev.mirror.library.android.util.JsonUtils;

/**
 * Created by dongqian on 16/3/22.
 */
public class YuXunPublishActivity<T> extends BaseActivity {
    private TextView mTvFYTime;//放鱼时间
    private TextView mTvType;//放鱼种类
    private EditText mEtJin;//放鱼斤数
    private TextView mTvKDTime;//开钓时间
    private TextView mTvXG;//是否限杆
    private TextView mTvSF;//收费标准
    private TextView mTvJY;//禁用钓饵
    private EditText mEtOther;//其他说明

    private YuChang mYuChang;
    private List<YuChang.Yu> mFishType;//渔场特色
    private List<YuChang.Yu> mXianGan;//限杆
    private int mXGId;
    private List<YuChang.Yu> mER;//禁止钓饵
    private YuXun mYuXun;

    private String mSf;//收费标准
    private String mYZ;//放鱼种类
    private String mXG;//是否限杆
    private String mERStr;//禁用钓饵

    private static final int REQUSET_CODE_SF = 6001;
    private static final int REQUSET_CODE_YZ = 6002;
    private static final int REQUSET_CODE_ER = 6003;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yuxun_publish);
        setBack();
        setTitleText("发布渔讯");
        setRightTitle("发布");

        mTvFYTime = (TextView)findViewById(R.id.fangyu_time);//放鱼时间
        mTvType = (TextView)findViewById(R.id.fangyu_type);//放鱼种类
        mTvKDTime = (TextView)findViewById(R.id.kaidiao_time);//开钓时间
        mTvXG = (TextView)findViewById(R.id.is_xiangan);//是否限杆
        mTvSF = (TextView)findViewById(R.id.price_biaozhun);//收费标准
        mTvJY = (TextView)findViewById(R.id.limit_diaoer);//禁止钓饵
        mEtOther = (EditText) findViewById(R.id.other_say);//其他说明
        mEtJin = (EditText)findViewById(R.id.fangyu_weight);

        mTvFYTime.setOnClickListener(this);
        mTvType.setOnClickListener(this);
        mTvKDTime.setOnClickListener(this);
        mTvXG.setOnClickListener(this);
        mTvSF.setOnClickListener(this);
        mTvJY.setOnClickListener(this);

        loadData();
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.price_biaozhun:
                startActivityForResult(new Intent(YuXunPublishActivity.this,PriceBZActivity.class),REQUSET_CODE_SF);
                break;
            case R.id.kaidiao_time:
                DialogHelper.initSelectTime(YuXunPublishActivity.this, new TimeInterface() {
                    @Override
                    public void getData(String data) {
                        mTvKDTime.setText(data);
                    }

                    @Override
                    public void getHour(String time) {

                    }
                });
                break;
            case R.id.fangyu_time:
                DialogHelper.initSelectTime(YuXunPublishActivity.this, new TimeInterface() {
                    @Override
                    public void getData(String data) {
                        mTvFYTime.setText(data);
                    }

                    @Override
                    public void getHour(String time) {

                    }
                });
                break;
            case R.id.fangyu_type:
                startActivityForResult(new Intent(YuXunPublishActivity.this,CheckBoxSelectActivity.class).
                        putParcelableArrayListExtra(INTENT_ID, (ArrayList<? extends Parcelable>) mFishType)
                                .putExtra("SELECT_TYPE", mYZ),
                        REQUSET_CODE_YZ);
                break;
            case R.id.is_xiangan:
                initSelectView(1, (List<T>) mXianGan);
                break;
            case R.id.limit_diaoer:
                startActivityForResult(new Intent(YuXunPublishActivity.this,CheckBoxSelectActivity.class).
                                putParcelableArrayListExtra(INTENT_ID, (ArrayList<? extends Parcelable>) mER)
                                .putExtra("SELECT_TYPE",mERStr),
                        REQUSET_CODE_ER);
                break;
            case R.id.right_text:
                sub();
                break;
        }
    }

    /**
     *
     * @param type 1鱼种类  2渔场特色
     * @param mList
     */
    private void initSelectView(final int type, final List<T> mList){
        UIUtil uiHelper = new UIUtil();
        uiHelper.initSelectListView(YuXunPublishActivity.this, mList, new DialogInterface() {
            @Override
            public void getPosition(int position) {
                switch (type) {
                    case 1:
                        mXianGan.get(position);
                        mXG = mXianGan.get(position).getName();
                        mTvXG.setText(mXG);
                        mXG = mXianGan.get(position).getId()+"";
                        break;

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            switch (requestCode) {
                case REQUSET_CODE_SF:
                    Uri sfData = data.getData();
                    mSf = sfData.toString();
                    mTvSF.setText("已编辑");

                    break;
                case REQUSET_CODE_YZ:
                    Uri yzData = data.getData();
                    mYZ = yzData.toString();
                    mTvType.setText(OptionUtil.getYu(mYuChang.getYu(), mYZ));
//                    mTvType.setText("已编辑");
                    break;
                case REQUSET_CODE_ER:
                    Uri erData = data.getData();
                    mERStr = erData.toString();
                    mTvJY.setText(OptionUtil.getYu(mYuChang.getEr(), mERStr));
                    break;
            }
        }
    }



    private void loadData(){
        Map<String,String> values = new HashMap<>();
        values.put("id", AppContext.ID+"");

        AppHttpClient mHttpClient = new AppHttpClient();
        mHttpClient.postData1(YUNCAHNG_SELECT_INFO2, values, new AppAjaxCallback.onResultListener() {

            @Override
            public void onResult(String data, String msg) {
                mYuChang = JsonUtils.parse(data, YuChang.class);

                mFishType = mYuChang.getYu();
                mXianGan = mYuChang.getXiangan();
                mER = mYuChang.getEr();
                mYuXun = mYuChang.getYuxun();

                mTvFYTime.setText(mYuXun.getFysj());//放鱼时间
                mYZ = mYuXun.getFyzl();//放鱼种类
                if(!TextUtils.isEmpty(mYZ)){
                    mTvType.setText(OptionUtil.getYu(mYuChang.getYu(), mYZ));
                }



                mTvKDTime.setText(mYuXun.getDysj());//开钓时间
                mXG = mYuXun.getXgcd();//是否限杆
                if(!TextUtils.isEmpty(mXG)){
                    mTvXG.setText(OptionUtil.getYu(mYuChang.getXiangan(), mXG));
                }

                mERStr = mYuXun.getJyez();//禁止钓饵
                if(!TextUtils.isEmpty(mERStr)){
                    mTvJY.setText(OptionUtil.getYu(mYuChang.getEr(), mERStr));
                }

                mSf = mYuXun.getSfbz();//收费标准
                if(!TextUtils.isEmpty(mSf)){
                    mTvSF.setText(mSf);
                }
                mEtOther.setText(mYuXun.getQtsm());//其他说明
                mEtJin.setText(mYuXun.getFyjs());//放鱼斤数
            }

            @Override
            public void onOtherResult(String data, int status) {

            }

            @Override
            public void onError(String msg) {
                showToast(msg);
            }
        });
    }

    private void sub(){
        String otherDec = mEtOther.getText().toString();
        String dkTime = mTvKDTime.getText().toString();
        String fyTime = mTvFYTime.getText().toString();
        String js = mEtJin.getText().toString();
        if(TextUtils.isEmpty(mYZ)){
            showToast("请选择放鱼种类");
            return;
        }

        if(TextUtils.isEmpty(mXG)){
            showToast("请选择是否限杆");
            return;
        }
        if(TextUtils.isEmpty(mERStr)){
            showToast("请选择是否禁用钓饵");
            return;
        }
        if(TextUtils.isEmpty(otherDec)){
            showToast("请输入其他描述");
            return;
        }
        if(TextUtils.isEmpty(dkTime)){
            showToast("请选择开钓时间");
            return;
        }
        if(TextUtils.isEmpty(mSf)){
            showToast("请编辑收费标准");
            return;
        }
        if(TextUtils.isEmpty(fyTime)){
            showToast("请选择放鱼时间");
            return;
        }
        if(TextUtils.isEmpty(js)){
            showToast("请输入放鱼斤数");
            return;
        }

        //"http://m.dyjh.cc/appi.php?s=Fisherymsg/griEditFisherymsg?fyzl=2 5 8 9&fhid=8&xgcd=20&jyez=13 14
        // &qtsm=qi ta shuo Ming&dysj=2016年03月24日09时&sfbz=1 100 6 100 5 50 5 50 5&fysj=2016年03月24日09时&fyjs=100"
        showProgressDialog("正在发布");
        Map<String,String> values = new HashMap<>();
        values.put("fyzl", mYZ);
        values.put("fhid", mYuChang.getFhid()+"");
        values.put("xgcd", mXG);
        values.put("jyez", mERStr);
        values.put("qtsm", otherDec);
        values.put("dysj", dkTime);
        values.put("sfbz", mSf);
        values.put("fysj", fyTime);
        values.put("fyjs", js);

        mHttpClient.postData1(YUNCAHNG_INFO_UPDATE, values, new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {
                showToast("发布成功");
                cancelProgressDialog();
                finish();
            }

            @Override
            public void onOtherResult(String data, int status) {
                switch (status){
                    case 201:
                        showToast("缺少参数");
                        break;
                    case 301:
                        showToast("保存失败");
                        break;
                    case 300:
                        showToast("数据不正确");
                        break;
                    default:
                        showToast("发布失败");
                        break;
                }
                cancelProgressDialog();
            }

            @Override
            public void onError(String msg) {
                showToast("发布失败");
                cancelProgressDialog();
            }
        });
    }
}
