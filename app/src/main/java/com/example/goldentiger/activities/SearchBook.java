package com.example.goldentiger.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.goldentiger.R;
import com.example.goldentiger.adapter.RecyclerViewAdapter;
import  com.example.goldentiger.model.BookView;

import android.view.View;
import android.widget.*;

import com.android.volley.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class SearchBook extends AppCompatActivity {

    private final String JSON_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    private List<BookView> lstBook;
    private RecyclerView recyclerView;

    private EditText search_text;
    private Button search_button;
    private TextView message_error;
    private RecyclerViewAdapter rAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_book);

        recyclerView = findViewById(R.id.book_view);
        search_text =findViewById(R.id.search_box);
        search_button= findViewById(R.id.search_buttton);
        message_error = findViewById(R.id.message_display);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        lstBook = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);


        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lstBook.clear();
                searchBook();
            }
        });

    }

    //Fonction permettant d'effectuer la recherche à travers le parcours de l'API GOOGLE Book API
    private void searchBook(){

        String search_query = search_text.getText().toString(); //Writing the name of the book or description.

        //Test si l'application est connecté ou non à internet
        boolean is_connected = NetworkState(this);
        if(!is_connected)
        {
            //Si ce n'est pas le cas, un fragment sous forme de dialogue apparaitra
            DialogFragment simpleDialogFragment = new SimpleDialogFragment();
            simpleDialogFragment.show(getSupportFragmentManager(), "Wifi Dialog");
           // message_error.setText("Echec de connexion Internet");
           // recyclerView.setVisibility(View.INVISIBLE);
           // message_error.setVisibility(View.VISIBLE);
            return;
        }
        //Si l'EditText et vide alors un toast apparaitra pour forcer l'utilisateur à mettre quelque chose dedans.
        if(search_query.equals(""))
        {
            Toast.makeText(this,"Veuillez rentrer un nom de livre, ou une petite description, ou un thème permettant de trouver un livre ",Toast.LENGTH_SHORT).show();
            return;
        }

        //Lancement de l'algorithm qui permettra de faire une recherhe de livres en faisant un parcours en détails des valeurs JSON.
        String final_query =search_query.replace(" ","+");
        Uri uri= Uri.parse(JSON_URL+final_query);
        Uri.Builder buider = uri.buildUpon();

        jsonrequest(buider.toString());
    }

    private void jsonrequest(String key) {
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, key.toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        //Si les éléments ne sont pas existant dans les valeurs JSON de l'API Google Book
                        //Les éléments seront affiché ainsi dans le design
                        String title = "";
                        String author = "";
                        String datePublished = "Pas encore valable";
                        String description = "Aucune Description";
                        int pageCount = 1000;
                        String categories = "Pas de catégories ";

                        String price = "Prix non-disponible";

                        try {
                            //On entre dans le tableau items pour récupérer les valeurs
                            JSONArray items = response.getJSONArray("items");

                            //Récupération des valeurs json dans le tableau items.
                            for (int i = 0; i < items.length(); i++) {
                                JSONObject item = items.getJSONObject(i);
                                JSONObject volumeInfo = item.getJSONObject("volumeInfo");

                                //On récupère les différents objets.
                                try {
                                    title = volumeInfo.getString("title");

                                    //Un livre peu être écrit par un ou plusieurs auteurs.
                                    //On doit donc parcourir le tableau autors
                                    JSONArray authors = volumeInfo.getJSONArray("authors");
                                    if (authors.length() == 1) {
                                        author = authors.getString(0);
                                    } else {
                                        author = authors.getString(0) + "|" + authors.getString(1);
                                    }


                                    datePublished = volumeInfo.getString("publishedDate");
                                    pageCount = volumeInfo.getInt("pageCount");


                                    JSONObject saleInfo = item.getJSONObject("saleInfo");
                                    JSONObject listPrice = saleInfo.getJSONObject("listPrice");
                                    price = listPrice.getString("amount") + " " + listPrice.getString("currencyCode");
                                    description = volumeInfo.getString("description");
                                    categories = volumeInfo.getJSONArray("categories").getString(0);

                                } catch (Exception e) {

                                }
                                //On récupère l'url de l'image qu'on utilisera pour afficher l'image de la première de couverture avec la librairie Glide.
                                String thumbnail = volumeInfo.getJSONObject("imageLinks").getString("thumbnail");

                                String previewLink = volumeInfo.getString("previewLink");
                                String url = volumeInfo.getString("infoLink");


                                //On insère les éléments dans la liste de Book view
                                lstBook.add(new BookView(title, author, datePublished, description, categories
                                        , thumbnail, price, pageCount, url));


                                //Affichage des éléments dans le RecyclerView
                                rAdapter = new RecyclerViewAdapter(SearchBook.this, lstBook);
                                recyclerView.setAdapter(rAdapter);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }

    //Fonction permettant de vérifier si on a bien une connexion internet
    private boolean NetworkState(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }
}
