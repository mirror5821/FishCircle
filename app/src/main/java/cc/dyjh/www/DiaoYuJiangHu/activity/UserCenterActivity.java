package cc.dyjh.www.DiaoYuJiangHu.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cc.dyjh.www.DiaoYuJiangHu.R;
import cc.dyjh.www.DiaoYuJiangHu.app.AppContext;
import cc.dyjh.www.DiaoYuJiangHu.bean.User;

/**
 * Created by mirror on 16/3/24.
 * 个人资料
 */
public class UserCenterActivity extends BaseActivity {
    private ImageView mImgHeader;
    private TextView mTvName,mTvPhone;
    private TextView mTvName1,mTvPhone1;

    private User mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        mImgHeader = (ImageView)findViewById(R.id.header);
        mTvName = (TextView)findViewById(R.id.name);
        mTvPhone = (TextView)findViewById(R.id.phone);
        mTvName1 = (TextView)findViewById(R.id.name1);
        mTvPhone1 = (TextView)findViewById(R.id.phone1);

        mUser = AppContext.user;
        AppContext.displayHeaderImage(mImgHeader, BASE_IMG_URL + mUser.getPic());
        mTvPhone.setText(mUser.getPhone());
        mTvName.setText(mUser.getName());
        mTvPhone1.setText(mUser.getPhone());
        mTvName1.setText(mUser.getName());

        mTvName1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.name1:
                startActivityForResult(new Intent(UserCenterActivity.this,EditSingleTextActivity.class).
                        putExtra(INTENT_ID,mUser.getName()),2001);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            switch (requestCode) {
                case 2001:
                    Uri nameData = data.getData();
                    String name = nameData.toString();
                    mTvName1.setText(name);
                    mTvName.setText(name);
                    break;

            }
        }
    }
}
