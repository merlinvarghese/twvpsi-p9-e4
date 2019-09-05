package com.tw.vapasi;

import com.tw.vapasi.exceptions.CustomException;
import com.tw.vapasi.exceptions.ParkingFullException;
import com.tw.vapasi.exceptions.VehicleAlreadyParkedException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ParkingLotTest {
    @Nested
    class ParkingTests {
        @Test
        void expectVehicleParkedWhenSlotAvailable() {
            ParkingLot parkingLot = new ParkingLot(4);
            assertDoesNotThrow(() -> parkingLot.park(new Vehicle("i10")));
        }

        @Test
        void expectParkFailsWhenVehicleAlreadyParked() throws CustomException {
            ParkingLot parkingLot = new ParkingLot(3);
            parkingLot.park(new Vehicle("i10"));
            assertThrows(VehicleAlreadyParkedException.class, () -> parkingLot.park(new Vehicle("i10")));
        }

        @Test
        void expectParkFailsWhenSlotNotAvailable() throws CustomException {
            ParkingLot parkingLot = new ParkingLot(0);
            assertThrows(ParkingFullException.class, () -> parkingLot.park(new Vehicle("i10")));
        }
    }
}