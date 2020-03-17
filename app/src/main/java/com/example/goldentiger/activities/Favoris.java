package com.example.goldentiger.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.goldentiger.model.BookFavoris;

import com.example.goldentiger.R;

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

        search_book.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (Favoris.this).adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

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
