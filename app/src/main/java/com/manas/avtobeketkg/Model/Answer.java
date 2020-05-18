package com.manas.avtobeketkg.Model;

import java.util.List;

public class Answer {
    String status;
    List<Passenger> places;
    public Answer(String status){ this.status = status;}

    public String getStatus() {
        return status;
    }
}
