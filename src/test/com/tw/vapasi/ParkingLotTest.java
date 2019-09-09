package com.tw.vapasi;

import com.tw.vapasi.exceptions.CustomException;
import com.tw.vapasi.exceptions.ParkingFullException;
import com.tw.vapasi.exceptions.VehicleAlreadyParkedException;
import com.tw.vapasi.exceptions.VehicleNotParkedException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("ALL")
class ParkingLotTest {
    @Nested
    class ActualObjectTests {
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

        @Nested
        class UnparkingTests {
            @Test
            void expectVehicleUnParksWhenAlreadyParked() throws CustomException {
                ParkingLot parkingLot = new ParkingLot(4);
                parkingLot.park(new Vehicle("i10"));
                assertDoesNotThrow(() -> parkingLot.unpark(new Vehicle("i10")));
            }

            @Test
            void expectVehicleUnParkFailsWhenNotAlreadyParked() {
                ParkingLot parkingLot = new ParkingLot(4);
                assertThrows(VehicleNotParkedException.class, () -> parkingLot.unpark(new Vehicle("i10")));
            }
        }

        @Nested
        class ParkedStatusTests {
            @Test
            void expectTrueWhenVehicleParkedSuccessfully() throws CustomException {
                ParkingLot parkingLot = new ParkingLot(4);
                Vehicle i10Car = new Vehicle("i10");

                parkingLot.park(i10Car);

                assertTrue(parkingLot.isParked(i10Car));
            }

            @Test
            void expectFalseWhenVehicleNotParkedSuccessfully() throws CustomException {
                ParkingLot parkingLot = new ParkingLot(4);
                Vehicle i10Car = new Vehicle("i10");

                assertFalse(parkingLot.isParked(i10Car));
            }
        }
    }

    @Nested
    class MockObjectsTests {
        @Nested
        class ParkTests {
            @Test
            void expectVehicleParkedWhenSlotAvailable() throws CustomException {
                ParkingLot parkingLot = new ParkingLot(4);
                Parkable cari10 = mock(Parkable.class);
                when(cari10.getRegistrationNumber()).thenReturn("KA87KI1234");

                assertDoesNotThrow(() -> parkingLot.park(cari10));
            }

            @Test
            void expectParkFailsWhenVehicleAlreadyParked() throws CustomException {
                ParkingLot parkingLot = new ParkingLot(3);
                Parkable cari10 = mock(Parkable.class);
                when(cari10.getRegistrationNumber()).thenReturn("KA87KI1234");
                parkingLot.park(cari10);

                assertThrows(VehicleAlreadyParkedException.class, () -> parkingLot.park(cari10));
            }

            @Test
            void expectParkFailsWhenSlotNotAvailable() throws CustomException {
                ParkingLot parkingLot = new ParkingLot(0);
                Parkable cari10 = mock(Parkable.class);
                when(cari10.getRegistrationNumber()).thenReturn("KA87KI1234");

                assertThrows(ParkingFullException.class, () -> parkingLot.park(cari10));
            }
        }

        @Nested
        class UnparkTests {
            @Test
            void expectVehicleUnParksWhenAlreadyParked() throws CustomException {
                ParkingLot parkingLot = new ParkingLot(4);
                Parkable cari10 = mock(Parkable.class);
                when(cari10.getRegistrationNumber()).thenReturn("KA87KI1234");
                parkingLot.park(cari10);

                assertDoesNotThrow(() -> parkingLot.unpark(cari10));
            }
        }

        @Nested
        class ParkedStatusTests {
            @Test
            void expectTrueWhenVehicleParkedSuccessfully() throws CustomException {
                ParkingLot parkingLot = new ParkingLot(4);
                Parkable cari10 = mock(Parkable.class);
                when(cari10.getRegistrationNumber()).thenReturn("KA87KI1234");
                parkingLot.park(cari10);

                assertTrue(parkingLot.isParked(cari10));
            }
        }
    }
}