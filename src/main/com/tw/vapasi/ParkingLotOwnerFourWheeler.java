package com.tw.vapasi;

import com.tw.vapasi.exceptions.ParkingFullNotificationNotReceivedException;

public class ParkingLotOwnerFourWheeler implements ParkingLotOwner {
    private boolean parkingFull;

    boolean isParkingFull() throws ParkingFullNotificationNotReceivedException {
        if (!parkingFull) {
            throw new ParkingFullNotificationNotReceivedException("Haven't received notification from parking lot about parking full");
        }

        return true;
    }

    public void notifyParkingFull() {
        this.parkingFull = true;
    }
}
