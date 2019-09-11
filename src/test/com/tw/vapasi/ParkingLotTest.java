package com.tw.vapasi;

import com.tw.vapasi.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
            ParkingLot parkingLot;
            Parkable parkable;

            @BeforeEach
            void setUp() {
                parkingLot = new ParkingLot(4);
                parkable = mock(Parkable.class);
                when(parkable.getRegistrationNumber()).thenReturn("KA87KI1234");
            }

            @Test
            void expectTrueWhenVehicleParkedSuccessfully() throws CustomException {
                parkingLot.park(parkable);

                assertTrue(parkingLot.isParked(parkable));
            }

            @Test
            void expectFalseWhenVehicleParkedUnSuccessfully() throws CustomException {
                assertFalse(parkingLot.isParked(parkable));
            }
        }
    }

    @Nested
    class ParkingLotOwnerNotificationTests {
        ParkingLot parkingLot;
        Parkable parkable;

        @BeforeEach
        void setUp() {
            ParkingLotOwnerFourWheeler parkingLotOwnerFourWheeler = new ParkingLotOwnerFourWheeler();
            parkingLot = new ParkingLot(4, parkingLotOwnerFourWheeler);
            parkable = mock(Parkable.class);
            when(parkable.getRegistrationNumber()).thenReturn("KA87KI1234");
        }

        @Test
        void expectOwnerGetsNotifiedWhenParkingFull() throws CustomException {
            ParkingLotOwnerFourWheeler parkingLotOwnerFourWheeler = new ParkingLotOwnerFourWheeler();
            parkingLot = new ParkingLot(1, parkingLotOwnerFourWheeler);
            parkingLot.park(parkable);

            assertTrue(parkingLotOwnerFourWheeler.isParkingFull());
        }

        @Test
        void expectOwnerGetsNoNotificationWhenParkingNotFull() throws CustomException {
            ParkingLotOwnerFourWheeler parkingLotOwnerFourWheeler = new ParkingLotOwnerFourWheeler();
            parkingLot = new ParkingLot(2, parkingLotOwnerFourWheeler);
            parkingLot.park(parkable);

            assertThrows(ParkingFullNotificationNotReceivedException.class, () -> parkingLotOwnerFourWheeler.isParkingFull());
        }
    }

    @Nested
    class ParkingLotOwnerNotificationTestsUsingMock {
        ParkingLot parkingLot;
        Parkable parkable;
        ParkingLotOwner parkingLotOwner;

        @BeforeEach
        void setUp() {
            parkingLotOwner = mock(ParkingLotOwner.class);
            parkingLot = new ParkingLot(4, parkingLotOwner);
            parkable = mock(Parkable.class);
            when(parkable.getRegistrationNumber()).thenReturn("KA87KI1234");
        }

        @Test
        void expectOwnerGetsNotifiedWhenParkingFull() throws CustomException {
            parkingLot = new ParkingLot(1, parkingLotOwner);
            parkingLot.park(parkable);
            verify(parkingLotOwner, times(1)).notifyParkingFull();
        }

        @Test
        void expectOwnerGetsNoNotificationWhenParkingNotFull() throws CustomException {
            parkingLot = new ParkingLot(2, parkingLotOwner);
            parkingLot.park(parkable);
            verify(parkingLotOwner, never()).notifyParkingFull();
        }

        @Test
        void expectOwnerGetsNotifiedWhenParkingAvailable() throws CustomException {
            parkingLot = new ParkingLot(1, parkingLotOwner);
            parkingLot.park(parkable);
            parkingLot.unpark(parkable);
            verify(parkingLotOwner).notifyParkingAvailable();
        }

        @Test
        void expectOwnerNotNotifiedAboutParkingAvailableIfSlotsAvailableAreMoreThanOne() throws CustomException {
            parkingLot = new ParkingLot(3, parkingLotOwner);
            parkingLot.park(parkable);
            parkingLot.unpark(parkable);
            verify(parkingLotOwner, never()).notifyParkingAvailable();
        }

    }
}