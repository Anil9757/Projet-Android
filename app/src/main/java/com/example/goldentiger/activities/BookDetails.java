package com.example.goldentiger.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;



import com.example.goldentiger.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

public class BookDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        //hide the default actionBar
        getSupportActionBar().hide();

        //Receive the data from Json

        Bundle extras = getIntent().getExtras(); //Initilizaze this variable to check if extras are null or not.

        String title ="", authors ="", description="" , categories ="", publishDate="",thumbnail ="", info="";
        if(extras != null){
            title = extras.getString("book_title");
            authors = extras.getString("book_author");
            description = extras.getString("book_desc");
            categories = extras.getString("book_categories");
            publishDate = extras.getString("book_publish_date");
            thumbnail = extras.getString("book_thumbnail");
            info = extras.getString("book_info");
        }

        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsingtoolbar);
        collapsingToolbarLayout.setTitleEnabled(true);

        TextView tTitle = findViewById(R.id.i_book_title);
        TextView tAuthors = findViewById(R.id.i_author);
        TextView tDescription = findViewById(R.id.text_description);
        TextView tCategory = findViewById(R.id.i_categorie);
        TextView tDatePublish = findViewById(R.id.i_publish_date);
        TextView tInformation = findViewById(R.id.book_info);

        ImageView iThumbnail = findViewById(R.id.i_thumbnail);

        tTitle.setText(title);
        tAuthors.setText(authors);
        tDescription.setText(description);
        tCategory.setText(categories);
        tDatePublish.setText(publishDate);

        final String InfoBook = info;

        tInformation.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(InfoBook));
                startActivity(i);
            }
        });


        collapsingToolbarLayout.setTitle(title);

        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.loading).error(R.drawable.loading);

        Glide.with(this).load(thumbnail).apply(requestOptions).into(iThumbnail);

    }
}
