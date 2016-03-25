package cc.dyjh.www.DiaoYuJiangHu.activity;

import java.util.HashMap;
import java.util.Map;

import cc.dyjh.www.DiaoYuJiangHu.R;
import cc.dyjh.www.DiaoYuJiangHu.app.AppContext;
import cc.dyjh.www.DiaoYuJiangHu.bean.Index;
import cc.dyjh.www.DiaoYuJiangHu.util.AppAjaxCallback;
import dev.mirror.library.android.Holder.DevRecyclerViewHolder;
import dev.mirror.library.android.util.JsonUtils;

/**
 * Created by dongqian on 16/3/24.
 */
public class YuXunListActivity extends BaseRecyclerViewActivity{
    @Override
    public int setLayoutById() {
        return R.layout.activity_base_recyclerview;
    }

    @Override
    public void loadData() {
        final Map<String,String> values = new HashMap<>();
        values.put("id", AppContext.ID+"");
        values.put("status","0");

        mHttpClient.postData1(YUXUN_LIST, values, new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {
            }

            @Override
            public void onOtherResult(String data, int status) {

            }

            @Override
            public void onError(String msg) {
            }
        });
    }

    @Override
    public int setItemLayoutId() {
        return 0;
    }

    @Override
    public void setItemView(DevRecyclerViewHolder holder, Object item) {

    }
}
