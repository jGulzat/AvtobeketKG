package com.manas.avtobeketkg.Model;

public class Stations {
    int id;
    String name;
    Double lat;
    Double lng;
    String formatted_address;
    String place_id;

    public Stations(int id, String name, Double lat, Double lng) {
        this.id = id;
        this.name = name;
        this.lat = lat;
        this.lng = lng;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public String getFormatted_address() { return formatted_address; }

    public String getPlace_id() { return place_id; }




}
