package cc.dyjh.www.DiaoYuJiangHu.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import cc.dyjh.www.DiaoYuJiangHu.R;
import cc.dyjh.www.DiaoYuJiangHu.fragment.IndexFragment;
import cc.dyjh.www.DiaoYuJiangHu.fragment.MyFragment;

public class MainActivity extends BaseTabActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){

        }
    }
*/


    private String [] mTabs = {"首页","我的"};
    @Override
    public String[] setTabTitles() {
        return mTabs;
    }

    @Override
    public int[] setTabIcons() {
        return new int[]{R.drawable.tab1, R.drawable.tab2};
    }

    @Override
    public <T extends Fragment> Class<T>[] setFragment() {
        return new Class[]{IndexFragment.class,
                MyFragment.class};
    }


    @Override
    public Bundle setFragmentArgment(int position) {
        return null;
    }


}