package com.djelaili.parking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Data
@Builder
@AllArgsConstructor
public class ParkingType {

    private final int initSlotsSize;
    private final Function<Integer, Integer> billingMethod;
    private final Map<String, Long> carStartDate = new HashMap<>();

    public synchronized boolean addCar(String carId, long startDate) {
        if (carStartDate.containsKey(carId)) {
            throw new IllegalArgumentException("The car : " + carId + " is already in the park");
        }
        if (carStartDate.size() < initSlotsSize) {
            carStartDate.put(carId, startDate);
            return true;
        }
        return false;
    }

    public synchronized int leaveAndBill(String carId, long endDate) {
        if (!carStartDate.containsKey(carId)) {
            throw new IllegalArgumentException("Cannot find the car : " + carId);
        }
        Long startDate = carStartDate.get(carId);
        int hours = (int) ((endDate - startDate) / 3600);
        return billingMethod.apply(hours);
    }
}
