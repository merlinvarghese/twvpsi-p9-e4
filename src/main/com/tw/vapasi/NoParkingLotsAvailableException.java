package com.tw.vapasi;

import com.tw.vapasi.exceptions.CustomException;

class NoParkingLotsAvailableException extends CustomException {
    NoParkingLotsAvailableException(String message) {
        super(message);
    }
}
