package com.tw.vapasi;

import com.tw.vapasi.exceptions.CustomException;
import com.tw.vapasi.exceptions.ParkingFullException;
import com.tw.vapasi.exceptions.VehicleAlreadyParkedException;
import com.tw.vapasi.exceptions.VehicleNotParkedException;

import java.util.HashSet;
import java.util.Set;

public class ParkingLot {
    private final int capacity;
    private Set<String> vehicles;

    public ParkingLot(int capacity) {
        this.capacity = capacity;
        vehicles = new HashSet<>();
    }

    public void park(Vehicle vehicle) throws CustomException {
        if (isSlotNotAvailable()) {
            throw new ParkingFullException("park:: Parking full");
        }

        if (isVehicleAlreadyParked(vehicle)) {
            throw new VehicleAlreadyParkedException("park:: Vehicle already parked");
        }

        vehicles.add(vehicle.getName());
    }

    public void unPark(Vehicle vehicle) throws CustomException {
        if (!isVehicleAlreadyParked(vehicle)) {
            throw new VehicleNotParkedException("park:: Vehicle not parked");
        }
    }

    private boolean isVehicleAlreadyParked(Vehicle vehicle) {
        return vehicles.contains(vehicle.getName());
    }

    private boolean isSlotNotAvailable() {
        return vehicles.size() == capacity;
    }
}
