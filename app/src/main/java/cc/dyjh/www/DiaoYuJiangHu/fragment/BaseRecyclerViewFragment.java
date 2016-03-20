package cc.dyjh.www.DiaoYuJiangHu.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import java.util.List;

import cc.dyjh.www.DiaoYuJiangHu.R;
import cc.dyjh.www.DiaoYuJiangHu.app.AppContext;
import dev.mirror.library.android.Holder.DevRecyclerViewHolder;
import dev.mirror.library.android.adapter.DevRecycerViewAdapter;
import dev.mirror.library.android.util.DpUtil;
import dev.mirror.library.android.util.VerticalSpaceItemDecoration;
import dev.mirror.library.android.xrecyclerview.ProgressStyle;
import dev.mirror.library.android.xrecyclerview.XRecyclerView;

/**
 * Created by mirror on 16/1/3.
 */
public abstract class BaseRecyclerViewFragment<T extends Parcelable> extends BaseFragment {
    public int mDefaultPage = 1;
    public int pageNo;
    public List<T> mList;
    public MyAdapter mAdapter;
    /**
     * 设置布局文件  若不设置，则实用默认的layout界面
     * @return
     */
    @Override
    public int setLayoutId() {
        if(setLayoutById()==0){
            return R.layout.base_recyclerview;
        }else{
            return setLayoutById();
        }
    }
    public View mViewLoading,mViewEmpty;
    public XRecyclerView mRecyclerView;
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewLoading = view.findViewById(R.id.loading);
        mViewEmpty = view.findViewById(R.id.empty);

        mRecyclerView = (XRecyclerView)view.findViewById(R.id.recyclerview);
        initOtherView();
        if(mList == null|| mList.isEmpty()){
            pageNo = mDefaultPage;
            loadData();
        }else{
            setAdapter();
        }


    }

    public void initOtherView(){

    }

    public void setAdapter(){
        //如果数据为空
        if(mList == null){
            showEmptyView();
            return;
        }

        //如果adapter为空
        if(mAdapter == null){
            newAdapter();
            showList();
        }else{
            mAdapter.notifyDataSetChanged();
        }

    }


    public void addHeaderView(){

    }
    private void newAdapter(){
        if(mAdapter == null) {
            mAdapter = new MyAdapter(AppContext.getInstance(),mList,setItemLayoutId());
            LinearLayoutManager layoutManager = new LinearLayoutManager(AppContext.getInstance());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(layoutManager);

            //设置刷新加载样式
            mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
            mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
            mRecyclerView.setArrowImageView(R.mipmap.ic_downgrey);

            mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(DpUtil.dip2px(getActivity(), 8)));

            //加入头部试图  可重新此方法
            addHeaderView();
            mRecyclerView.setAdapter(mAdapter);

            mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
                @Override
                public void onRefresh() {
                    pageNo = mDefaultPage;
                    loadData();
                }

                @Override
                public void onLoadMore() {
                    pageNo += 1;
                    loadData();
                }
            });
        }

    }

    /**
     * 显示列表数据
     */
    public void showList(){
        mViewEmpty.setVisibility(View.GONE);
        mViewLoading.setVisibility(View.GONE);
    }

    /**
     * 显示空数据
     */
    public void showEmptyView(){
        mViewEmpty.setVisibility(View.VISIBLE);
        mViewLoading.setVisibility(View.GONE);
    }

    protected class MyAdapter<T> extends DevRecycerViewAdapter<T> {

        /**
         * 构造器
         *
         * @param context
         * @param list
         * @param layoutId
         */
        public MyAdapter(Context context, List<T> list, int layoutId) {
            super(context, list, layoutId);
        }

        @Override
        public void convert(DevRecyclerViewHolder holder, T item) {
            setItemView(holder,item);
        }
    }

    public abstract int setLayoutById();
    public abstract void loadData();
    public abstract int setItemLayoutId();
    public abstract void setItemView(DevRecyclerViewHolder holder, Object item);
}
