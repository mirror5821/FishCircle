package cc.dyjh.www.DiaoYuJiangHu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import cc.dyjh.www.DiaoYuJiangHu.R;
import cc.dyjh.www.DiaoYuJiangHu.activity.YuXunListActivity;
import cc.dyjh.www.DiaoYuJiangHu.activity.YuXunPublishActivity;
import cc.dyjh.www.DiaoYuJiangHu.app.AppContext;
import cc.dyjh.www.DiaoYuJiangHu.bean.Index;
import cc.dyjh.www.DiaoYuJiangHu.bean.User;
import cc.dyjh.www.DiaoYuJiangHu.util.AppAjaxCallback;
import dev.mirror.library.android.util.JsonUtils;
import dev.mirror.library.android.view.CircleImageView;

/**
 * Created by dongqian on 16/3/20.
 */
public class IndexFragment extends BaseFragment {
    @Override
    public int setLayoutId() {
        return R.layout.fragment_index;
    }

    private LinearLayout mView1,mView2,mView3,mView4;
    private TextView mTvFuns,mTvComment;
    private CircleImageView mImgHeader;
    private Index mIndex;
    private User mUser;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView1 = (LinearLayout)view.findViewById(R.id.view1);
        mView2 = (LinearLayout)view.findViewById(R.id.view2);
        mView3 = (LinearLayout)view.findViewById(R.id.view3);
        mView4 = (LinearLayout)view.findViewById(R.id.view4);

        mTvFuns = (TextView)view.findViewById(R.id.tv_fans);
        mTvComment = (TextView)view.findViewById(R.id.tv_comment);
        mImgHeader = (CircleImageView)view.findViewById(R.id.header);

        mView1.setOnClickListener(this);
        mView2.setOnClickListener(this);
        mView3.setOnClickListener(this);
        mView4.setOnClickListener(this);

        loadData();
        loadUserData();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.view1:
                startActivity(new Intent(getActivity(), YuXunPublishActivity.class));
                break;
            case R.id.view2:
                startActivity(new Intent(getActivity(), YuXunListActivity.class));
                break;
            case R.id.view3:
                showToast("功能建设中，敬请期待!");
                break;
            case R.id.view4:
                showToast("功能建设中，敬请期待!");
                break;
        }
    }

    private void loadData(){
        final Map<String,String> values = new HashMap<>();
        values.put("id", AppContext.user.getId());

        mHttpClient.postData1(INDEX, values, new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {
                mIndex = JsonUtils.parse(data, Index.class);
                AppContext.index = mIndex;
                mTvFuns.setText(mIndex.getCount1() + "");
                mTvComment.setText(mIndex.getCount2() + "");
            }

            @Override
            public void onOtherResult(String data, int status) {

            }

            @Override
            public void onError(String msg) {
            }
        });
    }


    private void loadUserData(){
        final Map<String,String> values = new HashMap<>();
        values.put("id", AppContext.user.getId());

        mHttpClient.postData1(USER_INFOMATION, values, new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {
                mUser = JsonUtils.parse(data, User.class);
                AppContext.user = mUser;
//                AppContext.displayHeaderImage(mImgHeader, BASE_IMG_URL + mUser.getPic());
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
