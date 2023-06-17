package com.driver.services.impl;

import com.driver.model.ParkingLot;
import com.driver.model.Spot;
import com.driver.model.SpotType;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.SpotRepository;
import com.driver.services.ParkingLotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.driver.model.SpotType.*;

@Service
public class ParkingLotServiceImpl implements ParkingLotService {
    @Autowired
    ParkingLotRepository parkingLotRepository1;
    @Autowired
    SpotRepository spotRepository1;
    @Override
    public ParkingLot addParkingLot(String name, String address) {
        ParkingLot parkingLot = new ParkingLot();
        parkingLot.setName(name);
        parkingLot.setAddress(address);
        parkingLotRepository1.save(parkingLot);
        return parkingLot;
    }

    @Override
    public Spot addSpot(int parkingLotId, Integer numberOfWheels, Integer pricePerHour) {
        Spot spot = new Spot();
        ParkingLot parkingLot =  parkingLotRepository1.findById(parkingLotId).get();
        if(numberOfWheels <= 2) spot.setSpotType(SpotType.TWO_WHEELER);
        if(numberOfWheels <= 4)spot.setSpotType(SpotType.FOUR_WHEELER);
        else spot.setSpotType(SpotType.OTHERS);
        spot.setPricePerHour(pricePerHour);
        spot.setOccupied(false);
        spotRepository1.save(spot);
        parkingLot.getSpotList().add(spot);
        parkingLotRepository1.save(parkingLot);
        return spot;
    }

    @Override
    public void deleteSpot(int spotId) {
        spotRepository1.deleteById(spotId);
    }

    @Override
    public Spot updateSpot(int parkingLotId, int spotId, int pricePerHour) {
        ParkingLot parkingLot=parkingLotRepository1.findById(parkingLotId).get();
        Spot updateSpot=null;
        for (Spot spot:parkingLot.getSpotList())
        {
            if(spot.getId()==spotId)
                updateSpot=spot;
        }
        updateSpot.setPricePerHour(pricePerHour);
        spotRepository1.save(updateSpot);
        return updateSpot;
    }

    @Override
    public void deleteParkingLot(int parkingLotId) {
//        ParkingLot parkingLot = parkingLotRepository1.findById(parkingLotId).get();
//        List<Spot> spotList = parkingLot.getSpotList();
//        parkingLotRepository1.deleteById(parkingLotId);
//        for(Spot spot : spotList){
//            spotRepository1.deleteById(spot.getId());
//        }
//        parkingLotRepository1.save(parkingLot);
        parkingLotRepository1.deleteById(parkingLotId);
    }
}
