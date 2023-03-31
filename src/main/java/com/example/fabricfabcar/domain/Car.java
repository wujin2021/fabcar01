package com.example.fabricfabcar.domain;

public class Car {
    private String key;
    private String make;
    private String model;
    private String colour;
    private String owner;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }




    @Override
    public String toString() {
        return "Car{" +
                "key='" + key + '\'' +
                ", make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", colour='" + colour + '\'' +
                ", owner='" + owner + '\'' +
                '}';
    }
}
