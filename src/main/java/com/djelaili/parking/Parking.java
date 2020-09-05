package com.djelaili.parking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Singular;

import java.util.Map;

@Builder
@AllArgsConstructor
public class Parking {
    @Singular
    private final Map<CarType, ParkingType> parkingTypes;

    public boolean tryAddCar(CarType carType, String carId, long startDate) {
        return getParkingTypeOrThrow(carType).addCar(carId, startDate);
    }

    public int leaveAndBill(CarType carType, String carId, long endDate) {
        return getParkingTypeOrThrow(carType).leaveAndBill(carId, endDate);
    }

    private ParkingType getParkingTypeOrThrow(CarType carType) {
        ParkingType parkingType = parkingTypes.get(carType);
        if (parkingType == null) {
            throw new IllegalArgumentException("Cannot find the parking for the car type : " + carType);
        }
        return parkingType;
    }

}
