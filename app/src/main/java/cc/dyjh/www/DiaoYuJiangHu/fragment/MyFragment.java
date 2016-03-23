package cc.dyjh.www.DiaoYuJiangHu.fragment;

import android.os.Bundle;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import cc.dyjh.www.DiaoYuJiangHu.R;
import cc.dyjh.www.DiaoYuJiangHu.app.AppContext;
import cc.dyjh.www.DiaoYuJiangHu.util.AppAjaxCallback;

/**
 * Created by dongqian on 16/3/22.
 */
public class MyFragment extends BaseFragment {
    @Override
    public int setLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadData();
    }

    private void loadData(){
        final Map<String,String> values = new HashMap<>();
        values.put("id", AppContext.user.getId());

        mHttpClient.postData1(USER_INFOMATION, values, new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {
//                mIndex = JsonUtils.parse(data, Index.class);
//                mTvFuns.setText(mIndex.getCount1() + "");
//                mTvComment.setText(mIndex.getCount2() + "");
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
