package cc.dyjh.www.DiaoYuJiangHu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import cc.dyjh.www.DiaoYuJiangHu.R;
import cc.dyjh.www.DiaoYuJiangHu.activity.YuXunPublishActivity;
import cc.dyjh.www.DiaoYuJiangHu.app.AppContext;
import cc.dyjh.www.DiaoYuJiangHu.bean.Index;
import cc.dyjh.www.DiaoYuJiangHu.util.AppAjaxCallback;
import dev.mirror.library.android.util.JsonUtils;

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
    private Index mIndex;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView1 = (LinearLayout)view.findViewById(R.id.view1);
        mView2 = (LinearLayout)view.findViewById(R.id.view2);
        mView3 = (LinearLayout)view.findViewById(R.id.view3);
        mView4 = (LinearLayout)view.findViewById(R.id.view4);

        mTvFuns = (TextView)view.findViewById(R.id.tv_fans);
        mTvComment = (TextView)view.findViewById(R.id.tv_comment);

        mView1.setOnClickListener(this);
        mView2.setOnClickListener(this);
        mView3.setOnClickListener(this);
        mView4.setOnClickListener(this);

        loadData();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.view1:
                startActivity(new Intent(getActivity(), YuXunPublishActivity.class));
                break;
            case R.id.view2:
                break;
            case R.id.view3:
                break;
            case R.id.view4:
                break;
        }
    }

    private void loadData(){
        final Map<String,String> values = new HashMap<>();
        values.put("id", AppContext.user.getId());

        mHttpClient.postData1(INDEX, values, new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {
                mIndex = JsonUtils.parse(data,Index.class);
                mTvFuns.setText(mIndex.getCount1()+"");
                mTvComment.setText(mIndex.getCount2()+"");
            }

            @Override
            public void onOtherResult(String data, int status) {

            }

            @Override
            public void onError(String msg) {
            }
        });
    }

}
