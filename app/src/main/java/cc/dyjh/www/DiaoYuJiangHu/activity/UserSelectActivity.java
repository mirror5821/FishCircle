package cc.dyjh.www.DiaoYuJiangHu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import cc.dyjh.www.DiaoYuJiangHu.R;
import dev.mirror.library.android.util.UIHelper;

/**
 * Created by 王沛栋 on 2016/3/23.
 */
public class UserSelectActivity extends BaseActivity {
    private TextView mTv1,mTv2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_select);

        mTv1 = (TextView)findViewById(R.id.tv1);
        mTv2 = (TextView)findViewById(R.id.tv2);

        mTv1.setOnClickListener(this);
        mTv2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tv1:
                UIHelper.makePhoneCall(UserSelectActivity.this,getString(R.string.service_phone));
                break;
            case R.id.tv2:
                startActivity(new Intent(UserSelectActivity.this,UserInfoRegisterActivity.class));
                finish();
                break;
        }
    }
}
