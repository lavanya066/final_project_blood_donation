package com.example.blooddonation.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class overall {

        @SerializedName("blogdata")
        @Expose
        private String blogdata;
        @SerializedName("blogdate")
        @Expose
        private String blogdate;
        @SerializedName("blogname")
        @Expose
        private String blogname;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("imageurl")
        @Expose
        private String imageurl;

        public String getBlogdata() {
            return blogdata;
        }

        public void setBlogdata(String blogdata) {
            this.blogdata = blogdata;
        }

        public String getBlogdate() {
            return blogdate;
        }

        public void setBlogdate(String blogdate) {
            this.blogdate = blogdate;
        }

        public String getBlogname() {
            return blogname;
        }

        public void setBlogname(String blogname) {
            this.blogname = blogname;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getImageurl() {
            return imageurl;
        }

        public void setImageurl(String imageurl) {
            this.imageurl = imageurl;
        }

    }
