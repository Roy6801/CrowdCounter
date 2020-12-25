package com.projectdeepblue.crowdcounter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

public class SplashActivity extends AppCompatActivity {

    Intent it;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        SharedPreferences sp = getSharedPreferences("info", MODE_PRIVATE);

        it = new Intent(SplashActivity.this, LoginActivity.class);

        try
        {
            if (sp.contains("pwd"))
            {
                it = new Intent(SplashActivity.this, MainActivity.class);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(it);
                finish();
            }
        },3000);
    }
}