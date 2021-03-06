package com.example.goldentiger.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;

import com.example.goldentiger.R;

import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.example.goldentiger.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.example.goldentiger.model.BookFavoris;

//Cette activité s'affiche lorsqu'on clique sur un livre se trouvant dans le RecyclerView.
//On peut voir les détails du livre comme le nom de l'auteur, la catégorie, la date de publication.
//On peut voir la description du livre.
//Il y a un bouton Info qui permet d'aller sur différents site liés au livre: Google Book Store ou E-book pour ainsi acheté le livre ou voir les avis.
//L'utilisateur peut ajouter le livre dans ses favoris.

public class BookDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        //hide the default actionBar
        getSupportActionBar().hide();

        //Receive the data from Json

        Bundle extras = getIntent().getExtras(); //Initilisation de la variable pour voir si les extra son null ou s'il y a une valeur.

        String title ="", authors ="", description="" , categories ="", publishDate="",thumbnail ="", info="";

        //On associe les variable string aux valeurs Json récupéré dans l'activité SearchBook.java

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

        //association des composants designs avec les id du XML

        TextView tTitle = findViewById(R.id.i_book_title);
        TextView tAuthors = findViewById(R.id.i_author);
        TextView tDescription = findViewById(R.id.text_description);
        TextView tCategory = findViewById(R.id.i_categorie);
        TextView tDatePublish = findViewById(R.id.i_publish_date);
        TextView tInformation = findViewById(R.id.book_info);

        TextView tFavoris = findViewById(R.id.book_favoris);

        ImageView iThumbnail = findViewById(R.id.i_thumbnail);

        tTitle.setText(title);
        tAuthors.setText(authors);
        tDescription.setText(description);
        tCategory.setText(categories);
        tDatePublish.setText(publishDate);

        final String InfoBook = info;

        //Le bouton Info emmènera l'utilisateur vers une page lié au livre, soit pour l'acheter, soit pour voir les commentaires et notes sur le livres.
        tInformation.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(InfoBook));
                startActivity(i);
            }
        });


        //Insertion des détails du livres (Titre et noms d'Auteurs) dans la base de données BookFavoris (BDD Sugar ORM)
        tFavoris.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                TextView tTitle = findViewById(R.id.i_book_title);
                TextView tAuthors = findViewById(R.id.i_author);
                String Title = tTitle.getText().toString();
                String Authors = tAuthors.getText().toString();
                BookFavoris newFavorite = new BookFavoris(Title, Authors);
                newFavorite.save();

                DialogFragment recordDialogFragment = new RecordDialogFragment();
                recordDialogFragment.show(getSupportFragmentManager(), "Record Dialog");
            }
        });


        collapsingToolbarLayout.setTitle(title);

        RequestOptions requestOptions = new RequestOptions().centerCrop().placeholder(R.drawable.loading).error(R.drawable.loading);

        //Affichage de la photo de couverture du livre part l'intermédiaire de Glide et de l'url de l'image.
        Glide.with(this).load(thumbnail).apply(requestOptions).into(iThumbnail);

    }
}
