package com.manas.avtobeketkg.Model;

import java.io.Serializable;

public class Search implements Serializable {
    Integer from;
    Integer to;
    String date;

    public Search(Integer from, Integer to, String date) {
        this.from = from;
        this.to = to;
        this.date = date;
    }

}
