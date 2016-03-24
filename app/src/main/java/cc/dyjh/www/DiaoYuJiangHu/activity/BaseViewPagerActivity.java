package cc.dyjh.www.DiaoYuJiangHu.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import cc.dyjh.www.DiaoYuJiangHu.R;

/**
 * Created by 王沛栋 on 2016/3/24.
 */
public abstract class BaseViewPagerActivity extends BaseActivity {
    public RadioGroup mRG;
    public ViewPager mViewPager;
    public RadioButton[] Rbs;
    public RadioButton mRb1,mRb2;

    public String [] Types;
    private int mViewPagerCount = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_viewpager);

        mRG = (RadioGroup)findViewById(R.id.view_select);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mRb1 = (RadioButton)findViewById(R.id.rb1);
        mRb2 = (RadioButton)findViewById(R.id.rb2);
        mRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                onCheckChangeListener(group, checkedId);
            }
        });

        Types = setTypes();
        mRb1.setChecked(true);
        mRb1.setText(Types[0]);
        mRb2.setText(Types[1]);

        mViewPager.setOffscreenPageLimit(mViewPagerCount);
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                onPageChangeListenr(arg0);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });


        initView();

    }

    /**
     * 可以创建一些新的视图
     * 或者修改原来的视图
     */
    public void initView(){

    }
    class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int arg0) {
            return setViewPagerFragment(arg0);
        }

        @Override
        public int getCount() {
            return mViewPagerCount;
        }

    }


    public abstract String[] setTypes();
    public abstract void onPageChangeListenr(int position);
    public abstract void onCheckChangeListener(RadioGroup group, int checkedId);
    public abstract Fragment setViewPagerFragment(int position);
}
