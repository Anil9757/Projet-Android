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


    private void searchBook(){

        String search_query = search_text.getText().toString(); //Writing the name of the book or description.

        boolean is_connected = NetworkState(this);
        if(!is_connected)
        {
            DialogFragment simpleDialogFragment = new SimpleDialogFragment();
            simpleDialogFragment.show(getSupportFragmentManager(), "Wifi Dialog");
           // message_error.setText("Echec de connexion Internet");
           // recyclerView.setVisibility(View.INVISIBLE);
           // message_error.setVisibility(View.VISIBLE);
            return;
        }

        if(search_query.equals(""))
        {
            Toast.makeText(this,"Veuillez rentrer un nom de livre, ou une petite description, ou un thème permettant de trouver un livre ",Toast.LENGTH_SHORT).show();
            return;
        }
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
                        String title = "";
                        String author = "";
                        String datePublished = "Pas encore valable";
                        String description = "Aucune Description";
                        int pageCount = 1000;
                        String categories = "Pas de catégories ";

                        String price = "Prix non-disponible";

                        try {
                            JSONArray items = response.getJSONArray("items");

                            for (int i = 0; i < items.length(); i++) {
                                JSONObject item = items.getJSONObject(i);
                                JSONObject volumeInfo = item.getJSONObject("volumeInfo");


                                try {
                                    title = volumeInfo.getString("title");

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
                                String thumbnail = volumeInfo.getJSONObject("imageLinks").getString("thumbnail");

                                String previewLink = volumeInfo.getString("previewLink");
                                String url = volumeInfo.getString("infoLink");


                                lstBook.add(new BookView(title, author, datePublished, description, categories
                                        , thumbnail, price, pageCount, url));


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

    private boolean NetworkState(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }
}