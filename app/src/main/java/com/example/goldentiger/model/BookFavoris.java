package com.example.goldentiger.model;

import com.orm.SugarRecord;

public class BookFavoris extends SugarRecord {

     String title;
     String author;

    public BookFavoris(){

    }

    public BookFavoris(String title, String author)
    {
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }


    public String toString()
    {
        return "Title: " + title +"\n Author(s): " +author;
    }

}
