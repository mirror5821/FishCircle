package cc.dyjh.www.DiaoYuJiangHu.activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengDialogButtonListener;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UpdateStatus;

import cc.dyjh.www.DiaoYuJiangHu.R;
import cc.dyjh.www.DiaoYuJiangHu.bean.Constants;
import cc.dyjh.www.DiaoYuJiangHu.util.AppHttpClient;
import dev.mirror.library.android.activity.DevBaseActivity;

/**
 * Created by dongqian on 16/3/20.
 */
public class BaseActivity extends DevBaseActivity implements Constants{
    public AppHttpClient mHttpClient = new AppHttpClient();
    private ImageView mImgBack;
    private TextView mTvTitleRight;
    private TextView mTvTitleBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);


        //友盟更新
        UmengUpdateAgent.update(this);
        //设置不仅在wifi下更新
        UmengUpdateAgent.setUpdateOnlyWifi(false);
        //		静默下载更新
        UmengUpdateAgent.silentUpdate(this);
        UmengUpdateAgent.setDialogListener(new UmengDialogButtonListener() {

            @Override
            public void onClick(int status) {
                switch (status) {
                    case UpdateStatus.Update:
                        UmengUpdateAgent.update(getApplicationContext());
                        break;
                }
            }
        });

        MobclickAgent.setDebugMode(true);
        //      SDK在统计Fragment时，需要关闭Activity自带的页面统计，
        //		然后在每个页面中重新集成页面统计的代码(包括调用了 onResume 和 onPause 的Activity)。
        MobclickAgent.openActivityDurationTrack(false);
        //		MobclickAgent.setAutoLocation(true);
        //		MobclickAgent.setSessionContinueMillis(1000);

        MobclickAgent.updateOnlineConfig(this);

    }

    /**
     * 设置后退事件
     */
    public void setBack(){
        mImgBack = (ImageView)findViewById(R.id.left_icon);
        mImgBack.setImageResource(R.mipmap.ic_back_w);
        mImgBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 设置titlebar文字
     * @param title
     */
    public void setTitleText(String title){
        mTvTitleBar = (TextView)findViewById(R.id.bar_title);
        mTvTitleBar.setText(title);
    }

    /**
     * 设置titlebar右侧文字和点击事件
     * @param str
     */

    public void setRightTitle(String str){
        mTvTitleRight = (TextView)findViewById(R.id.right_text);
        mTvTitleRight.setText(str);
        mTvTitleRight.setOnClickListener(this);
    }

}
