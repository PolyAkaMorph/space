package com.space.service;

import com.space.controller.ShipOrder;
import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.StreamSupport;

@Service
public class ShipDataService {
    private static final Long DEFAULT_MIN_DATE = 26192235660000L; // Sat Jan 01 00:01:00 MSK 2800
    private static final Long DEFAULT_MAX_DATE = 33134734859999L; // Sat Jan 01 00:00:59 MSK 3020
    private static final Double DEFAULT_MIN_SPEED = 0.01D;
    private static final Double DEFAULT_MAX_SPEED = 0.99D;
    private static final Integer DEFAULT_MIN_CREW_SIZE = 0;
    private static final Integer DEFAULT_MAX_CREW_SIZE = 9999;
    private static final Double DEFAULT_MIN_RATING = 0D;
    private static final Double DEFAULT_MAX_RATING = 80D;
    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 3;


    @Autowired
    private ShipCrudRepository shipCrudRepository;

    @Transactional
    public Ship getAloneShip(Long id) {
        return shipCrudRepository.findById(id).orElse(null);
    }

    @Transactional
    public Page<Ship> getShipsList(String name,
                                   String planet,
                                   ShipType shipType,
                                   Long after, Long before,
                                   Boolean isUsed,
                                   Double minSpeed, Double maxSpeed,
                                   Integer minCrewSize, Integer maxCrewSize,
                                   Double minRating, Double maxRating,
                                   ShipOrder shipOrder, Integer pageNumber, Integer pageSize) {
        PageRequest pageRequest = PageRequest.of(setDefaultPageNumber(pageNumber), setDefaultPageSize(pageSize),
                Sort.Direction.ASC, setDefaultShipOrder(shipOrder).getFieldName());
        return shipCrudRepository.getShipsList(name, planet, shipType,
                setDefaultAfter(after), setDefaultBefore(before),
                isUsed,
                setDefaultMinSpeed(minSpeed), setDefaultMaxSpeed(maxSpeed),
                setDefaultMinCrewSize(minCrewSize), setDefaultMaxCrewSize(maxCrewSize),
                setDefaultMinRating(minRating), setDefaultMaxRating(maxRating), pageRequest);
    }

    @Transactional
    public Iterable<Ship> getShipsListStable(String name, String planet) {
        return shipCrudRepository.getShipsStable(name, planet);
    }

    @Transactional
    public long getShipsCount(String name,
                              String planet,
                              ShipType shipType,
                              Long after, Long before,
                              Boolean isUsed,
                              Double minSpeed, Double maxSpeed,
                              Integer minCrewSize, Integer maxCrewSize,
                              Double minRating, Double maxRating) {

        Page<Ship> ships = shipCrudRepository.getShipsList(name, planet, shipType,
                setDefaultAfter(after), setDefaultBefore(before),
                isUsed,
                setDefaultMinSpeed(minSpeed), setDefaultMaxSpeed(maxSpeed),
                setDefaultMinCrewSize(minCrewSize), setDefaultMaxCrewSize(maxCrewSize),
                setDefaultMinRating(minRating), setDefaultMaxRating(maxRating), PageRequest.of(DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE,
                        Sort.Direction.DESC, ShipOrder.ID.getFieldName()));
        return ships.getTotalElements();
        //return StreamSupport.stream(shipCrudRepository.findAll().spliterator(), false).count();
    }


    private static Integer setDefaultPageNumber(Integer pageNumber) {
        return null == pageNumber ? DEFAULT_PAGE_NUMBER : pageNumber;
    }

    private static Integer setDefaultPageSize(Integer pageSize) {
        return null == pageSize ? DEFAULT_PAGE_SIZE : pageSize;
    }

    private static Date setDefaultAfter(Long after) {
        //todo think about cases like minSpeed > defaultMaxSpeed
        if (null == after || DEFAULT_MIN_DATE > after) {
            return new Date(DEFAULT_MIN_DATE);
        } else {
            return new Date(after);
        }
    }

    private static Date setDefaultBefore(Long before) {
        if (null == before || DEFAULT_MAX_DATE < before) {
            return new Date(DEFAULT_MAX_DATE);
        } else {
            return new Date(before);
        }
    }

    private static Boolean setDefaultIsUsed(Boolean isUsed) {
        return null == isUsed ? false : isUsed;
    }

    private static Double setDefaultMinSpeed(Double speed) {
        //todo math round here to .99
        if (null == speed || speed < DEFAULT_MIN_SPEED) {
            return DEFAULT_MIN_SPEED;
        } else {
            return speed;
        }
    }

    private static Double setDefaultMaxSpeed(Double speed) {
        //todo math round here to .99
        if (null == speed || speed > DEFAULT_MAX_SPEED) {
            return DEFAULT_MAX_SPEED;
        } else {
            return speed;
        }
    }

    private static Integer setDefaultMinCrewSize(Integer crewSize) {
        if (null == crewSize || crewSize < DEFAULT_MIN_CREW_SIZE) {
            return DEFAULT_MIN_CREW_SIZE;
        } else {
            return crewSize;
        }
    }

    private static Integer setDefaultMaxCrewSize(Integer crewSize) {
        if (null == crewSize || crewSize > DEFAULT_MAX_CREW_SIZE) {
            return DEFAULT_MAX_CREW_SIZE;
        } else {
            return crewSize;
        }
    }

    private static Double setDefaultMinRating(Double rating) {
        //todo math round here to .99
        if (null == rating || rating < DEFAULT_MIN_RATING) {
            return DEFAULT_MIN_RATING;
        } else {
            return rating;
        }
    }

    private static Double setDefaultMaxRating(Double rating) {
        //todo math round here to .99
        if (null == rating || rating > DEFAULT_MAX_RATING) {
            return DEFAULT_MAX_RATING;
        } else {
            return rating;
        }
    }

    private static ShipOrder setDefaultShipOrder(ShipOrder shipOrder) {
        return null == shipOrder ? ShipOrder.ID : shipOrder;
    }

}






