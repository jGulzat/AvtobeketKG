package com.manas.avtobeketkg.Model;

public class VokzalInfo {
    Integer id;
    String name;
    int image1;
    String image;
    String info;
    String phoneNumber;
    String email;


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return name;
    }

    public int getImage() { return image1; }

    public String getInfo() {
        return info;
    }

    public VokzalInfo(String name, String phoneNumber, int image1, String info) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.image1 = image1;
        this.info = info;
    }
    public VokzalInfo(String name, String phoneNumber, String image, String info) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.image = image;
        this.info = info;
    }

}
