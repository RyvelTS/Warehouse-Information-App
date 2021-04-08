package com.rtsproject.ryvel_2018939060053_final;

public class commodity {
    private String id;
    private String name;
    private String number;
    private String manufacturer;
    private String price;
    private String location;

    public commodity(String id, String name, String number, String manufacturer, String price, String location) {
        this.id= id;
        this.name = name;
        this.number = number;
        this.manufacturer = manufacturer;
        this.price = price;
        this.location = location;


    }
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getPrice() {
        return price;
    }

    public String getLocation() {
        return location;
    }


}
