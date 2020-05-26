package com.manas.avtobeketkg.Model;

import java.io.Serializable;

public class Passenger extends Throwable implements Serializable {
    int number;
    String user_email;
    String user_name;
    Integer place_number;
    String S_code;

    public Passenger(int number, int place_number) {
        this.number = number;
        this.place_number = place_number;
    }
    public Passenger(Integer place_number, String user_name, String user_email) {
        this.place_number = place_number;
        this.user_name = user_name;
        this.user_email = user_email;
    }


    public Passenger(String email, String user_name) {
        this.user_email = email;
        this.user_name = user_name;
    }

    public String getEmail() {
        return user_email;
    }

    public String getFullname() {
        return user_name;
    }

    public void setEmail(String email) {
        this.user_email = email;
    }

    public void setFullname(String fullname) {
        this.user_name = fullname;
    }

    public int getNumber() {
        return number;
    }

    public int getPlace_number() {
        return place_number;
    }

    public void setPlace_number(int place_number) {
        this.place_number = place_number;
    }

}
