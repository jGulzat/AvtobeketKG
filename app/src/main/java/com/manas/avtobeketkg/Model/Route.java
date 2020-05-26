package com.manas.avtobeketkg.Model;
import java.io.Serializable;
import java.util.List;

public class Route implements Serializable {

    private Integer id;
    private String from;
    private String to;
    private String date;
    private String price;
    private String arrive_time;
    private String dictance;
    private List<LatLng> route;
    private String trans_schema;
    private List<FoodService>foods;
    private String baggage;
    private String status = "to";
    private Boolean success;
    private String message;

    public Boolean getSucces() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    private  Integer numberOfSeats;
    private boolean isSelected = false;

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setNumberOfSeats(Integer numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Integer getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }


    public void setRoute(List<LatLng> route) {
        this.route = route;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public List<FoodService> getFoods() {
        return foods;
    }

    public String getBaggage() {
        return baggage;
    }

    public Route(String from, String to, String date, String price,
                 String arrive_time, String dictance, List<LatLng> route, String trans_schema, List<FoodService> foods,
                 String baggage, Integer numberOfSeats, Boolean success, String message) {
        this.from = from;
        this.to = to;
        this.date = date;
        this.price = price;
        this.arrive_time = arrive_time;
        this.dictance = dictance;
        this.route = route;
        this.trans_schema = trans_schema;
        this.foods = foods;
        this.baggage = baggage;
        this.numberOfSeats = numberOfSeats;
        this.status = "to";
        this.success = success;
        this.message = message;

    }

    public Route( String from, String date, String price, String arrive_time) {
        this.from = from;
        this.date = date;
        this.price = price;
        this.arrive_time = arrive_time;
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

    public String getArrive_time() {
        return arrive_time;
    }

    public String getDictance() {
        return dictance;
    }

    public String getTrans_schema() { return trans_schema; }

    public List<LatLng>getRoute() { return  route; }

    public Integer getId() { return id; }
}

