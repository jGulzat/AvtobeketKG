package com.manas.avtobeketkg.Model;

import java.util.List;

public class History {

    String username;
    String fullname;
    String from;
    String to;
    String date;
    String price;

    public History(String username, String fullname, String from, String to, String date, String price) {
        this.username = username;
        this.fullname = fullname;
        this.from = from;
        this.to = to;
        this.date = date;
        this.price = price;
    }

    public String getUsername() {
        return username;
    }

    public String getFullname() {
        return fullname;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getDate() {
        return date;
    }

    public String getPrice() {
        return price;
    }
}
