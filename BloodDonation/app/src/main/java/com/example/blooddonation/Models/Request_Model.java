package com.example.blooddonation.Models;


    import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

    public class Request_Model {

        @SerializedName("request")
        @Expose
        private List<Request> request = null;

        public List<Request> getRequest() {
            return request;
        }

        public void setRequest(List<Request> request) {
            this.request = request;
        }

    }

