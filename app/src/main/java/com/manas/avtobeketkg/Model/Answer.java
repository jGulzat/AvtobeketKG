package com.manas.avtobeketkg.Model;

import java.util.List;

public class Answer {
    String status;
    String user;
    String action;
    String route_schema;
    List<Passenger> places;


    public Answer(String status, String user, String action, String route_schema, List<Passenger> places) {
        this.status = status;
        this.user = user;
        this.action = action;
        this.route_schema = route_schema;
        this.places = places;
    }

    public String getStatus() {
        return status;
    }

    public String getUser() {
        return user;
    }

    public String getAction() {
        return action;
    }

    public String getRoute_schema() {
        return route_schema;
    }

    public List<Passenger> getPlaces() {
        return places;
    }
}
