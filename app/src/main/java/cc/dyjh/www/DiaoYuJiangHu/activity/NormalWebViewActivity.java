package cc.dyjh.www.DiaoYuJiangHu.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.DownloadListener;


import cc.dyjh.www.DiaoYuJiangHu.R;
import dev.mirror.library.android.view.ProgressWebView;

/**
 * Created by 王沛栋 on 2016/3/14.
 */
public class NormalWebViewActivity extends  BaseActivity{

    private ProgressWebView webview;
    private String url;
    private String mIntent;
    private String mTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_webview);

        setBack();

        mIntent = getIntent().getStringExtra(INTENT_ID);
        mTitle = getIntent().getStringExtra("TITLE");
        setTitleText(mTitle);

        url =mIntent;

        //绑定控件
        webview = (ProgressWebView) findViewById(R.id.webview);

        //设置数据

        //http://zmnyw.cn/index.php?s=/Home/Article/detail/id/60.html
        //\/index.php?s=\/Home\/Article\/detail\/id\/27.html
        webview.getSettings().setJavaScriptEnabled(true);
        webview.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });

        webview.loadUrl(url);
    }
}
