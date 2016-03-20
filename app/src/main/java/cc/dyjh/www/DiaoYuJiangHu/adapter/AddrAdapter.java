package cc.dyjh.www.DiaoYuJiangHu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cc.dyjh.www.DiaoYuJiangHu.R;
import cc.dyjh.www.DiaoYuJiangHu.bean.AddrBase;

/**
 * Created by 王沛栋 on 2016/3/8.
 */
public  class AddrAdapter<T extends AddrBase> extends BaseAdapter {
    private int mType;
    private Context mContext;
    private List<T> mList;
    public AddrAdapter(Context context, List<T> list, int type){
        this.mType = type;
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            switch (mType){
                case 1:
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_about, null);
                    break;
                default:
                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_about, null);
                    break;
            }
        }

        TextView tv = (TextView)convertView.findViewById(android.R.id.text1);
        tv.setText(mList.get(position).getAddrName());
        return convertView;
    }
}