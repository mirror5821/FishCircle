package cc.dyjh.www.DiaoYuJiangHu.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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
import dev.mirror.library.android.activity.MultiImageSelectorActivity;
import dev.mirror.library.android.util.ImageTools;
import dev.mirror.library.android.util.JsonUtils;

/**
 * Created by dongqian on 16/3/22.
 */
public class YuXunDetailsActivity<T> extends BaseActivity {
    private TextView mTvFYTime;//放鱼时间
    private TextView mTvType;//放鱼种类
    private TextView mEtJin;//放鱼斤数
    private TextView mTvKDTime;//开钓时间
    private TextView mTvXG;//是否限杆
    private TextView mTvSF;//收费标准
    private TextView mTvJY;//禁用钓饵
    private TextView mEtOther;//其他说明
    private Button mBtn;

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

    private ImageTools mImageTools;

    private static final int REQUSET_CODE_SF = 6001;
    private static final int REQUSET_CODE_YZ = 6002;
    private static final int REQUSET_CODE_ER = 6003;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yuxun_details);
        setBack();
        setTitleText("渔讯详情");

        mImageTools = new ImageTools(this);

        mTvFYTime = (TextView)findViewById(R.id.fangyu_time);//放鱼时间
        mTvType = (TextView)findViewById(R.id.fangyu_type);//放鱼种类
        mTvKDTime = (TextView)findViewById(R.id.kaidiao_time);//开钓时间
        mTvXG = (TextView)findViewById(R.id.is_xiangan);//是否限杆
        mTvSF = (TextView)findViewById(R.id.price_biaozhun);//收费标准
        mTvJY = (TextView)findViewById(R.id.limit_diaoer);//禁止钓饵
        mEtOther = (TextView) findViewById(R.id.other_say);//其他说明
        mEtJin = (TextView)findViewById(R.id.fangyu_weight);
        mBtn = (Button)findViewById(R.id.btn);
        mBtn.setVisibility(View.GONE);

        mTvFYTime.setOnClickListener(this);
        mTvType.setOnClickListener(this);
        mTvKDTime.setOnClickListener(this);
        mTvXG.setOnClickListener(this);
        mTvSF.setOnClickListener(this);
        mTvJY.setOnClickListener(this);
        mBtn.setOnClickListener(this);

        loadData();
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
                if (!TextUtils.isEmpty(mYZ)) {
                    mTvType.setText(OptionUtil.getYu(mYuChang.getYu(), mYZ));
                }


                mTvKDTime.setText(mYuXun.getDysj());//开钓时间
                mXG = mYuXun.getXgcd();//是否限杆
                if (!TextUtils.isEmpty(mXG)) {
                    mTvXG.setText(OptionUtil.getYu(mYuChang.getXiangan(), mXG));
                }

                mERStr = mYuXun.getJyez();//禁止钓饵
                if (!TextUtils.isEmpty(mERStr)) {
                    mTvJY.setText(OptionUtil.getYu(mYuChang.getEr(), mERStr));
                }

                mSf = mYuXun.getSfbz();//收费标准
                if (!TextUtils.isEmpty(mSf)) {
                    mTvSF.setText(mSf);
                    try{
                        String [] sfbz = mSf.split(" ");
                        StringBuilder sb = new StringBuilder();
                        sb.append("放鱼:日钓");
                        sb.append(sfbz[1]);
                        sb.append("元");
                        sb.append(sfbz[2]);
                        sb.append("小时");

                        sb.append(sfbz[3]);
                        sb.append("元");
                        sb.append(sfbz[4]);
                        sb.append("小时");

                        sb.append(sfbz[5]);
                        sb.append("元");
                        sb.append(sfbz[6]);
                        sb.append("小时");

                        sb.append(sfbz[7]);
                        sb.append("元");
                        sb.append(sfbz[8]);
                        sb.append("小时");

                        mTvSF.setText(sb.toString());
                    }catch (Exception e){

                    }
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
}
