package com.tw.vapasi;

public class Vehicle implements Parkable {
    final String registrationNumber;

    public Vehicle(String name) {
        this.registrationNumber = name;
    }

    public String getName() {
        return registrationNumber;
    }
}
