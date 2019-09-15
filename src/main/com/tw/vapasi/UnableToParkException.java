package com.tw.vapasi;

import com.tw.vapasi.exceptions.CustomException;

class UnableToParkException extends CustomException {
    UnableToParkException(String s) {
        super(s);
    }
}
