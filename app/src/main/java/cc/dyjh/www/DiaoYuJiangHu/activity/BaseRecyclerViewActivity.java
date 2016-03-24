package cc.dyjh.www.DiaoYuJiangHu.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import cc.dyjh.www.DiaoYuJiangHu.app.AppContext;
import dev.mirror.library.android.Holder.DevRecyclerViewHolder;
import dev.mirror.library.android.adapter.DevRecycerViewAdapter;
import dev.mirror.library.android.util.DpUtil;
import dev.mirror.library.android.util.VerticalSpaceItemDecoration;
import dev.mirror.library.android.xrecyclerview.ProgressStyle;
import dev.mirror.library.android.xrecyclerview.XRecyclerView;

/**
 * Created by 王沛栋 on 2016/3/4.
 */
public abstract class BaseRecyclerViewActivity<T extends Parcelable> extends BaseActivity{
    public int mDefaultPage = 1;
    public int pageNo;
    public List<T> mList;
    public MyAdapter mAdapter;

    public View mViewLoading,mViewEmpty;
    public XRecyclerView mRecyclerView;
    /**
     * 设置布局文件  若不设置，则实用默认的layout界面
     * @return
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(setLayoutById()==0){
            setContentView(dev.mirror.library.android.R.layout.base_recyclerview);
        }else{
            setContentView(setLayoutById());
        }

        initOtherView();
        mViewLoading = findViewById(dev.mirror.library.android.R.id.loading);
        mViewEmpty = findViewById(dev.mirror.library.android.R.id.empty);

        mRecyclerView = (XRecyclerView)findViewById(dev.mirror.library.android.R.id.recyclerview);
        if(mList == null|| mList.isEmpty()){
            pageNo = mDefaultPage;
            loadData();
        }else{
            setAdapter();
        }
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

    /**
     * 此处可以初始化一些其他视图
     * 比如在复杂的界面布局中可以初始化非Recyclerview的视图
     */
    public void initOtherView(){

    }

//    RecyclerView.LayoutManager lm = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);

    public RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
    private void newAdapter(){
        if(mAdapter == null) {
            mAdapter = new MyAdapter(this,mList,setItemLayoutId());

//            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mRecyclerView.setLayoutManager(layoutManager);

            mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
            mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
            mRecyclerView.setArrowImageView(dev.mirror.library.android.R.mipmap.ic_downgrey);
            mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(DpUtil.dip2px(AppContext.getInstance(), 1)));

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
                    pageNo+=1;
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
