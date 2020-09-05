package com.djelaili.parking;


import org.junit.Assert;
import org.junit.Test;

public class ParkingTest {
    private final Parking parking = Parking.builder()
            .parkingType(CarType.CAR_SEDAN, ParkingType.builder()
                    .initSlotsSize(2)
                    .billingMethod(h -> h * 5)
                    .build())
            .parkingType(CarType.CAR_20KW, ParkingType.builder()
                    .initSlotsSize(3)
                    .billingMethod(h -> 30 + (h * 5))
                    .build())
            .build();

    @Test
    public void addCar() {
        long startDate = System.currentTimeMillis();
        boolean ok = parking.tryAddCar(CarType.CAR_SEDAN, "001", startDate);
        Assert.assertTrue(ok);
    }

    @Test
    public void leaveAndBill() {
        long startDate = System.currentTimeMillis();
        String carId = "001";
        parking.tryAddCar(CarType.CAR_SEDAN, carId, startDate);

        long endDate = startDate + (3600 * 3);
        int mount = parking.leaveAndBill(CarType.CAR_SEDAN, carId, endDate);
        Assert.assertEquals(mount, 15);
    }

    @Test
    public void leaveAndBillWrongCarId() {
        try {
            parking.leaveAndBill(CarType.CAR_SEDAN, "wrongId", 0L);
            Assert.fail("Missing exception");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), "Cannot find the car : wrongId");
        }
    }

    @Test
    public void addWrongCarType() {
        try {
            parking.tryAddCar(CarType.CAR_50KW, "001", 0L);
            Assert.fail("Missing exception");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), "Cannot find the parking for the car type : CAR_50KW");
        }
    }

    @Test
    public void addExistingCar() {
        parking.tryAddCar(CarType.CAR_SEDAN, "001", 0L);
        try {
            parking.tryAddCar(CarType.CAR_SEDAN, "001", 0L);
            Assert.fail("Missing exception");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), "The car : 001 is already in the park");
        }
    }

    @Test
    public void refuseAddingCar() {
        Assert.assertTrue(parking.tryAddCar(CarType.CAR_SEDAN, "001", 0L));
        Assert.assertTrue(parking.tryAddCar(CarType.CAR_SEDAN, "002", 0L));
        Assert.assertFalse(parking.tryAddCar(CarType.CAR_SEDAN, "003", 0L));
    }
}