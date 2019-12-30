package com.space.service;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.stream.StreamSupport;

@Service
public class ShipDataService {
    private static final Long minDate = 26192235660000L; // Sat Jan 01 00:01:00 MSK 2800
    private static final Long maxDate = 33134734859999L; // Sat Jan 01 00:00:59 MSK 3020
    private static final Double minSpeed = 0.01D;
    private static final Double maxSpeed = 0.99D;
    private static final Integer minCrewSize = 0;
    private static final Integer maxCrewSize = 9999;
    private static final Double minRating = 0D;
    private static final Double maxRating = 80D;




    @Autowired
    private ShipCrudRepository shipCrudRepository;

    @Transactional
    public Ship getAloneShip(Long id) {
        return shipCrudRepository.findById(id).orElse(null);
    }

    @Transactional
    public Iterable<Ship> getShipsList(String name,
                                       String planet,
                                       ShipType shipType,
                                       Long after, Long before,
                                       Boolean isUsed,
                                       Double minSpeed, Double maxSpeed,
                                       Integer minCrewSize, Integer maxCrewSize,
                                       Double minRating, Double maxRating) {
        return shipCrudRepository.getShipsList(name, planet, shipType,
                setDefaultAfter(after), setDefaultBefore(before),
                setDefaultIsUsed(isUsed),
                setDefaultMinSpeed(minSpeed), setDefaultMaxSpeed(maxSpeed),
                setDefaultMinCrewSize(minCrewSize), setDefaultMaxCrewSize(maxCrewSize),
                setDefaultMinRating(minRating), setDefaultMaxRating(maxRating));
    }

    @Transactional
    public Iterable<Ship> getShipsListStable(String name, String planet) {
        return shipCrudRepository.getShipsStable(name, planet);
    }

    @Transactional
    public long getShipsCount() {
        return StreamSupport.stream(shipCrudRepository.findAll().spliterator(), false).count();
    }


    private static Date setDefaultAfter(Long after) {
        //todo think about cases like minSpeed > defaultMaxSpeed
        if (null == after || minDate > after) {
            return new Date(minDate);
        } else {
            return new Date(after);
        }
    }

    private static Date setDefaultBefore(Long before) {
        if (null == before || maxDate < before) {
            return new Date(maxDate);
        } else {
            return new Date(before);
        }
    }

    private static Boolean setDefaultIsUsed(Boolean isUsed) {
        return null == isUsed ? false : isUsed;
    }

    private static Double setDefaultMinSpeed(Double speed) {
        //todo math round here to .99
        if (null == speed || speed < minSpeed) {
            return minSpeed;
        } else {
            return speed;
        }
    }

    private static Double setDefaultMaxSpeed(Double speed) {
        //todo math round here to .99
        if (null == speed || speed > maxSpeed) {
            return maxSpeed;
        } else {
            return speed;
        }
    }

    private static Integer setDefaultMinCrewSize(Integer crewSize) {
        if (null == crewSize || crewSize < minCrewSize) {
            return minCrewSize;
        } else {
            return crewSize;
        }
    }

    private static Integer setDefaultMaxCrewSize(Integer crewSize) {
        if (null == crewSize || crewSize > maxCrewSize) {
            return maxCrewSize;
        } else {
            return crewSize;
        }
    }

    private static Double setDefaultMinRating(Double rating) {
        //todo math round here to .99
        if (null == rating || rating < minRating) {
            return minRating;
        } else {
            return rating;
        }
    }

    private static Double setDefaultMaxRating(Double rating) {
        //todo math round here to .99
        if (null == rating || rating > maxRating) {
            return maxRating;
        } else {
            return rating;
        }
    }
}






