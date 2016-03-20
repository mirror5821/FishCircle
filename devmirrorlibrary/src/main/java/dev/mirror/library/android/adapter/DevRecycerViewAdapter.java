package dev.mirror.library.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import dev.mirror.library.android.Holder.DevRecyclerViewHolder;

/**
 * Created by dongqian on 16/1/3.
 */
public abstract class DevRecycerViewAdapter<T> extends RecyclerView.Adapter<DevRecyclerViewHolder> {
    private ArrayList<View> mHeaderViews = new ArrayList<>();//头视图
    private ArrayList<View> mFooterViews = new ArrayList<>();//尾视图

    private ArrayList<Integer> mHeaderViewTypes = new ArrayList<>();
    private ArrayList<Integer> mFooterViewTypes = new ArrayList<>();

    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<T> mLists;
    protected final int mItemLayoutId;

    private int VIEW_POSITION = 100000;

    /**
     * 构造器
     * @param context
     * @param list
     * @param layoutId
     */
    public DevRecycerViewAdapter(Context context,List<T> list,int layoutId){
        this.mContext = context;
        this.mLists = list;
        this.mItemLayoutId = layoutId;
        this.mInflater = LayoutInflater.from(context);
    }

    /**
     * 可以添加多个头部视图
     * @param view
     */
    public void addHeaderView(View view){
        mHeaderViews.add(view);
    }

    /**
     * 可以添加多个尾部视图
     * @param view
     */
    public void addFooterView(View view){
        mFooterViews.add(view);
    }

    @Override
    public int getItemViewType(int position) {
        if(mHeaderViews.size()>0&& position<mHeaderViews.size()){
            //给头部视图分配id
            mHeaderViewTypes.add(position*VIEW_POSITION);
            return position*VIEW_POSITION;
        }

        if(mFooterViews.size()>0 && position>getAdvanceCount()-1+mHeaderViews.size()){
            //记录viewtype标记
            mFooterViewTypes.add(position*VIEW_POSITION);
            return position*VIEW_POSITION;
        }
        if(mHeaderViews.size()>0){
            return getAdvanceViewType(position - mHeaderViews.size());
        }
        return getAdvanceViewType(position);
    }

    /**
     * 创建ViewHolder
     * @param parent
     * @param viewType
     * @return
     */
    protected  DevRecyclerViewHolder onCreateAdvanceViewHolder(ViewGroup parent,int viewType){
        View v = mInflater.inflate(mItemLayoutId,null);
        return new DevRecyclerViewHolder(v,this);
    }

    @Override
    public DevRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mHeaderViewTypes.contains(viewType)){
            return new HeaderHolder(mHeaderViews.get(viewType/VIEW_POSITION));
        }
        if(mFooterViewTypes.contains(viewType)){
            int indext = viewType/VIEW_POSITION-getAdvanceCount()-mHeaderViews.size();
            return new FooterHolder(mFooterViews.get(indext));
        }
        return onCreateAdvanceViewHolder(parent,viewType);
    }

    @Override
    public void onBindViewHolder(DevRecyclerViewHolder holder, int position) {
        if(mFooterViews.size()>0&& (position>getAdvanceCount()-1+mHeaderViews.size())){
            //暂时未做footerview的实现
            return;
        }
        if(mHeaderViews.size()>0){
            if(position<mHeaderViews.size()){
                return;
            }

            onBindAdvanceViewHolder(holder,position-mHeaderViews.size());
        }

        onBindAdvanceViewHolder(holder,position);
    }

    @Override
    public int getItemCount() {
        if(mHeaderViews.size()>0&& mFooterViews.size()>0){
            return getAdvanceCount()+mHeaderViews.size()+mFooterViews.size();
        }
        if(mHeaderViews.size()>0){
            return getAdvanceCount()+mHeaderViews.size();
        }
        if(mFooterViews.size()>0){
            return getAdvanceCount()+mFooterViews.size();
        }
        return getAdvanceCount();
    }

    /**
     * 页面布局类型个数，默认是1
     * @param position
     * @return
     */
    public int getAdvanceViewType(int position){
        if(position < 0)
            return 1;
        else
            return position;
    }
    private int getAdvanceCount(){
        if(mLists !=null){
            return mLists.size();
        }else {
            return 0;
        }
    }

    private void onBindAdvanceViewHolder(DevRecyclerViewHolder holder,int i){
        convert(holder,mLists.get(i));
    }

    /**
     * 头部
     */
    class HeaderHolder extends DevRecyclerViewHolder{
        public HeaderHolder(View itemView){
            super(itemView);
        }
    }

    /**
     * 尾部
     */
    class FooterHolder extends DevRecyclerViewHolder{
        public FooterHolder(View itemView){
            super(itemView);
        }
    }

    /**
     * 设置每个页面显示的内容
     * @param holder
     * @param item
     */
    public abstract void convert(DevRecyclerViewHolder holder,T item);

    public void setLists(List<T> list){
        this.mLists = list;
        notifyDataSetChanged();
    }
}
