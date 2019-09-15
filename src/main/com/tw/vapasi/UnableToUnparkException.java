package com.tw.vapasi;

import com.tw.vapasi.exceptions.CustomException;

class UnableToUnparkException extends CustomException {
    UnableToUnparkException(String s) {
        super(s);
    }
}
