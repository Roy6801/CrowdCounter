package com.projectdeepblue.crowdcounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.webkit.WebView;

public class MainActivity3 extends AppCompatActivity {

    WebView webView;
    String url;
    Intent it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main3);

        it = getIntent();
        url = it.getStringExtra("url");

        webView = findViewById(R.id.wvMain3);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.loadUrl(url);
    }
}