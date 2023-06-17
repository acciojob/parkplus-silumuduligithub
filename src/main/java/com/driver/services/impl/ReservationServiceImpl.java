package com.driver.services.impl;

import com.driver.Exceptions.ParkingLotIsNotFound;
import com.driver.Exceptions.UserIsNotFound;
import com.driver.model.*;
import com.driver.repository.ParkingLotRepository;
import com.driver.repository.ReservationRepository;
import com.driver.repository.SpotRepository;
import com.driver.repository.UserRepository;
import com.driver.services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.driver.model.SpotType.FOUR_WHEELER;
import static com.driver.model.SpotType.TWO_WHEELER;
import static org.springframework.util.ClassUtils.isPresent;

@Service
public class ReservationServiceImpl implements ReservationService {
    @Autowired
    UserRepository userRepository3;
    @Autowired
    SpotRepository spotRepository3;
    @Autowired
    ReservationRepository reservationRepository3;
    @Autowired
    ParkingLotRepository parkingLotRepository3;
    @Override
    public Reservation reserveSpot(Integer userId, Integer parkingLotId, Integer timeInHours, Integer numberOfWheels) throws Exception{
        Optional<ParkingLot> parkingLotOptional = parkingLotRepository3.findById(parkingLotId);
       if(parkingLotOptional.isPresent() == false) {
            throw new ParkingLotIsNotFound("Cannot make reservation");
        }
       Optional<User> userOptional = userRepository3.findById(userId);
       if(userOptional.isPresent() == false){
           throw new UserIsNotFound("Cannot make reservation");
       }
       Spot spot = null;
        ParkingLot parkingLot = parkingLotOptional.get();
        List<Spot> spotList = parkingLot.getSpotList();
        int spotPricePerHour = Integer.MAX_VALUE;
        for(Spot spot1 : spotList){
            SpotType spotType = spot1.getSpotType();
            int wheels = 0;
            if(spotType.equals(TWO_WHEELER))wheels = 2;
            else if(spotType.equals(FOUR_WHEELER))wheels = 4;
            else wheels = Integer.MAX_VALUE;
            if(wheels >= numberOfWheels){
                if(spotPricePerHour > spot1.getPricePerHour()){
                    spot = spot1;
                }
            }
        }
        Reservation reservation = new Reservation();
        reservation.setNumberOfHours(timeInHours);
        reservation.setSpot(spot);
        reservationRepository3.save(reservation);
        return reservation;
    }
}
