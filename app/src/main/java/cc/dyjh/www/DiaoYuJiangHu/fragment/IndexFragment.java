package cc.dyjh.www.DiaoYuJiangHu.fragment;

import android.os.Bundle;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import cc.dyjh.www.DiaoYuJiangHu.R;
import cc.dyjh.www.DiaoYuJiangHu.app.AppContext;
import cc.dyjh.www.DiaoYuJiangHu.util.AppAjaxCallback;
import cc.dyjh.www.DiaoYuJiangHu.util.AppHttpClient;

/**
 * Created by dongqian on 16/3/20.
 */
public class IndexFragment extends BaseFragment {
    @Override
    public int setLayoutId() {
        return R.layout.fragment_index;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//hehe
        final Map<String,String> values = new HashMap<>();
        values.put("id", AppContext.user.getId());

        AppHttpClient mHttpClient = new AppHttpClient();
        mHttpClient.postData1(GET_YUXUN, values, new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {
                showToast(msg);
                values.clear();
            }

            @Override
            public void onError(String msg) {
                showToast("err-------" + msg);
            }
        });
    }

    /**
     {"status":0,"result":{"yuxun":{"id":"9","f_id":"1","hot_fysj":null,"hot_fyzl":null,"hot_fyjs":null,"hot_dysj":null,"hot_xgcd":null,"hot_sfbz":null,"hot_jyez":null,"hot_qtsm":null,"hot_img":null,"hot_time":null,"hot_num":"0","status":"0"},"fhid":"9","yu":[{"id":"10","type":"1","text":"\u9ed1\u9c7c","ordernum":"1"},{"id":"9","type":"1","text":"\u9bb0\u9c7c","ordernum":"2"},{"id":"8","type":"1","text":"\u7f57\u975e","ordernum":"3"},{"id":"7","type":"1","text":"\u9cb3\u9c7c","ordernum":"4"},{"id":"6","type":"1","text":"\u4e2d\u534e\u9c9f","ordernum":"5"},{"id":"5","type":"1","text":"\u9ca2\u9c7c","ordernum":"6"},{"id":"4","type":"1","text":"\u9752\u9c7c","ordernum":"7"},{"id":"3","type":"1","text":"\u8349\u9c7c","ordernum":"8"},{"id":"2","type":"1","text":"\u9cab\u9c7c","ordernum":"9"},{"id":"1","type":"1","text":"\u9ca4\u9c7c","ordernum":"10"},{"id":"25","type":"1","text":"\u5176\u4ed6","ordernum":"11"}],"er":[{"id":"17","type":"2","text":"\u4e0d\u9650","ordernum":"1"},{"id":"11","type":"2","text":"\u8001\u9b3c","ordernum":"2"},{"id":"12","type":"2","text":"\u4e38\u4e5d","ordernum":"3"},{"id":"13","type":"2","text":"\u9493\u9c7c\u738b","ordernum":"4"},{"id":"14","type":"2","text":"\u9f99\u738b\u6068","ordernum":"5"},{"id":"15","type":"2","text":"\u6b66\u6c49\u5929\u5143","ordernum":"6"},{"id":"16","type":"2","text":"\u5316\u6c0f\u9975\u6599","ordernum":"7"}],"xiangan":[{"id":"18","type":"3","text":"\u4e0d\u9650","ordernum":"1"},{"id":"19","type":"3","text":"3.6\u7c73","ordernum":"2"},{"id":"20","type":"3","text":"4.5\u7c73","ordernum":"3"},{"id":"21","type":"3","text":"5.4\u7c73","ordernum":"4"},{"id":"22","type":"3","text":"5.7\u7c73","ordernum":"5"},{"id":"23","type":"3","text":"6.3\u7c73","ordernum":"6"},{"id":"24","type":"3","text":"7.2\u7c73","ordernum":"7"}]},"msg":"Request data success"}

     */
}
