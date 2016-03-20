package cc.dyjh.www.DiaoYuJiangHu.util;


import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.Map;

import cc.dyjh.www.DiaoYuJiangHu.bean.Constants;

/**
 * Created by mirror on 16/1/3.
 */
public class AppHttpClient{
    public AppHttpClient(){

    }

    public static final String SERVIER_HEADER = Constants.BASE_URL;
    public void postData1(String fName,Map<String,String> values,AppAjaxCallback.onResultListener listener){
        StringBuilder sb = new StringBuilder();
        sb.append(SERVIER_HEADER);
        sb.append(fName);

        AppAjaxParam params = new AppAjaxParam(sb.toString());


        for(Map.Entry<String, String> entry:values.entrySet()){
            params.addParameter(entry.getKey(), entry.getValue());
        }
        x.http().post(params,new AppAjaxCallback<>(listener));

    }


    public <T> void postData(String fName,RequestParams params,AppAjaxCallback.onRecevierDataListener<T> listener){
        StringBuilder sb = new StringBuilder();
        sb.append(SERVIER_HEADER);
        sb.append(fName);

        x.http().post(params, new AppAjaxCallback<>(listener));
    }
}
