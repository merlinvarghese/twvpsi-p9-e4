package com.tw.vapasi;

interface ParkingLotObserver {
    void notifyParkingFull(ParkingLot parkingLot);
    void notifyParkingAvailable(ParkingLot parkingLot);
}