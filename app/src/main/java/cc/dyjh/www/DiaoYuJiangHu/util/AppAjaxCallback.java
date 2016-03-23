package cc.dyjh.www.DiaoYuJiangHu.util;

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;

import java.util.List;

import dev.mirror.library.android.util.JsonUtils;


/**
 * Created by mirror on 16/1/3.
 */
public class AppAjaxCallback<T> implements Callback.CommonCallback<String> {
    private onRecevierDataListener<T> mRecevierDataListener;
    private onResultListener mListener;

    public AppAjaxCallback(onRecevierDataListener<T> dataListener){
        mRecevierDataListener = dataListener;
    }

    public AppAjaxCallback(onResultListener dataListener){
        this.mListener = dataListener;
    }


    /**
     * 定义普通数据请求listener
     */
    public interface onResultListener{
        void onResult(String data, String msg);
        void onOtherResult(String data, int status);
        void onError(String msg);
    }

    /**
     * 定义list请求listener
     * @param <T>
     */
    public interface onRecevierDataListener<T>{
        void onReceiverData(List<T> data, String msg);
        void onReceiverError(String msg);
        Class<T> dataTypeClass();
    }


    private String listNoData = "未查询到任何数据";
    private String mErrorMsg = "网络错误,请检查您的网络!";
    @Override
    public void onSuccess(String result) {
        String t =  result.toString();
        System.out.println("--------------------"+t);
        if(!TextUtils.isEmpty(t)){
            try{
                JSONObject jb = new JSONObject(t);
                int status = jb.getInt("status");
                /**
                 * 0=>'成功',
                 101=>'账号状态异常',
                 102=>'密码不正确',
                 103=>'用户不存在',
                 300=>'数据不正确',
                 301=>'保存数据失败',
                 302=>'添加数据失败',
                 201=>'缺少必要参数',
                 401=>'验证超时',
                 402=>'验证码不正确',
                 403=>'不能发送短信',
                 501=>'保存图片出错',
                 601=>'手机号已注册',
                 701=>'缺少渔场资料'
                 */

                String msg = jb.getString("msg");
                //如果==0，表示查询成功
                if(status == 0){
                    String data = jb.getString("result").toString();
                    //表示请求是列表数据
                    if(mRecevierDataListener !=null){
                        List<T> list = JsonUtils.parseList(data, mRecevierDataListener.dataTypeClass());
                        //如果数据不为空
                        if(list!=null){
                            mRecevierDataListener.onReceiverData(list, msg);
                        }else{
                            try{
                                //判断分页list的返回
                                JSONObject jb2 = new JSONObject(data);
                                if(!jb2.getString("list").equals(null)){
                                    mRecevierDataListener.onReceiverData(JsonUtils.parseList(jb2.getString("list"),
                                            mRecevierDataListener.dataTypeClass()),msg);
                                }else{
                                    mRecevierDataListener.onReceiverError(listNoData);
                                }
                            }catch(Exception e){
                                mRecevierDataListener.onReceiverError(listNoData);
                            }
//							mRecevieDataListener.onReceiverError("----"+msg);
                        }
                    }else{
                        mListener.onResult(data,msg);
                    }
                }else{
                    String data = jb.getString("result").toString();
                    //表示返回其他状态
                    mListener.onOtherResult(data,status);

                    /*if(mRecevierDataListener!=null){
                        mRecevierDataListener.onReceiverError(msg);
                    }else{
                        mListener.onError(msg);
                    }*/
                }
            }catch(JSONException e){
//				if(mRecevieDataListener!=null){
//					mRecevieDataListener.onReceiverError(e.getMessage());
//				}else{
//					mListener.onError(e.getMessage());
//				}
                if(mRecevierDataListener!=null){
                    mRecevierDataListener.onReceiverError(e.getLocalizedMessage());
                }else{
                    mListener.onError(e.getLocalizedMessage());
                }
            }
        }else{
            if(mRecevierDataListener!=null){
                mRecevierDataListener.onReceiverError(mErrorMsg);
            }else{
                mListener.onError(mErrorMsg);
            }
        }
    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        if(mRecevierDataListener!=null){
            mRecevierDataListener.onReceiverError(ex.getMessage());
        }else{
            mListener.onError(ex.getMessage());
        }
    }

    @Override
    public void onCancelled(CancelledException cex) {

    }

    @Override
    public void onFinished() {

    }
}
