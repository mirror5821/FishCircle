package cc.dyjh.www.DiaoYuJiangHu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

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
public class ImageAdd2Activity extends BaseActivity implements AdapterView.OnItemClickListener {
    private NoScrollGridView mGridView;

    private ImageAddsAdapter mAdapter;
    private List<String> mListImg = new ArrayList<String>();
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
        if(mTypeId == 2){
            if(!TextUtils.isEmpty(getIntent().getStringExtra("ALBUM"))){
                mAlbum = getIntent().getStringExtra("ALBUM").split(" ");
                for (String str:mAlbum){
                    if(!TextUtils.isEmpty(str)||!str.equals(" ")){
                        mList.add(BASE_IMG_URL+str);
                    }
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
        Intent intent = new Intent(ImageAdd2Activity.this, MultiImageSelectorActivity.class);
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
    private void upload1(){

        //渔场id,imagedata:图片流,imagetype:图片类型, ablum:保留的原来图片
        if(mSelectPath == null){
            showToast("请选择上传的照片");
            return;
        }
        showProgressDialog("正在提交数据");
        Map<String,String> values = new HashMap<>();
        values.put("fhid",mId+"");
//        String [] strs = new String[mSelectPath.size()];
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i<mSelectPath.size();i++){
            sb.append(mImageTools.filePathToString(mSelectPath.get(i)));
            if(i!=mSelectPath.size()-1){
                sb.append(",");
            }
        }
        values.put("imagedata[]",sb.toString());//
//        System.out.println("-----------------" + sb.toString());

        /**
         * http://m.dyjh.cc/appi.php?s=Fisherymsg/griUploadFisherymsgImgs?fhid=64&imagetype=jpeg&imagedata=(
         111,
         111
         )
         */
        values.put("imagetype", "jpeg");

//        参数 fhid:渔汛id,imagedata:图片流,imagetype:图片类型

        mHttpClient.postData1(YUXUN_IMG_UPDATE, values, new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {

                showToast("操作成功");
                cancelProgressDialog();
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


    private void upload2(){

        if(TextUtils.isEmpty(getIntent().getStringExtra("ALBUM"))&& mSelectPath == null){
            showToast("请选择上传的照片");
            return;
        }

        showProgressDialog("正在提交数据");
        //渔场id,imagedata:图片流,imagetype:图片类型, ablum:保留的原来图片
        Map<String,String> values = new HashMap<>();
        values.put("fisheryid", mId + "");

        if(mSelectPath == null){
            if(TextUtils.isEmpty(getIntent().getStringExtra("ALBUM"))){
                showToast("请选择照片");
                return;
            }else{
                values.put("ablum[]",getIntent().getStringExtra("ALBUM"));
            }
        }else{
            StringBuilder sb = new StringBuilder();
            for(int i = 0;i<mSelectPath.size();i++){
                sb.append(mImageTools.filePathToString(mSelectPath.get(i)));
                if(i!=mSelectPath.size()-1){
                    sb.append(",");
                }
            }

            values.put("imagedata[]",sb.toString());//

            values.put("imagetype", "jpeg");
            values.put("ablum[]","");//使用空格拼接
        }


        mHttpClient.postData1(YUCHANG_IMG_UPLOAD, values, new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {

                showToast("操作成功");
                cancelProgressDialog();

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


}