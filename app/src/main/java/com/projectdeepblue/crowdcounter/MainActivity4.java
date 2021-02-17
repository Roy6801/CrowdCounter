package com.projectdeepblue.crowdcounter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity4 extends AppCompatActivity {

    DatabaseReference dbHist;
    WebView wvMain4;
    SharedPreferences sp;
    Intent it;

    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setContentView(R.layout.activity_main4);

        it = getIntent();
        sp = getSharedPreferences("server", Context.MODE_PRIVATE);
        wvMain4 = findViewById(R.id.wvMain4);

        String timestamp = it.getStringExtra("timestamp");
        String server = sp.getString("name","");

        dbHist = FirebaseDatabase.getInstance().getReference("History/"+server+"/"+timestamp);
        dbHist.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                url = snapshot.getValue().toString();
                wvMain4.setWebViewClient(new WebViewClient());
                wvMain4.getSettings().setJavaScriptEnabled(true);
                wvMain4.getSettings().setDisplayZoomControls(false);
                wvMain4.getSettings().setBuiltInZoomControls(true);
                wvMain4.getSettings().setLoadWithOverviewMode(true);
                wvMain4.getSettings().setUseWideViewPort(true);
                wvMain4.loadUrl(url);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}