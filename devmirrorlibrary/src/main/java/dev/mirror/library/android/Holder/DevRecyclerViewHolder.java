package dev.mirror.library.android.Holder;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.TextView;

import dev.mirror.library.android.adapter.DevRecycerViewAdapter;

/**
 * Created by dongqian on 16/1/3.
 */
public class DevRecyclerViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;
    private View mConvertView;
    private DevRecycerViewAdapter mAdapter;

    public DevRecyclerViewHolder(View itemView) {
        super(itemView);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.MATCH_PARENT);
        itemView.setLayoutParams(lp);
        this.mViews = new SparseArray<>();
        this.mConvertView = itemView;
    }

    public <T>DevRecyclerViewHolder(View itemView,final DevRecycerViewAdapter<T> adapter) {
        super(itemView);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.MATCH_PARENT);
        itemView.setLayoutParams(lp);
        this.mViews = new SparseArray<>();
        this.mConvertView = itemView;
        this.mAdapter = adapter;
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 通过控件id获取相对的控件，如果没有则加入views
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId){
        View view = mViews.get(viewId);
        if(view == null){
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        try {
            return (T)view;
        }catch (ClassCastException e){
            return null;
        }
    }

    /**
     * 通过控件id获取相对的控件，如果没有则加入views
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T findViewById(int viewId){
        View view = mViews.get(viewId);
        if(view == null){
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        try{
            return (T)view;
        }catch (ClassCastException e){
            return null;
        }
    }

    public DevRecyclerViewHolder setText(int viewId,String text){
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }
}
