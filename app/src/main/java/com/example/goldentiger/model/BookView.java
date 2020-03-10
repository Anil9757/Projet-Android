package com.example.goldentiger.model;

public class BookView {

        private String title;
        private String author;
        private String datePublished;
        private String Description;
        private String Category;
        private String image_url;
        private String price;
        private int pageCount;
        private String Url;

        public BookView(String title, String author, String datePublished, String Description, String Category, String image_url,
                        String price, int pageCount, String Url)
        {
            this.title = title;
            this.author = author;
            this.datePublished = datePublished;
            this.Description = Description;
            this.Category = Category;
            this.price = price;
            this.image_url = image_url;
            this.pageCount = pageCount;
            this.Url = Url;

        }

        public String getTitle() {
            return title;
        }

        public String getAuthor() {
            return author;
        }

        public String getDatePublished() {
            return datePublished;
        }

        public String getDescription() {
            return Description;
        }

        public String getCategory() {
            return Category;
        }

        public String getImage_url() {
            return image_url;
        }

        public String getPrice() {
            return price;
        }

        public int getPageCount() {
            return pageCount;
        }

        public String getUrl() {
            return Url;
        }
    }
