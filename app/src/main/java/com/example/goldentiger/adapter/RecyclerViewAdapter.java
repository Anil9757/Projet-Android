package com.example.goldentiger.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.*;

import com.example.goldentiger.R;
import com.example.goldentiger.activities.BookDetails;
import com.example.goldentiger.model.BookView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {


    private Context nContext;
    private List<BookView>  nData;
    private RequestOptions options;

    public RecyclerViewAdapter(Context nContext, List<BookView> nData)
    {
        this.nContext = nContext;
        this.nData = nData;

        options = new RequestOptions().centerCrop().placeholder(R.drawable.loading).error(R.drawable.loading);
    }


    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(nContext);
        //Dans cette vue il y a les éléments comme l'affichage de l'image, le titre, le prix et le nom de l'auteur formant
        //une ligne qui apparaitre dans la recyclerview.
        view = inflater.inflate(R.layout.book_row_item, parent, false);
        final MyViewHolder viewHolder = new MyViewHolder(view);
        //Cette fonction ne se réalise que quand on clique sur sur un livre se trouvant dans la recyclerview.
        //Il ramène à l'activité BookDetails où l'on voit les détails du livre.
        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(nContext , BookDetails.class);
                //Par l'intermédiaire du viewholder, on récupère chaque élément pour les envoyés dans l'activité BookDetails.
                int pos = viewHolder.getAdapterPosition();
                i.putExtra("book_title" ,nData.get(pos).getTitle());
                i.putExtra("book_author" ,nData.get(pos).getAuthor());
                i.putExtra("book_desc" ,nData.get(pos).getDescription());
                i.putExtra("book_categories" ,nData.get(pos).getCategory());
                i.putExtra("book_publish_date" ,nData.get(pos).getDatePublished());
                i.putExtra("book_info" ,nData.get(pos).getUrl());
                i.putExtra("book_thumbnail" ,nData.get(pos).getImage_url());

                //Lancement de l'activité BookDetails.
                nContext.startActivity(i);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {

        //on affecte les composants design aux valeurs récupérés
        //Les éléments ci-dessous seront affichés dans la recyclerview
        BookView book = nData.get(position);
        holder.tTitle.setText(book.getTitle());
        holder.tAuthor.setText(book.getAuthor());
        holder.tCategory.setText(book.getCategory());
        holder.tPrice.setText(book.getPrice());

        //Utilisation de la bibliothèque Glide pour l'affichage du livre.s
        Glide.with(nContext).load(book.getImage_url()).apply(options).into(holder.vThumbnail);

    }

    //La taille de liste de données.
    @Override
    public int getItemCount() {
        return nData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView vThumbnail;
        TextView tTitle, tCategory, tPrice, tAuthor;

        LinearLayout container;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);

            //Composant défini par rapport au design XML
            vThumbnail = itemView.findViewById(R.id.thumbnail);
            tTitle = itemView.findViewById(R.id.title);
            tCategory = itemView.findViewById(R.id.category);
            tAuthor = itemView.findViewById(R.id.author);
            tPrice = itemView.findViewById(R.id.price);

            container = itemView.findViewById(R.id.container);


        }

    }
}
