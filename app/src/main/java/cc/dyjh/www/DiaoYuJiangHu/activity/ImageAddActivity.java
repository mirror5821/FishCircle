package cc.dyjh.www.DiaoYuJiangHu.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.dyjh.www.DiaoYuJiangHu.R;
import cc.dyjh.www.DiaoYuJiangHu.adapter.ImageAddsAdapter;
import cc.dyjh.www.DiaoYuJiangHu.util.AppAjaxCallback;
import dev.mirror.library.android.activity.MultiImageSelectorActivity;
import dev.mirror.library.android.util.ImageTools;
import dev.mirror.library.android.view.NoScrollGridView;

/**
 * Created by 王沛栋 on 2016/3/28.
 */
public class ImageAddActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private NoScrollGridView mGridView;

    private ImageAddsAdapter mAdapter;
    private List<String> mList;

    private ImageTools mImageTools;
    private int mId;
    private int mTypeId;

    private String [] mAlbum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_add);
        setTitleText("选择照片");
        setRightTitle("上传图片");
        setBack();


        mId = getIntent().getIntExtra(INTENT_ID,0);
        if(mId == 0){
            finish();
        }
        mTypeId = getIntent().getIntExtra("TYPE",0);


        mImageTools = new ImageTools(this);

        mGridView = (NoScrollGridView) findViewById(R.id.gridview);

        mList = new ArrayList<>();
        mList.clear();
        if(mTypeId == 2){
            if(null != getIntent().getStringArrayListExtra("UPDATE_IMG")){
                for (String str:getIntent().getStringArrayListExtra("UPDATE_IMG")){

                    if(!TextUtils.isEmpty(str)){
                        if(str.startsWith("http://")){
                            mList.add(str.replace(BASE_IMG_URL,""));
                            System.out.println("-----------------------1--"+str);
                        }else{
                            mList.add(str);
                            System.out.println("-----------------------2--"+str);
                        }
                    }

                }
            }
            if(!TextUtils.isEmpty(getIntent().getStringExtra("ALBUM"))){
                mAlbum = getIntent().getStringExtra("ALBUM").split(" ");
                for (String str:mAlbum){
                    str = str+"";
                    if(!TextUtils.isEmpty(str)||!str.equals(" ")||str.equals("")){

                        String s = (BASE_IMG_URL+str).trim();
                        if(!s.equals(BASE_IMG_URL)){
                            System.out.println("-----------------------3--"+str);
                            mList.add(BASE_IMG_URL+str);
                        }

                    }
                }
            }
        }else{
            if(getIntent().getStringArrayListExtra("IMGS")!=null){
                mSelectPath = getIntent().getStringArrayListExtra("IMGS");
                for (String url:mSelectPath){
//                    System.out.println("------------------"+url);
                    mList.add(url);
                }

            }

        }

        mList.add(null);


        mAdapter = new ImageAddsAdapter(getApplicationContext(), mList);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.right_text:
                switch (mTypeId){
                    //鱼汛发布上传照片
                    case 1:
                        upload1();
                        break;
                    case 2:
                        upload2();
                        break;
                }
                break;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //表示点击上传照片的按钮
        if (position == mList.size() - 1) {
            openImage();
//            mImageTools.showGetImageDialog("选择照片的方式!");
        } else {//这里应该做其他动作
            mList.remove(position);
            mAdapter.notifyDataSetChanged();
        }
    }


    private ArrayList<String> mSelectPath;
    private static final int REQUEST_IMAGE = 2;

    private void openImage() {
        int selectedMode = MultiImageSelectorActivity.MODE_MULTI;
        ;
//        selectedMode = MultiImageSelectorActivity.MODE_SINGLE;


        int maxNum = 6;
        Intent intent = new Intent(ImageAddActivity.this, MultiImageSelectorActivity.class);
        // 是否显示拍摄图片
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        // 最大可选择图片数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, maxNum);
        // 选择模式
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, selectedMode);
        // 默认选择
//        if (mSelectPath != null && mSelectPath.size() > 0) {
//            intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
//        }
        if (mList != null && mList.size() > 1) {
            mList.remove(mList.size() - 1);
            intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, (ArrayList) mList);
        }
        startActivityForResult(intent, REQUEST_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                mList.clear();
                mAdapter.notifyDataSetChanged();

                mList.addAll(mSelectPath);
                mList.add(null);

                mAdapter.notifyDataSetChanged();
            }
        }
    }

    Map<String,String> values1 = new HashMap<>();
    private void upload1(){

        //渔场id,imagedata:图片流,imagetype:图片类型, ablum:保留的原来图片
        if(mSelectPath == null){
            showToast("请选择上传的照片");
            return;
        }
        showProgressDialog("正在上传的照片");

        new Thread() {
            public void run() {
                values1.put("fhid",mId+"");
//        String [] strs = new String[mSelectPath.size()];
                StringBuilder sb = new StringBuilder();
                for(int i = 0;i<mSelectPath.size();i++){
                    sb.append(mImageTools.filePathToString2(mSelectPath.get(i)));
                    if(i!=mSelectPath.size()-1){
                        sb.append(",");
                    }
                }

                values1.put("imagedata[]",sb.toString());//

                values1.put("imagetype", "jpeg");

                Message message = Message.obtain();
                message.what = 2;
                mHandler.sendMessage(message);

            }
        }.start();



    }

    private void upload4(){
//        参数 fhid:渔汛id,imagedata:图片流,imagetype:图片类型

        mHttpClient.postData1(YUXUN_IMG_UPDATE, values1, new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {

                showToast("操作成功");
                cancelProgressDialog();

                Intent data2 = new Intent();
                data2.putStringArrayListExtra("IMAGE_LIST", mSelectPath);
                setResult(RESULT_OK, data2);
                finish();
            }

            @Override
            public void onOtherResult(String data, int status) {
                switch (status){
                    case 101:

                        finish();
                        break;
                    case 103:

                        break;
                    default:

                        break;

                }
                cancelProgressDialog();
                showToast("操作失败");
            }

            @Override
            public void onError(String msg) {
                cancelProgressDialog();
                showToast("操作失败");
            }
        });
    }


    private int mImgCount = 0;
    Map<String,String> values = new HashMap<>();
    private void upload2(){

        if(mList.size() == 0){
            showToast("请选择上传的照片");
            return;
        }

        showProgressDialog("正在上传照片");

        new Thread(){
            public void run(){

                StringBuilder sbImgLoc = new StringBuilder();
                StringBuilder sbImgNet = new StringBuilder();

                for (String str:mList){
                    if(!TextUtils.isEmpty(str)){
                        if(str.startsWith("http://")){
                            sbImgNet.append(str.replace(BASE_IMG_URL,""));
                            sbImgNet.append(" ");
                            mImgCount = mImgCount+1;
                        }else{
                            sbImgLoc.append(mImageTools.filePathToString2(str));
                            sbImgLoc.append(",");
//                    sbImgLoc.append(" ");
                            mImgCount = mImgCount+1;
                        }
                    }
                }

                //渔场id,imagedata:图片流,imagetype:图片类型, ablum:保留的原来图片
                values.clear();
                values.put("fisheryid", mId + "");

                String mImgLoc = sbImgLoc.toString();
                if(TextUtils.isEmpty(mImgLoc)){
                    values.put("imagedata[]","");//
                }else{
                    values.put("imagedata[]",mImgLoc.substring(0,mImgLoc.length()-1));//
                }

                values.put("imagetype", "jpeg");
                String mImgNet = sbImgNet.toString();
                if(TextUtils.isEmpty(mImgNet)){
                    values.put("ablum[]","");//使用空格拼接
                } else {
                    values.put("ablum[]",mImgNet.substring(0,mImgNet.length()-1));//使用空格拼接
                }

                Message message = Message.obtain();
                message.what = 1;
                mHandler.sendMessage(message);

            }
        }.start();

    }

    private void upload3(){
        mHttpClient.postData1(YUCHANG_IMG_UPLOAD, values, new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {

                showToast("操作成功");
                cancelProgressDialog();


                int i = 0;
                String [] strs = data.split(" ");
                for (String str:strs){
                    str = str+"";
                    if(!TextUtils.isEmpty(str)||!str.equals(" ")||str.equals("")){

                        String s = (BASE_IMG_URL+str).trim();
                        if(!s.equals(BASE_IMG_URL)){

                            i = i+1;
                        }

                    }
                }

                List<String> lists = new ArrayList<String>();
                for (String str:mList){
                    if(!TextUtils.isEmpty(str)){
                        if(!str.startsWith("http://")){
                            lists.add(str);
                        }
                    }

                }

                Intent data2 = new Intent();
                data2.putExtra("ALUM",data.replace("[","").replace("]","").replace(","," ").replace("\"","").replace("\\/","/"));
                data2.putExtra("IMAGE_COUNT",mImgCount);
//                data2.putExtra("IMAGE_COUNT",i);
                data2.putStringArrayListExtra("IMAGE_LOC", (ArrayList<String>) lists);
                setResult(RESULT_OK, data2);

                finish();

            }

            @Override
            public void onOtherResult(String data, int status) {
                switch (status){
                    case 101:

                        finish();
                        break;
                    case 103:

                        break;
                    default:

                        break;

                }
                cancelProgressDialog();
                showToast("操作失败");
            }

            @Override
            public void onError(String msg) {
                cancelProgressDialog();
                showToast("操作失败");
            }
        });
    }

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch(msg.what){
                case 1:
                    upload3();
                    break;
                case 2:
                    upload4();
                    break;
            }
        }
    };
}