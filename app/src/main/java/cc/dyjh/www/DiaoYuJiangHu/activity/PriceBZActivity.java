package cc.dyjh.www.DiaoYuJiangHu.activity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;

import cc.dyjh.www.DiaoYuJiangHu.R;
import cc.dyjh.www.DiaoYuJiangHu.bean.PriceListener;
import cc.dyjh.www.DiaoYuJiangHu.fragment.Price1Fragment;
import cc.dyjh.www.DiaoYuJiangHu.fragment.Price2Fragment;

/**
 * Created by 王沛栋 on 2016/3/24.
 */
public class PriceBZActivity extends BaseViewPagerActivity {

    @Override
    public void initView() {
        super.initView();
        setBack();
        setRightTitle("保存");
        setTitleText("收费标准");
        String str [] = getIntent().getStringExtra(INTENT_ID).split(" ");
        if(str[0].equals("1")){
            mRb1.setChecked(true);
        }else{
            mRb2.setChecked(true);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.right_text:
                if(mViewPager.getCurrentItem() == 0){
                    mFragment1.getPrice(new PriceListener() {
                        @Override
                        public void getPirce(String str) {
                            Uri datas = Uri.parse(str);
                            Intent intent = new Intent(null,datas);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    });
                }else{
                    mFragment2.getPrice(new PriceListener() {
                        @Override
                        public void getPirce(String str) {
                            Uri datas = Uri.parse(str);
                            Intent intent = new Intent(null,datas);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    });
                }

                break;
        }
    }

    @Override
    public String[] setTypes() {
        return new String[]{"包时收费","其他收费方式"};
    }

    @Override
    public void onPageChangeListenr(int position) {
        selectTab(position);
    }

    @Override
    public void onCheckChangeListener(RadioGroup group, int checkedId) {
        if(checkedId == R.id.rb1){
            mViewPager.setCurrentItem(0);
        }else{
            mViewPager.setCurrentItem(1);
        }
    }

    private String mIntentId;
    private Price1Fragment mFragment1;
    private Price2Fragment mFragment2;
    @Override
    public Fragment setViewPagerFragment(int position) {
        mIntentId = getIntent().getStringExtra(INTENT_ID);
        if(TextUtils.isEmpty(mIntentId)){

        }
        if(position == 0){
            if(mFragment1 == null){
                mFragment1 = new Price1Fragment();
                Bundle b = new Bundle();
                b.putString(INTENT_ID, mIntentId);

                mFragment1.setArguments(b);
            }
            return mFragment1;
        }else{
            if(mFragment2 == null){
                mFragment2 = new Price2Fragment();
                Bundle b = new Bundle();
                b.putString(INTENT_ID, mIntentId);
                mFragment2.setArguments(b);
            }
            return mFragment2;
        }


    }

    private void selectTab(int tab){
        if(tab == 0){
            mRb1.setChecked(true);
        }else{
            mRb2.setChecked(true);
        }
    }
}

