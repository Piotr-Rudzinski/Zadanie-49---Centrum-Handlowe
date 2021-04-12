package com.infoshareacademy.vehicles;

public class Plane extends Vehicle {
    private String name;

    public Plane (String name) {
        this.name = name;
    }

    public String getName () {
        return  name;
    }
}
