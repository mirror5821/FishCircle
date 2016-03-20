package cc.dyjh.www.DiaoYuJiangHu.fragment;

import cc.dyjh.www.DiaoYuJiangHu.bean.Constants;
import cc.dyjh.www.DiaoYuJiangHu.util.AppHttpClient;
import dev.mirror.library.android.fragment.DevBaseFragment;

/**
 * Created by dongqian on 16/3/20.
 */
public class BaseFragment extends DevBaseFragment implements Constants{
    public AppHttpClient mHttpClient = new AppHttpClient();
}
