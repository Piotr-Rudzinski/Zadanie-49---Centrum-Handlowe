package com.infoshareacademy.vehicles;

public class Car extends Vehicle {
    private String name;

    public Car (String name) {
        this.name = name;
    }

    public String getName () {
        return  name;
    }
}
