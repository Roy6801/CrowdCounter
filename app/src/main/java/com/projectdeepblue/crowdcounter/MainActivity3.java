package com.projectdeepblue.crowdcounter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity3 extends AppCompatActivity {

    WebView wvMain3;
    SharedPreferences sp;
    Intent it;

    String camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_main3);

        it  = getIntent();
        camera = it.getStringExtra("camera");
        wvMain3 = findViewById(R.id.wvMain3);

        sp = getSharedPreferences("server", Context.MODE_PRIVATE);

        String url = "http://"+sp.getString("ip","")+":"+sp.getString("port","")+"/"+camera;

        wvMain3.setWebViewClient(new WebViewClient());
        wvMain3.getSettings().setJavaScriptEnabled(true);
        wvMain3.getSettings().setDisplayZoomControls(false);
        wvMain3.getSettings().setBuiltInZoomControls(true);
        wvMain3.getSettings().setLoadWithOverviewMode(true);
        wvMain3.getSettings().setUseWideViewPort(true);
        wvMain3.loadUrl(url);
    }
}