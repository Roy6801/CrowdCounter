package com.projectdeepblue.crowdcounter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity3 extends AppCompatActivity {

    DatabaseReference dbCam;
    WebView wvMain3;
    TextView tvMain3;
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
        tvMain3 = findViewById(R.id.tvMain3);

        sp = getSharedPreferences("server", Context.MODE_PRIVATE);

        String server = sp.getString("name","");
        dbCam = FirebaseDatabase.getInstance().getReference("Servers/"+server+"/Cameras/"+camera);

        dbCam.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tvMain3.setText("Count : "+snapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

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