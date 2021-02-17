package com.projectdeepblue.crowdcounter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MallActivity2 extends AppCompatActivity {

    DatabaseReference dbHist;
    WebView wvMall2;
    Intent it;
    String timestamp,url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_mall2);

        wvMall2 = findViewById(R.id.wvMall2);

        it = getIntent();
        timestamp = it.getStringExtra("timestamp");

        dbHist = FirebaseDatabase.getInstance().getReference("Mall/History/"+timestamp);

        dbHist.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                url = snapshot.getValue().toString();
                wvMall2.setWebViewClient(new WebViewClient());
                wvMall2.getSettings().setJavaScriptEnabled(true);
                wvMall2.getSettings().setDisplayZoomControls(false);
                wvMall2.getSettings().setBuiltInZoomControls(true);
                wvMall2.getSettings().setLoadWithOverviewMode(true);
                wvMall2.getSettings().setUseWideViewPort(true);
                wvMall2.loadUrl(url);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}