package com.manas.avtobeketkg.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PassengerInfo  implements Serializable {
    String action;
    Integer T_RouteId;
    List<Passenger> Passengers;

    public Integer getT_RouteId() {
        return T_RouteId;
    }

    public Integer getO_RouteId() {
        return O_RouteId;
    }

    Integer O_RouteId;

    public List<Passenger> getPassengers() {
        return Passengers;
    }

    public String getAction() {
        return action;
    }

    public PassengerInfo(Integer T_RouteId, List<Passenger> Passengers, String action) {
        this.T_RouteId = T_RouteId;
        this.Passengers = Passengers;
        this.action = action;
    }
    public PassengerInfo(Integer T_RouteId,Integer o_RouteId, String action, List<Passenger> passengers) {
        this.T_RouteId = T_RouteId;
        this.O_RouteId = o_RouteId;
        this.Passengers = passengers;
        this.action = action;
    }
}
