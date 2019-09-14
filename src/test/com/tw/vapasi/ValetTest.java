package com.tw.vapasi;

import com.tw.vapasi.exceptions.CustomException;
import com.tw.vapasi.exceptions.VehicleNotParkedException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ValetTest {

    @Test
    void expectValetToParkWhenParkingLotsAvailable() throws CustomException {
        Set<ParkingLot> parkingLots = new HashSet<>();
        parkingLots.add(new ParkingLot(1));
        parkingLots.add(new ParkingLot(1));

        Valet valet = new Valet(parkingLots);
        Parkable cari10 = mock(Parkable.class);
        when(cari10.getRegistrationNumber()).thenReturn("cari10");

        valet.park(cari10);

        assertTrue(valet.isParked(cari10));
    }

    @Test
    void expectValetFailsToParkWhenNoParkingLotsAvailable() throws CustomException {
        Set<ParkingLot> parkingLots = new HashSet<>();
        parkingLots.add(new ParkingLot(1));

        Valet valet = new Valet(parkingLots);
        Parkable cari10 = mock(Parkable.class);
        Parkable cari20 = mock(Parkable.class);
        when(cari10.getRegistrationNumber()).thenReturn("cari10");
        when(cari20.getRegistrationNumber()).thenReturn("cari20");
        valet.park(cari10);

        assertThrows(NoParkingLotsAvailableException.class, () -> valet.park(cari20));
        assertFalse(valet.isParked(cari20));
    }
}
