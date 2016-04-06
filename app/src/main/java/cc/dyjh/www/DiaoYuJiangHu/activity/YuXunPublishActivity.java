package cc.dyjh.www.DiaoYuJiangHu.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.dyjh.www.DiaoYuJiangHu.R;
import cc.dyjh.www.DiaoYuJiangHu.app.AppContext;
import cc.dyjh.www.DiaoYuJiangHu.bean.DialogInterface;
import cc.dyjh.www.DiaoYuJiangHu.bean.YuChang;
import cc.dyjh.www.DiaoYuJiangHu.bean.YuXun;
import cc.dyjh.www.DiaoYuJiangHu.iface.TimeInterface;
import cc.dyjh.www.DiaoYuJiangHu.util.AppAjaxCallback;
import cc.dyjh.www.DiaoYuJiangHu.util.AppHttpClient;
import cc.dyjh.www.DiaoYuJiangHu.util.DialogHelper;
import cc.dyjh.www.DiaoYuJiangHu.util.OptionUtil;
import cc.dyjh.www.DiaoYuJiangHu.util.UIUtil;
import dev.mirror.library.android.activity.MultiImageSelectorActivity;
import dev.mirror.library.android.util.ImageTools;
import dev.mirror.library.android.util.JsonUtils;

/**
 * Created by dongqian on 16/3/22.
 */
public class YuXunPublishActivity<T> extends BaseActivity {
    private TextView mTvFYTime;//放鱼时间
    private TextView mTvType;//放鱼种类
    private EditText mEtJin;//放鱼斤数
    private TextView mTvKDTime;//开钓时间
    private TextView mTvXG;//是否限杆
    private TextView mTvSF;//收费标准
    private TextView mTvJY;//禁用钓饵
    private EditText mEtOther;//其他说明
    private Button mBtn;

    private YuChang mYuChang;
    private List<YuChang.Yu> mFishType;//渔场特色
    private List<YuChang.Yu> mXianGan;//限杆
    private int mXGId;
    private List<YuChang.Yu> mER;//禁止钓饵
    private YuXun mYuXun;

    private String mSf;//收费标准
    private String mYZ;//放鱼种类
    private String mXG;//是否限杆
    private String mERStr;//禁用钓饵

    private ImageTools mImageTools;

    private static final int REQUSET_CODE_SF = 6001;
    private static final int REQUSET_CODE_YZ = 6002;
    private static final int REQUSET_CODE_ER = 6003;
    private static final int REQUSET_CODE_IMG = 6004;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yuxun_publish);
        setBack();
        setTitleText("发布渔讯");
        setRightTitle("发布");

        mImageTools = new ImageTools(this);

        mTvFYTime = (TextView)findViewById(R.id.fangyu_time);//放鱼时间
        mTvType = (TextView)findViewById(R.id.fangyu_type);//放鱼种类
        mTvKDTime = (TextView)findViewById(R.id.kaidiao_time);//开钓时间
        mTvXG = (TextView)findViewById(R.id.is_xiangan);//是否限杆
        mTvSF = (TextView)findViewById(R.id.price_biaozhun);//收费标准
        mTvJY = (TextView)findViewById(R.id.limit_diaoer);//禁止钓饵
        mEtOther = (EditText) findViewById(R.id.other_say);//其他说明
        mEtJin = (EditText)findViewById(R.id.fangyu_weight);
        mBtn = (Button)findViewById(R.id.btn);

        mTvFYTime.setOnClickListener(this);
        mTvType.setOnClickListener(this);
        mTvKDTime.setOnClickListener(this);
        mTvXG.setOnClickListener(this);
        mTvSF.setOnClickListener(this);
        mTvJY.setOnClickListener(this);
        mBtn.setOnClickListener(this);

        loadData();
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.price_biaozhun:
                startActivityForResult(new Intent(YuXunPublishActivity.this,PriceBZActivity.class).
                        putExtra(INTENT_ID,mSf),REQUSET_CODE_SF);
                break;
            case R.id.kaidiao_time:
                DialogHelper.initSelectTime(YuXunPublishActivity.this, new TimeInterface() {
                    @Override
                    public void getData(String data) {
                        mTvKDTime.setText(data);
                    }

                    @Override
                    public void getHour(String time) {

                    }
                });
                break;
            case R.id.fangyu_time:
                DialogHelper.initSelectTime(YuXunPublishActivity.this, new TimeInterface() {
                    @Override
                    public void getData(String data) {
                        mTvFYTime.setText(data);
                    }

                    @Override
                    public void getHour(String time) {

                    }
                });
                break;
            case R.id.fangyu_type:
                startActivityForResult(new Intent(YuXunPublishActivity.this,CheckBoxSelectActivity.class).
                        putParcelableArrayListExtra(INTENT_ID, (ArrayList<? extends Parcelable>) mFishType)
                                .putExtra("SELECT_TYPE", mYZ),
                        REQUSET_CODE_YZ);
                break;
            case R.id.is_xiangan:
                initSelectView(1, (List<T>) mXianGan);
                break;
            case R.id.limit_diaoer:
                startActivityForResult(new Intent(YuXunPublishActivity.this,CheckBoxSelectActivity.class).
                                putParcelableArrayListExtra(INTENT_ID, (ArrayList<? extends Parcelable>) mER)
                                .putExtra("SELECT_TYPE",mERStr),
                        REQUSET_CODE_ER);
                break;
            case R.id.right_text:
                sub();
                break;
            case R.id.btn:
                startActivityForResult(new Intent(YuXunPublishActivity.this,ImageAddActivity.class)
                        .putExtra(INTENT_ID,mYuChang.getFhid())
                        .putStringArrayListExtra("IMGS",mSelectPath)
                        .putExtra("TYPE",1),REQUSET_CODE_IMG);
//                openImage();
                break;
        }
    }

    /**
     *
     * @param type 1鱼种类  2渔场特色
     * @param mList
     */
    private void initSelectView(final int type, final List<T> mList){
        UIUtil uiHelper = new UIUtil();
        uiHelper.initSelectListView(YuXunPublishActivity.this, mList, new DialogInterface() {
            @Override
            public void getPosition(int position) {
                switch (type) {
                    case 1:
                        mXianGan.get(position);
                        mXG = mXianGan.get(position).getName();
                        mTvXG.setText(mXG);
                        mXG = mXianGan.get(position).getId()+"";
                        break;

                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            switch (requestCode) {
                case REQUSET_CODE_SF:
                    Uri sfData = data.getData();
                    mSf = sfData.toString();

                    mTvSF.setText(mSf);
                    try{
                        String [] sfbz = mSf.split(" ");
                        if(sfbz[0].equals("2")){
                            mTvSF.setText(sfbz[1]);
                            return;
                        }
                        StringBuilder sb = new StringBuilder();
                        sb.append("放鱼:日钓");
                        sb.append(sfbz[1]);
                        sb.append("元");
                        sb.append(sfbz[2]);
                        sb.append("小时");

                        sb.append(sfbz[3]);
                        sb.append("元");
                        sb.append(sfbz[4]);
                        sb.append("小时");

                        sb.append(sfbz[5]);
                        sb.append("元");
                        sb.append(sfbz[6]);
                        sb.append("小时");

                        sb.append(sfbz[7]);
                        sb.append("元");
                        sb.append(sfbz[8]);
                        sb.append("小时");

                        mTvSF.setText(sb.toString());
                    }catch (Exception e){

                    }



                    break;
                case REQUSET_CODE_YZ:
                    Uri yzData = data.getData();
                    mYZ = yzData.toString();
                    mTvType.setText(OptionUtil.getYu(mYuChang.getYu(), mYZ));
//                    mTvType.setText("具体内容");
                    break;
                case REQUSET_CODE_ER:
                    Uri erData = data.getData();
                    mERStr = erData.toString();
                    mTvJY.setText(OptionUtil.getYu(mYuChang.getEr(), mERStr));
                    break;
                case REQUEST_IMAGE:
                    mSelectPath = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                    mBtn.setText("已选择 "+mSelectPath.size()+" 张");
                    upload();
                    break;
                case REQUSET_CODE_IMG:
                    mSelectPath = data.getStringArrayListExtra("IMAGE_LIST");
                    mBtn.setText("已选择 "+mSelectPath.size()+" 张");
                    break;
            }
        }
    }



    private void loadData(){
        Map<String,String> values = new HashMap<>();
        values.put("id", AppContext.ID+"");

        AppHttpClient mHttpClient = new AppHttpClient();
        mHttpClient.postData1(YUNCAHNG_SELECT_INFO2, values, new AppAjaxCallback.onResultListener() {

            @Override
            public void onResult(String data, String msg) {
                mYuChang = JsonUtils.parse(data, YuChang.class);

                mFishType = mYuChang.getYu();
                mXianGan = mYuChang.getXiangan();
                mER = mYuChang.getEr();
                mYuXun = mYuChang.getYuxun();

                mTvFYTime.setText(mYuXun.getFysj());//放鱼时间
                mYZ = mYuXun.getFyzl();//放鱼种类
                if (!TextUtils.isEmpty(mYZ)) {
                    mTvType.setText(OptionUtil.getYu(mYuChang.getYu(), mYZ));
                }


                mTvKDTime.setText(mYuXun.getDysj());//开钓时间
                mXG = mYuXun.getXgcd();//是否限杆
                if (!TextUtils.isEmpty(mXG)) {
                    mTvXG.setText(OptionUtil.getYu(mYuChang.getXiangan(), mXG));
                }

                mERStr = mYuXun.getJyez();//禁止钓饵
                if (!TextUtils.isEmpty(mERStr)) {
                    mTvJY.setText(OptionUtil.getYu(mYuChang.getEr(), mERStr));
                }

                mEtOther.setText(mYuXun.getQtsm());//其他说明
                mEtJin.setText(mYuXun.getFyjs());//放鱼斤数

                mSf = mYuXun.getSfbz();//收费标准

                if (!TextUtils.isEmpty(mSf)) {
                    mTvSF.setText(mSf);
                    try{
                        String [] sfbz = mSf.split(" ");
                        if(sfbz[0].equals("2")){
                            mTvSF.setText(sfbz[1]);
                            return;
                        }
                        StringBuilder sb = new StringBuilder();
                        sb.append("放鱼:日钓");
                        sb.append(sfbz[1]);
                        sb.append("元");
                        sb.append(sfbz[2]);
                        sb.append("小时");

                        sb.append(sfbz[3]);
                        sb.append("元");
                        sb.append(sfbz[4]);
                        sb.append("小时");

                        sb.append(sfbz[5]);
                        sb.append("元");
                        sb.append(sfbz[6]);
                        sb.append("小时");

                        sb.append(sfbz[7]);
                        sb.append("元");
                        sb.append(sfbz[8]);
                        sb.append("小时");

                        mTvSF.setText(sb.toString());
                    }catch (Exception e){

                    }


                }

            }

            @Override
            public void onOtherResult(String data, int status) {
                showNormalDialogByTwoButton("您未完善渔场信息","是否完善？","取消", new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(android.content.DialogInterface dialog, int which) {
                        finish();
                    }
                },"确定", new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(android.content.DialogInterface dialog, int which) {
                        startActivity(new Intent(YuXunPublishActivity.this,UserInfoUpdateActivity.class));
                        finish();
                    }
                });

            }

            @Override
            public void onError(String msg) {
            }
        });
    }

    private void sub(){
        String otherDec = mEtOther.getText().toString();
        String dkTime = mTvKDTime.getText().toString();
        String fyTime = mTvFYTime.getText().toString();
        String js = mEtJin.getText().toString();
        if(TextUtils.isEmpty(mYZ)){
            showToast("请选择放鱼种类");
            return;
        }

        if(TextUtils.isEmpty(mXG)){
            showToast("请选择是否限杆");
            return;
        }
        if(TextUtils.isEmpty(mERStr)){
            showToast("请选择是否禁用钓饵");
            return;
        }
        if(TextUtils.isEmpty(otherDec)){
            showToast("请输入其他描述");
            return;
        }
        if(TextUtils.isEmpty(dkTime)){
            showToast("请选择开钓时间");
            return;
        }
        if(TextUtils.isEmpty(mSf)){
            showToast("请编辑收费标准");
            return;
        }
        if(TextUtils.isEmpty(fyTime)){
            showToast("请选择放鱼时间");
            return;
        }
        if(TextUtils.isEmpty(js)){
            showToast("请输入放鱼斤数");
            return;
        }

        //"http://m.dyjh.cc/appi.php?s=Fisherymsg/griEditFisherymsg?fyzl=2 5 8 9&fhid=8&xgcd=20&jyez=13 14
        // &qtsm=qi ta shuo Ming&dysj=2016年03月24日09时&sfbz=1 100 6 100 5 50 5 50 5&fysj=2016年03月24日09时&fyjs=100"
        showProgressDialog("正在发布");
        Map<String,String> values = new HashMap<>();
        values.put("fyzl", mYZ);
        values.put("fhid", mYuChang.getFhid()+"");
        values.put("xgcd", mXG);
        values.put("jyez", mERStr);
        values.put("qtsm", otherDec);
        values.put("dysj", dkTime);
        values.put("sfbz", mSf);
        values.put("fysj", fyTime);
        values.put("fyjs", js);

        mHttpClient.postData1(YUNCAHNG_INFO_UPDATE, values, new AppAjaxCallback.onResultListener() {
            @Override
            public void onResult(String data, String msg) {
                showToast("发布成功");
                cancelProgressDialog();
                finish();
            }

            @Override
            public void onOtherResult(String data, int status) {
                switch (status){
                    case 201:
                        showToast("缺少参数");
                        break;
                    case 301:
                        showToast("保存失败");
                        break;
                    case 300:
                        showToast("数据不正确");
                        break;
                    default:
                        showToast("发布失败");
                        break;
                }
                cancelProgressDialog();
            }

            @Override
            public void onError(String msg) {
                showToast("发布失败");
                cancelProgressDialog();
            }
        });
    }

    private ArrayList<String> mSelectPath;
    private static final int REQUEST_IMAGE = 6;
    private void openImage(){
        int selectedMode = MultiImageSelectorActivity.MODE_MULTI;
//        selectedMode = MultiImageSelectorActivity.MODE_SINGLE;

        int maxNum = 6;
        Intent intent = new Intent(YuXunPublishActivity.this, MultiImageSelectorActivity.class);
        // 是否显示拍摄图片
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SHOW_CAMERA, true);
        // 最大可选择图片数量
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_COUNT, maxNum);
        // 选择模式
        intent.putExtra(MultiImageSelectorActivity.EXTRA_SELECT_MODE, selectedMode);
        // 默认选择
        if (mSelectPath != null && mSelectPath.size() > 0) {
            intent.putExtra(MultiImageSelectorActivity.EXTRA_DEFAULT_SELECTED_LIST, mSelectPath);
        }

        startActivityForResult(intent, REQUEST_IMAGE);
    }


    private void upload(){
        showProgressDialog("正在提交数据");
        //渔场id,imagedata:图片流,imagetype:图片类型, ablum:保留的原来图片
        Map<String,String> values = new HashMap<>();
        values.put("fhid",mYuChang.getFhid()+"");
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


//        for(String img:mSelectPath){
//            values.put("imagedata[]", mImageTools.filePathToString(img));//（照片1的流,照片2的流）
//        }
//        values.put("imagedata", "("+mSelectPath.get(0).toString()+")");//（照片1的流,照片2的流）
       values.put("imagetype", "jpeg");

//        参数 fhid:渔汛id,imagedata:图片流,imagetype:图片类型

        mHttpClient.postData1(YUXUN_IMG_UPDATE, values, new AppAjaxCallback.onResultListener() {
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
