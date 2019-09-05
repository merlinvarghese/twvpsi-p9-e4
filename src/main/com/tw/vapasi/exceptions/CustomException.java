package com.tw.vapasi.exceptions;

public class CustomException extends Exception {
    public CustomException(String s) {
        super("ParkingLot::" + s);
    }
}
