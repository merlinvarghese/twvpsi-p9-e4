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
    private ParkingLotOwner parkingLotOwner;

    ParkingLot(int capacity, ParkingLotOwner parkingLotOwner) {
        this.capacity = capacity;
        this.parkingLotOwner = parkingLotOwner;
        vehicles = new HashSet<>();
    }

    public ParkingLot(int capacity) {
        this.capacity = capacity;
        this.parkingLotOwner = null;
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
        if (isSlotNotAvailable() && isOwnerPresent()) {
            parkingLotOwner.notifyParkingFull();
        }
    }

    void unpark(Parkable vehicle) throws CustomException {
        if (!isVehicleAlreadyParked(vehicle)) {
            throw new VehicleNotParkedException("park:: Vehicle not parked");
        }

        vehicles.remove(vehicle.getRegistrationNumber());

        if (isOwnerPresent() && isThisFirstSlotAvaialbleAfterParkingWasFull()) {
            parkingLotOwner.notifyParkingAvailable();
        }
    }

    private boolean isThisFirstSlotAvaialbleAfterParkingWasFull() {
        return vehicles.size() == capacity - 1;
    }

    private boolean isOwnerPresent() {
        return parkingLotOwner != null;
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
