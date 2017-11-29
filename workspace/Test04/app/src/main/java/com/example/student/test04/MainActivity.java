package com.example.student.test04;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText editUrl;
    private Button btnGo;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // inflate

        editUrl = (EditText) findViewById(R.id.edit_url);
        btnGo = (Button) findViewById(R.id.btn_go);
        webView = (WebView) findViewById(R.id.webview);

        // 웹뷰에 내장 웹브라우저 하나 장착하기
//        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view,request,error);
                Toast.makeText(MainActivity.this,"Ohhh",Toast.LENGTH_SHORT).show();
                view.loadUrl("file:///android_res/raw/hang.html");
            }
        });

        // 웹뷰에 적용할 셋팅 여러개 한꺼번에 적용시키는 settings
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

//        webView.loadUrl("http://m.daum.net");
        webView.loadUrl("file:///android_res/raw/hang.html");

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = editUrl.getText().toString();
                webView.loadUrl("http://"+url);
            }
        });
    }

}
