package com.example.booksearchengine.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booksearchengine.model.BookFavoris;

import com.example.booksearchengine.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Favoris extends AppCompatActivity {

    private ListView mListView;

    private EditText search_book;
    private TextView message_error;

    ArrayAdapter<BookFavoris> adapter;

    private ArrayList<BookFavoris> bookFavorisArrayList;

    BookFavoris book = new BookFavoris();

    private Button button;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoris);

        search_book = findViewById(R.id.i_search_box);

        mListView = (ListView) findViewById(R.id.listView);

        List<BookFavoris> list = BookFavoris.listAll(BookFavoris.class);

        adapter = new ArrayAdapter<BookFavoris>(Favoris.this, android.R.layout.simple_list_item_1, list);
        mListView.setAdapter(adapter);


        button = (Button) findViewById(R.id.search_buttton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String finalsearch = search_book.getText().toString();
                searchBook(finalsearch);
            }
        });

        button2 = (Button) findViewById(R.id.delete_buttton);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteAllBook();
            }
        });

    }

    public void searchBook(String title){

        List<BookFavoris> listName = BookFavoris.find(BookFavoris.class, "title = ? ", title);

        adapter = new ArrayAdapter<BookFavoris>(Favoris.this, android.R.layout.simple_list_item_1, listName);
        mListView.setAdapter(adapter);

    }

    public void BuyBook(String title){

        List<BookFavoris> listName = BookFavoris.find(BookFavoris.class, "title = ? ", title);

        adapter = new ArrayAdapter<BookFavoris>(Favoris.this, android.R.layout.simple_list_item_1, listName);
        mListView.setAdapter(adapter);

    }

    public void DeleteBook(String title){

        List<BookFavoris> listName = BookFavoris.listAll(BookFavoris.class);
        BookFavoris.deleteAll(BookFavoris.class);
        List<BookFavoris> list = BookFavoris.listAll(BookFavoris.class);

        adapter = new ArrayAdapter<BookFavoris>(Favoris.this, android.R.layout.simple_list_item_1, list);
        mListView.setAdapter(adapter);

    }

    public void DeleteAllBook(){

        List<BookFavoris> listName = BookFavoris.listAll(BookFavoris.class);
        BookFavoris.deleteAll(BookFavoris.class);
        List<BookFavoris> list = BookFavoris.listAll(BookFavoris.class);

        adapter = new ArrayAdapter<BookFavoris>(Favoris.this, android.R.layout.simple_list_item_1, list);
        mListView.setAdapter(adapter);

    }

}
