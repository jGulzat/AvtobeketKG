package com.manas.avtobeketkg.Model;

import java.io.Serializable;
import java.util.List;

public class PassengerInfo  implements Serializable {
    Integer trans_id;
    List<Passenger> places_to_book_or_buy;
    String action;
    Integer T_RouteId;
    Integer O_RouteId;

    public PassengerInfo( Integer t_RouteId, Integer o_RouteId,List<Passenger> places_to_book_or_buy, String action) {
        this.places_to_book_or_buy = places_to_book_or_buy;
        this.action = action;
        this.T_RouteId = t_RouteId;
        this.O_RouteId = o_RouteId;
    }

    public Integer getTrans_id() {
        return trans_id;
    }

    public List<Passenger> getPlaces_to_book_or_buy() {
        return places_to_book_or_buy;
    }

    public String getAction() {
        return action;
    }

    public PassengerInfo(Integer trans_id, List<Passenger> places_to_book_or_buy, String action) {
        this.trans_id = trans_id;
        this.places_to_book_or_buy = places_to_book_or_buy;
        this.action = action;
    }
}
