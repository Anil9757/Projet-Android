package com.example.goldentiger.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.goldentiger.R;

public class MainActivity extends AppCompatActivity {

    //Search book button
    private Button button;

    //Book Favoris button
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RelativeLayout relativeLayout = findViewById(R.id.layout);
        //Les 4 lignes sont pour l'animation du background.
        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();

        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();

        ///////////////////////////////////////
        button = (Button) findViewById(R.id.button1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });

        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity3();
            }
        });
    }

    //Bouton permettant de faire une recherche de livre.
    public void openActivity2(){
        Intent intent = new Intent(this, SearchBook.class);
        startActivity(intent);
    }

    //Bouton permettant d'aller voir les favoris.
    public void openActivity3(){
        Intent intent = new Intent(this, Favoris.class);
        startActivity(intent);
    }
}
