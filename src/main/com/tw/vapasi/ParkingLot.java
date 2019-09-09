package com.tw.vapasi;

import com.tw.vapasi.exceptions.CustomException;
import com.tw.vapasi.exceptions.ParkingFullException;
import com.tw.vapasi.exceptions.VehicleAlreadyParkedException;
import com.tw.vapasi.exceptions.VehicleNotParkedException;

import java.util.HashSet;
import java.util.Set;

class ParkingLot {
    private final int capacity;
    private Set<String> vehicles;

    ParkingLot(int capacity) {
        this.capacity = capacity;
        vehicles = new HashSet<>();
    }

    void park(Parkable vehicle) throws CustomException {
        if (isSlotNotAvailable()) {
            throw new ParkingFullException("park:: Parking full");
        }

        if (isVehicleAlreadyParked(vehicle)) {
            throw new VehicleAlreadyParkedException("park:: Vehicle already parked");
        }

        vehicles.add(vehicle.getRegistrationNumber());
    }

    void unpark(Parkable vehicle) throws CustomException {
        if (!isVehicleAlreadyParked(vehicle)) {
            throw new VehicleNotParkedException("park:: Vehicle not parked");
        }

        vehicles.remove(vehicle.getRegistrationNumber());
    }

    boolean isParked(Parkable vehicle) {
        return isVehicleAlreadyParked(vehicle);
    }

    private boolean isVehicleAlreadyParked(Parkable vehicle) {
        return vehicles.contains(vehicle.getRegistrationNumber());
    }

    private boolean isSlotNotAvailable() {
        return vehicles.size() == capacity;
    }
}
