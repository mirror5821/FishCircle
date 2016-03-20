package cc.dyjh.www.DiaoYuJiangHu.activity;

import android.os.Bundle;
import android.view.Window;

import cc.dyjh.www.DiaoYuJiangHu.bean.Constants;
import dev.mirror.library.android.activity.DevBaseActivity;

/**
 * Created by dongqian on 16/3/20.
 */
public class BaseActivity extends DevBaseActivity implements Constants{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
    }
}
