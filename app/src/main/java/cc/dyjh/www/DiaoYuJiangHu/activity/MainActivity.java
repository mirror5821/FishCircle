package cc.dyjh.www.DiaoYuJiangHu.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import cc.dyjh.www.DiaoYuJiangHu.R;
import cc.dyjh.www.DiaoYuJiangHu.fragment.IndexFragment;

public class MainActivity extends BaseTabActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){

        }
    }



    private String [] mTabs = {"首页","验证","我的"};
    @Override
    public String[] setTabTitles() {
        return mTabs;
    }

    @Override
    public int[] setTabIcons() {
        return new int[]{R.drawable.tab1, R.drawable.tab2, R.drawable.tab3};
    }

    @Override
    public <T extends Fragment> Class<T>[] setFragment() {
        return new Class[]{IndexFragment.class,IndexFragment.class,
                IndexFragment.class};
    }


    @Override
    public Bundle setFragmentArgment(int position) {
        return null;
    }


}