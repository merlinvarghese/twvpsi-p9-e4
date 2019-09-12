package com.tw.vapasi;

import com.tw.vapasi.exceptions.CustomException;
import com.tw.vapasi.exceptions.VehicleNotParkedException;

import java.util.*;

class Valet implements ParkingLotObserver {
    private Set<ParkingLot> availableParkingLots;
    private Map<String, ParkingLot> parkingLotsMapping = new HashMap<>();

    Valet(Set<ParkingLot> parkingLots) {
        availableParkingLots = parkingLots;
        registerForNotifications();
    }

    private void registerForNotifications() {
        for (ParkingLot parkingLot : availableParkingLots) {
            parkingLot.registerForNotifications(this);
        }
    }

    void park(Parkable vehicle) throws CustomException {
        if (availableParkingLots.isEmpty()) {
            throw new NoParkingLotsAvailableException("All Parking Lots Full. Can't park");
        }

        Iterator<ParkingLot> itr = availableParkingLots.iterator();
        ParkingLot parkingLotToPark = itr.next();
        parkingLotToPark.park(vehicle);
        parkingLotsMapping.put(vehicle.getRegistrationNumber(), parkingLotToPark);
    }


    public void notifyParkingFull(ParkingLot parkingLot) {
        availableParkingLots.remove(parkingLot);
    }

    public void notifyParkingAvailable(ParkingLot parkingLot) {
        availableParkingLots.add(parkingLot);
    }
}
