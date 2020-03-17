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

    ArrayAdapter<BookFavoris> adapter;

    BookFavoris book = new BookFavoris();

    private Button button;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoris);

        //EditText
        search_book = findViewById(R.id.i_search_box);

        mListView = (ListView) findViewById(R.id.listView);

        //On liste tous les éléments se trouvant dans la base de données BookFavoris.
        List<BookFavoris> list = BookFavoris.listAll(BookFavoris.class);

        //On mets en place l'adapter pour effectuer l'affichage de la liste.
        adapter = new ArrayAdapter<BookFavoris>(Favoris.this, android.R.layout.simple_list_item_1, list);
        mListView.setAdapter(adapter);

        //Usage d'un filter pour effectuer une recherche plus dynamique sur l'application.
        //L'utilisateur tape le début ou un élément de la liste de la liste pour trouver par exemple le titre de l'oeuvre et son auteur.
        search_book.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            //On utilise cette fonction générer pour trouver l'élement que l'on souhaite trouver.
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (Favoris.this).adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //Bouton permettant d'actionner la fonction DeleteAll
        button2 = (Button) findViewById(R.id.delete_buttton);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteAllBook();
            }
        });

    }

    //Fonction permettant de supprimer toutes la liste
    public void DeleteAllBook(){

        List<BookFavoris> listName = BookFavoris.listAll(BookFavoris.class);
        BookFavoris.deleteAll(BookFavoris.class);
        List<BookFavoris> list = BookFavoris.listAll(BookFavoris.class);

        adapter = new ArrayAdapter<BookFavoris>(Favoris.this, android.R.layout.simple_list_item_1, list);
        mListView.setAdapter(adapter);

    }

}
