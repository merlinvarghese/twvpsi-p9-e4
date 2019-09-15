package com.tw.vapasi;

import com.tw.vapasi.exceptions.CustomException;
import com.tw.vapasi.exceptions.ParkingFullException;
import com.tw.vapasi.exceptions.VehicleAlreadyParkedException;
import com.tw.vapasi.exceptions.VehicleNotParkedException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class ParkingLot {
    private final int capacity;
    private Set<String> vehicles;
    private List<ParkingLotObserver> observers = new ArrayList<>();

    ParkingLot(int capacity) {
        this.capacity = capacity;
        vehicles = new HashSet<>();
    }

    void registerForNotifications(ParkingLotObserver observer) {
        observers.add(observer);
    }

    void park(Parkable vehicle) throws CustomException {
        if (isSlotNotAvailable()) {
            throw new ParkingFullException("park:: Parking full");
        }

        if (isVehicleAlreadyParked(vehicle)) {
            throw new VehicleAlreadyParkedException("park:: Vehicle already parked");
        }

        vehicles.add(vehicle.getRegistrationNumber());

        sendParkingFullNotification();
    }

    void unpark(Parkable vehicle) throws CustomException {
        if (!isVehicleAlreadyParked(vehicle)) {
            throw new VehicleNotParkedException("park:: Vehicle not parked");
        }

        vehicles.remove(vehicle.getRegistrationNumber());

        sendParkingAvailableNotification();
    }

    boolean isParked(Parkable vehicle) {
        return isVehicleAlreadyParked(vehicle);
    }

    private void sendParkingFullNotification() {
        if (isSlotNotAvailable()) {
            for (ParkingLotObserver observer : observers) {
                observer.notifyParkingFull(this);
            }
        }
    }

    private void sendParkingAvailableNotification() {
        if (isParkingAvailableAgain()) {
            for (ParkingLotObserver observer : observers) {
                observer.notifyParkingAvailable(this);
            }
        }
    }

    private boolean isParkingAvailableAgain() {
        return vehicles.size() == capacity - 1;
    }

    private boolean isVehicleAlreadyParked(Parkable vehicle) {
        return vehicles.contains(vehicle.getRegistrationNumber());
    }

    private boolean isSlotNotAvailable() {
        return vehicles.size() == capacity;
    }
}
