package com.goodcompany.gamechangernotes.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.goodcompany.gamechangernotes.R;


public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 4000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent LoginIntent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(LoginIntent);
                finish();

            }
        },SPLASH_TIME_OUT);
    }
}
