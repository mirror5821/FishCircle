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
public class Price1Fragment extends BaseFragment {
    private EditText mEtP1,mEtP2,mEtP3,mEtP4;
    private EditText mEtH1,mEtH2,mEtH3,mEtH4;

    private String mIntentId;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIntentId = getArguments().getString(INTENT_ID);
    }

    @Override
    public int setLayoutId() {
        return R.layout.fragment_match_bs;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEtP1 = (EditText)view.findViewById(R.id.p1);
        mEtP2 = (EditText)view.findViewById(R.id.p2);
        mEtP3 = (EditText)view.findViewById(R.id.p3);
        mEtP4 = (EditText)view.findViewById(R.id.p4);

        mEtH1 = (EditText)view.findViewById(R.id.h1);
        mEtH2 = (EditText)view.findViewById(R.id.h2);
        mEtH3 = (EditText)view.findViewById(R.id.h3);
        mEtH4 = (EditText)view.findViewById(R.id.h4);

        if(!TextUtils.isEmpty(mIntentId)){
            String [] ids = mIntentId.split(" ");
            mEtP1.setText(ids[0]);
            mEtH1.setText(ids[1]);
            mEtP2.setText(ids[2]);
            mEtH2.setText(ids[3]);
            mEtP3.setText(ids[4]);
            mEtH3.setText(ids[5]);
            mEtP4.setText(ids[6]);
            mEtH4.setText(ids[7]);
        }
    }

    /**
     * 获取接口
     * @param listener
     */
    public void getPrice(PriceListener listener){
        String h1 = mEtH1.getText().toString();
        String h2 = mEtH2.getText().toString();
        String h3 = mEtH3.getText().toString();
        String h4 = mEtH4.getText().toString();
        String p1 = mEtP1.getText().toString();
        String p2 = mEtP2.getText().toString();
        String p3 = mEtP3.getText().toString();
        String p4 = mEtP4.getText().toString();


        if(TextUtils.isEmpty(h1)){
            showToast("输入信息不完整");
            return;
        }
        if(TextUtils.isEmpty(h2)){
            showToast("输入信息不完整");
            return;
        }
        if(TextUtils.isEmpty(h3)){
            showToast("输入信息不完整");
            return;
        }
        if(TextUtils.isEmpty(h4)){
            showToast("输入信息不完整");
            return;
        }
        if(TextUtils.isEmpty(p1)){
            showToast("输入信息不完整");
            return;
        }
        if(TextUtils.isEmpty(p2)){
            showToast("输入信息不完整");
            return;
        }
        if(TextUtils.isEmpty(p3)){
            showToast("请选择地区");
            return;
        }
        if(TextUtils.isEmpty(p4)){
            showToast("输入信息不完整");
            return;
        }
//        "1 100 1 200 2 300 3 400 4";
        StringBuilder sb = new StringBuilder();
        sb.append("1");
        sb.append(" "+p1);
        sb.append(" "+h1);
        sb.append(" "+p2);
        sb.append(" "+h2);
        sb.append(" "+p3);
        sb.append(" "+h3);
        sb.append(" "+p4);
        sb.append(" "+h4);

        String price = sb.toString();

        listener.getPirce(price);
    }
}
