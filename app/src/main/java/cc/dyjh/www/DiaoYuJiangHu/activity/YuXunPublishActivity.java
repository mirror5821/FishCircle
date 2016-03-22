package cc.dyjh.www.DiaoYuJiangHu.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import cc.dyjh.www.DiaoYuJiangHu.R;

/**
 * Created by dongqian on 16/3/22.
 */
public class YuXunPublishActivity extends BaseActivity {
    private TextView mTvFYTime;//放鱼时间
    private TextView mTvType;//放鱼种类
    private EditText mEtJin;//放鱼斤数
    private TextView mTvKDTime;//开钓时间
    private TextView mTvXG;//是否限杆
    private TextView mTvSF;//收费标准
    private TextView mTvJY;//禁用钓饵
    private TextView mTvOther;//其他说明
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yuxun_publish);

        mTvFYTime = (TextView)findViewById(R.id.fangyu_time);
        mTvType = (TextView)findViewById(R.id.fangyu_type);
        mTvKDTime = (TextView)findViewById(R.id.kaidiao_time);
        mTvXG = (TextView)findViewById(R.id.is_xiangan);
        mTvSF = (TextView)findViewById(R.id.price_biaozhun);
        mTvJY = (TextView)findViewById(R.id.limit_diaoer);
        mTvOther = (TextView)findViewById(R.id.other_say);

        mEtJin = (EditText)findViewById(R.id.fangyu_weight);

        mTvFYTime.setOnClickListener(this);
        mTvType.setOnClickListener(this);
        mTvKDTime.setOnClickListener(this);
        mTvXG.setOnClickListener(this);
        mTvSF.setOnClickListener(this);
        mTvJY.setOnClickListener(this);
        mTvOther.setOnClickListener(this);

    }
}
