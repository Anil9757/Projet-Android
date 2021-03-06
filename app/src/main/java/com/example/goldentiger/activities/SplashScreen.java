package com.example.goldentiger.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.goldentiger.R;

public class SplashScreen extends AppCompatActivity {

    @Override
    //SplashScreen pour introduire le logo du Corbeau et ensuite affiché la page principale avec le menu principale.
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);
            }
        }, 3000);
    }
}
