package com.example.blooddonation.Models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Marker_location {




        @SerializedName("locations")
        @Expose
        private List<Locations> locations = null;

        public List<Locations> getLocations() {
            return locations;
        }

        public void setLocations(List<Locations> locations) {
            this.locations = locations;
        }

    }
