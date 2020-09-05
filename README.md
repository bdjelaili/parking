# Toll Parking Library #

Toll Parking  is the API​that meets the following requirements​ :
A toll parking contains multiple parking slots of different types :

- The standard parking slots for sedan cars (gasoline-powered)
- Parking slots with 20kw power supply for electric cars
- Parking slots with 50kw power supply for electric cars

20kw electric cars cannot use 50kw power supplies and vice-versa.

Every Parking is free to implement is own pricing policy :
- Some only bills their customer for each hour spent in the parking (nb hours * hour price)
- Some other bill a fixed amount + each hour spent in the parking (fixed amount + nb hours * hour
price)

Cars of all types come in and out randomly, the API must :
- Send them to the right parking slot of refuse them if there is no slot (of the right type) left.
- Mark the parking slot as Free when the car leaves it
- Bill the customer when the car leaves

## How Build
It's necessary to have maven and jdk 8 to build from sources, run mvn install

## How to use
```
// create Parking instance
Parking parking = Parking.builder()
            // add parking type with slots size and billing method
            .parkingType(CarType.CAR_SEDAN, ParkingType.builder()
                    .initSlotsSize(2)
                    .billingMethod(h -> h * 5)
                    .build())
            .parkingType(CarType.CAR_20KW, ParkingType.builder()
                    .initSlotsSize(3)
                    .billingMethod(h -> 30 + (h * 5))
                    .build())
            .build();

// add car
String carId = "001";
long startDate = System.currentTimeMillis();
parking.tryAddCar(CarType.CAR_SEDAN, carId, startDate);

// leave and bill after 3 hours
long endDate = startDate + (3600 * 3);
int price = parking.leaveAndBill(CarType.CAR_SEDAN, carId, endDate);

```