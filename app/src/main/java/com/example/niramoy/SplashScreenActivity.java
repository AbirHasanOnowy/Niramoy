package com.example.niramoy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;

public class SplashScreenActivity extends AppCompatActivity {

    Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        delayGeneration();
    }
        private void delayGeneration() {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    i = new Intent(SplashScreenActivity.this, SignInActivity.class);
                    startActivity(i);
                    finish();
                }

            },2000);
        }

}