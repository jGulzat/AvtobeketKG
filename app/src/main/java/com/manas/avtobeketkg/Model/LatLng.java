package com.manas.avtobeketkg.Model;

import java.io.Serializable;

public class LatLng implements Serializable {
    Double lat;
    Double lng;

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public LatLng(Double lat, Double lng) {
        this.lat = lat;
        this.lng = lng;
    }
}
