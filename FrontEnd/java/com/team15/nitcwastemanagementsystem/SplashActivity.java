package com.team15.nitcwastemanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Only open splash screen for 3 seconds
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i =  new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
                finish();
            }
        },3000);
    }
}