package com.manas.avtobeketkg.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class FoodService implements Serializable {
        String name;
        String address;
        String image;

        public String getName() {
            return name;
        }

        public String getAddress() {
            return address;
        }

        public String getImage() {
            return image;
        }

        public FoodService(String name, String address, String image) {
            this.name = name;
            this.address = address;
            this.image = image;
        }

}
