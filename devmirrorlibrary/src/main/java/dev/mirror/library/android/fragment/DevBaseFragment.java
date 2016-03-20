package dev.mirror.library.android.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by dongqian on 16/1/3.
 */
public class DevBaseFragment  extends Fragment implements View.OnClickListener{

    private View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView==null){
            rootView=inflater.inflate(setLayoutId(), container, false);
        }
        //缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    public int setLayoutId(){
        return 0;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void showToast(int resId){
        if(getActivity()!=null){
            Toast.makeText(getActivity(), resId, Toast.LENGTH_LONG).show();;
        }
    }

    public void showToast(Character text){
        if(getActivity()!=null){
            Toast.makeText(getActivity(),text,Toast.LENGTH_LONG).show();;
        }
    }

    /**
     * showToast
     * @param str
     */
    public void showToast(String str){
        Toast.makeText(getActivity(),str,Toast.LENGTH_LONG).show();
    }



    @Override
    public void onClick(View v) {

    }

    private ProgressDialog mProgressDialog;
    /**
     * 显示加载dialog
     * @param msg
     */
    public void showProgressDialog(String msg){
        if(mProgressDialog == null){
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setCancelable(true);
        }
        //如果显示的字符为空，则设置默认值
        if(TextUtils.isEmpty(msg)){
            msg = "正在加载数据...";
        }
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }

    /**
     * 显示加载dialog
     * @param msg
     * @param cancelable
     */
    public void showProgressDialog(String msg,boolean cancelable){
        if(mProgressDialog == null){
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setCancelable(cancelable);
        }
        //如果显示的字符为空，则设置默认值
        if(TextUtils.isEmpty(msg)){
            msg = "正在加载数据...";
        }
        mProgressDialog.setMessage(msg);
        mProgressDialog.show();
    }

    /**
     * 取消加载dialog
     */
    public void cancelProgressDialog(){
        if(mProgressDialog != null){
            mProgressDialog.cancel();
        }
    }

    /**
     * 初始化TextView
     * @param resId
     * @return
     *
     */
    public TextView initTextView(int resId){
        TextView tv = (TextView)getActivity().findViewById(resId);
        tv.setOnClickListener(this);
        return tv;
    }

    /**
     * 初始化Edittext
     * @param resId
     * @return
     */
    public EditText initEditText(int resId){
        EditText tv = (EditText)getActivity().findViewById(resId);
        tv.setOnClickListener(this);
        return tv;
    }

    /**
     * 初始化ImageView
     * @param resId
     * @return
     */
    public ImageView initImageView(int resId){
        ImageView img = (ImageView)getActivity().findViewById(resId);
        //		 img.setOnClickListener((OnClickListener) getActivity());
        img.setOnClickListener(this);
        return img;
    }

    /**
     * 初始化Button
     * @param resId
     * @return
     */
    public Button initButton(int resId){
        Button btn = (Button)getActivity().findViewById(resId);
        btn.setOnClickListener(this);
        return btn;
    }

    /**
     * 初始化LinearLayout
     * @param resId
     * @return
     */
    public LinearLayout initLinearLayout(int resId){
        LinearLayout btn = (LinearLayout)getActivity().findViewById(resId);
        btn.setOnClickListener(this);
        return btn;
    }

    private AlertDialog.Builder mBuilder;
    /**
     * 弹出提示框
     * @param title
     * @param msg
     * @param btnStr
     * @param l
     */
    public void showNormalDialog(String title,String msg,String btnStr,DialogInterface.OnClickListener l){
        if(mBuilder == null){
            mBuilder = new AlertDialog.Builder(getActivity());
        }
        mBuilder.setTitle(title);
        mBuilder.setMessage(msg);
        mBuilder.setPositiveButton(btnStr, l);
        mBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                arg0.dismiss();
            }
        });
        Dialog d = mBuilder.create();
        d.show();
    }

}
