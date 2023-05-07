package com.juangut.actividad1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashHome extends AppCompatActivity {

    private static int splashTimeOut = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_home);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent hIntent = new Intent(SplashHome.this, MainActivity.class);
                startActivity(hIntent);
                finish();
            }
        }, splashTimeOut);
    }
}