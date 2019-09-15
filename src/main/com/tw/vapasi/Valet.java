package com.tw.vapasi;

import com.tw.vapasi.exceptions.CustomException;
import com.tw.vapasi.exceptions.VehicleNotParkedException;

import java.util.*;

class Valet implements ParkingLotObserver {
    private Set<ParkingLot> availableParkingLots;
    private Set<ParkingLot> registeredParkingLots;
    private Map<String, ParkingLot> parkingLotsMapping = new HashMap<>();

    Valet(Set<ParkingLot> parkingLots) {
        availableParkingLots = new HashSet<>();
        availableParkingLots.addAll(parkingLots);

        registeredParkingLots = new HashSet<>();
        registeredParkingLots.addAll(parkingLots);

        registerForNotifications();
    }

    private void registerForNotifications() {
        for (ParkingLot parkingLot : availableParkingLots) {
            parkingLot.registerForNotifications(this);
        }
    }

    void park(Parkable vehicle) throws NoParkingLotsAvailableException, UnableToParkException {
        if (availableParkingLots.isEmpty()) {
            throw new NoParkingLotsAvailableException("All Parking Lots Full. Can't park");
        }

        Iterator<ParkingLot> itr = availableParkingLots.iterator();
        ParkingLot parkingLotToPark = itr.next();
        try {
            parkingLotToPark.park(vehicle);
        } catch (CustomException e) {
            throw new UnableToParkException("Vehicle could not be parked");
        }

        parkingLotsMapping.put(vehicle.getRegistrationNumber(), parkingLotToPark);
    }


    public void notifyParkingFull(ParkingLot parkingLot) {
        availableParkingLots.remove(parkingLot);
    }

    public void notifyParkingAvailable(ParkingLot parkingLot) {
        availableParkingLots.add(parkingLot);
    }

    boolean isParked(Parkable vehicle) {
        ParkingLot parkingLot = parkingLotsMapping.get(vehicle.getRegistrationNumber());
        if (parkingLot == null) {
            return false;
        }

        return parkingLot.isParked(vehicle);
    }

    void unpark(Parkable vehicle) throws UnableToUnparkException, VehicleNotParkedException {
        ParkingLot parkingLotWhereParked = parkingLotsMapping.get(vehicle.getRegistrationNumber());
        if (parkingLotWhereParked == null) {
            throw new VehicleNotParkedException("Vehicle never parked");
        }

        try {
            parkingLotWhereParked.unpark(vehicle);
        } catch (CustomException e) {
            throw new UnableToUnparkException("Vehicle could not be unparked");
        }

        parkingLotsMapping.remove(vehicle.getRegistrationNumber());
    }

    boolean isUnparked(Parkable vehicle) {
        boolean isRemovedFromValet = isVehicleRemovedFromValetVehiclesList(parkingLotsMapping.get(vehicle.getRegistrationNumber()));
        boolean isRemovedFromParkingLot = isVehicleRemovedFromParkingLot(vehicle);

        return isRemovedFromValet && isRemovedFromParkingLot;
    }

    private boolean isVehicleRemovedFromParkingLot(Parkable vehicle) {
        boolean isVehicleRemovedFromParkingLot = true;
        for (ParkingLot parkingLot:registeredParkingLots) {
            if (parkingLot.isParked(vehicle)) {
                isVehicleRemovedFromParkingLot = false;
                break;
            }
        }
        return isVehicleRemovedFromParkingLot;
    }

    private boolean isVehicleRemovedFromValetVehiclesList(ParkingLot parkingLot) {
        return parkingLot == null;
    }
}
