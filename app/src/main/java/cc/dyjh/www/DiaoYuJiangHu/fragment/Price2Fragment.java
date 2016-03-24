package cc.dyjh.www.DiaoYuJiangHu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import cc.dyjh.www.DiaoYuJiangHu.R;
import cc.dyjh.www.DiaoYuJiangHu.bean.PriceListener;

/**
 * Created by 王沛栋 on 2016/3/24.
 */
public class Price2Fragment extends BaseFragment {
    private EditText mEt;
    @Override
    public int setLayoutId() {
        return R.layout.fragment_match_other;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEt = (EditText)view.findViewById(R.id.et);
    }

    /**
     * 获取接口
     * @param listener
     */
    public void getPrice(PriceListener listener){
        String str = mEt.getText().toString();
        if(TextUtils.isEmpty(str)){
            showToast("请输入收费标准");
            return;
        }
        listener.getPirce(str);
    }
}
