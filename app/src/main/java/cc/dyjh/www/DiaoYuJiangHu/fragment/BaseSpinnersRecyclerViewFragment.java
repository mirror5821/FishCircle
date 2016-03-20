package cc.dyjh.www.DiaoYuJiangHu.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import cc.dyjh.www.DiaoYuJiangHu.R;
import cc.dyjh.www.DiaoYuJiangHu.adapter.AddrAdapter;
import cc.dyjh.www.DiaoYuJiangHu.app.AppContext;
import dev.mirror.library.android.Holder.DevRecyclerViewHolder;

/**
 * Created by 王沛栋 on 2016/3/7.
 */
public abstract class BaseSpinnersRecyclerViewFragment<T> extends BaseRecyclerViewFragment{
    public Spinner spinner1,spinner2;
    public List<T> Types1;
    public List<T> Types2;
    public AddrAdapter mAdapter1;
    public AddrAdapter mAdapter2;

    @Override
    public int setLayoutById() {
        mList = new ArrayList();
        return R.layout.activity_base_spinners;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinner1 = (Spinner)view.findViewById(R.id.spinner1);
        spinner2 = (Spinner)view.findViewById(R.id.spinner2);

        bindSpinner();
    }

//    @Override
//    public void initOtherView() {
//        super.initOtherView();
//        spinner1 = (Spinner)findViewById(R.id.spinner1);
//        spinner2 = (Spinner)findViewById(R.id.spinner2);
//
//        bindSpinner();
//    }

    private void bindSpinner(){


        Types1 = setSpinnerData1();
        Types2 = setSpinnerData2();

        mAdapter1 = new AddrAdapter(AppContext.getInstance(),Types1,1);
        mAdapter2 = new AddrAdapter(AppContext.getInstance(),Types2,2);

        spinner1.setAdapter(mAdapter1);
        spinner1.setVisibility(View.VISIBLE);//设置默认显示
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                Spinner1OnItemClick(arg0, arg1, arg2, arg3);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        spinner2.setAdapter(mAdapter2);
        spinner2.setVisibility(View.VISIBLE);//设置默认显示
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                Spinner2OnItemClick(arg0,arg1,arg2,arg3);

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

    }

//    public static class AddrAdapter<T extends AddrBase> extends BaseAdapter {
//        private int mType;
//        private Context mContext;
//        private List<T> mList;
//        public AddrAdapter(Context context,List<T> list,int type){
//            this.mType = type;
//            this.mContext = context;
//            this.mList = list;
//        }
//
//        @Override
//        public int getCount() {
//            return mList.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return position;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            if(convertView == null) {
//                if (mType == 2) {
//                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_spinner_d, null);
//                }else{
//                    convertView = LayoutInflater.from(mContext).inflate(R.layout.item_spinner_l, null);
//                }
//
//            }
//
//            TextView tv = (TextView)convertView.findViewById(android.R.id.text1);
//            tv.setText(mList.get(position).getAddrName());
//            return convertView;
//        }
//    }


    @Override
    public void loadData() {
        loadData2();
    }

    @Override
    public int setItemLayoutId() {
        return setItemLayoutId2();
    }

    @Override
    public void setItemView(DevRecyclerViewHolder holder, Object item) {
        setItemView2(holder, item);
    }


    public abstract List<T> setSpinnerData1();
    public abstract List<T> setSpinnerData2();

    public abstract void Spinner1OnItemClick(AdapterView<?> arg0, View arg1,int arg2, long arg3);
    public abstract void Spinner2OnItemClick(AdapterView<?> arg0, View arg1,int arg2, long arg3);

    public abstract void loadData2();
    public abstract int setItemLayoutId2();
    public abstract void setItemView2(DevRecyclerViewHolder holder, Object item);
}
