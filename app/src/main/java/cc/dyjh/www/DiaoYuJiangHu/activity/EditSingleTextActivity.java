package cc.dyjh.www.DiaoYuJiangHu.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import cc.dyjh.www.DiaoYuJiangHu.R;

/**
 * Created by dongqian on 16/3/24.
 */
public class EditSingleTextActivity extends BaseActivity {
    private EditText mEt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_single_text);
        setTitleText("");
        setBack();
        setRightTitle("保存");
        mEt = (EditText)findViewById(R.id.et);
        mEt.setText(getIntent().getStringExtra(INTENT_ID));
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.right_text:
                String str = mEt.getText().toString();
                if(TextUtils.isEmpty(str)){
                    showToast("请输入编辑的内容");
                    return;
                }
                Uri datas = Uri.parse(str);
                Intent intent = new Intent(null,datas);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}
