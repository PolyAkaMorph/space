package com.space.service;

import com.space.controller.ShipOrder;
import com.space.controller.response_statuses.BadRequestException;
import com.space.controller.response_statuses.ResourceNotFoundException;
import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.repository.ShipCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;

@Service
public class ShipDataService {
    private static final Long DEFAULT_MIN_DATE = 26192235660000L; // Sat Jan 01 00:01:00 MSK 2800
    private static final Long DEFAULT_MAX_DATE = 33134734859999L; // Sat Jan 01 00:00:59 MSK 3020
    private static final Long DEFAULT_DATE = 33103198800000L; //
    private static final Double DEFAULT_MIN_SPEED = 0.01D;
    private static final Double DEFAULT_MAX_SPEED = 0.99D;
    private static final Integer DEFAULT_MIN_CREW_SIZE = 0;
    private static final Integer DEFAULT_MAX_CREW_SIZE = 9999;
    private static final Double DEFAULT_MIN_RATING = 0D;
    private static final Double DEFAULT_MAX_RATING = 80D;
    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 3;

    private static final Double ratingPercent = 80D;


    @Autowired
    private ShipCrudRepository shipCrudRepository;

    @PersistenceContext
    EntityManager em;

    @Transactional
    public Ship updateShip(Long id, Ship ship) {
        if (null == id) {
            throw new BadRequestException();
        }
        Ship newShip = getAloneShip(id);
        String name = ship.getName();
        String planet = ship.getPlanet();
        Date prodDate = ship.getProdDate();
        Boolean isUsed = ship.getIsUsed();
        ShipType shipType = ship.getShipType();
        Double speed = ship.getSpeed();
        Integer crewSize = ship.getCrewSize();

        if (null == name && null == planet && null == shipType && null == speed && null == crewSize && null == prodDate) {
            return newShip;
        }
        if (null != name && (name.length() > 50 || name.isEmpty())) throw new BadRequestException();
        if (null != planet && (planet.length() > 50 || planet.isEmpty())) throw new BadRequestException();
        if (null != prodDate && (prodDate.getTime() > DEFAULT_MAX_DATE || prodDate.getTime() < DEFAULT_MIN_DATE)) throw new BadRequestException();
        if (null != speed && (speed > DEFAULT_MAX_SPEED || speed < DEFAULT_MIN_SPEED)) throw new BadRequestException();
        if (null != crewSize && (crewSize > DEFAULT_MAX_CREW_SIZE || crewSize < DEFAULT_MIN_CREW_SIZE)) throw new BadRequestException();

        if (null != name) newShip.setName(name);
        if (null != planet) newShip.setPlanet(planet);
        if (null != speed) newShip.setSpeed(speed);
        if (null != shipType) newShip.setShipType(shipType);
        if (null != prodDate) newShip.setProdDate(prodDate);
        if (null != isUsed) newShip.setIsUsed(isUsed);
        if (null != crewSize) newShip.setCrewSize(crewSize);
        Double rating = calcRating(newShip.getSpeed(),newShip.getIsUsed(),newShip.getProdDate().getTime());
        newShip.setRating(rating);
        em.merge(newShip);
        em.flush();

        return newShip;
    }

    private void checkParams(String name, String planet, Long prodDate, Double speed, Integer crewSize) {
        if (name.length() > 50 || name.isEmpty() || planet.length() > 50 || planet.isEmpty()) {
            throw new BadRequestException();
        }
        if (prodDate > DEFAULT_MAX_DATE || prodDate < DEFAULT_MIN_DATE ||
                speed > DEFAULT_MAX_SPEED || speed < DEFAULT_MIN_SPEED ||
                crewSize > DEFAULT_MAX_CREW_SIZE || crewSize < DEFAULT_MIN_CREW_SIZE) {
            throw new BadRequestException();
        }
    }

    @Transactional
    public void deleteShip(Long id) {
        Ship ship = getAloneShip(id);
        em.remove(ship);
        em.flush();
    }

    @Transactional
    public Ship createShip(Ship ship) {
        if (null == ship || null == ship.getProdDate()) {
            throw new BadRequestException();
        }
        String name = ship.getName();
        String planet = ship.getPlanet();
        ShipType shipType = ship.getShipType();
        Long prodDate = ship.getProdDate().getTime();
        Boolean isUsed = ship.getIsUsed();
        Double speed = ship.getSpeed();
        Integer crewSize = ship.getCrewSize();
        if (null == name || null == planet || null == shipType || null == speed || null == crewSize) {
            throw new BadRequestException();
        }
        checkParams(name, planet, prodDate, speed, crewSize);
        isUsed = null == isUsed ? false : isUsed;
        Double rating = calcRating(speed, isUsed, prodDate);
        ship.setRating(rating);
        ship.setIsUsed(isUsed);
        em.persist(ship);
        em.flush();

        return ship;
    }

    @Transactional
    public Ship getAloneShip(Long id) {
        if (!isItInteger(id)) {
            throw new BadRequestException();
        }
        return shipCrudRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
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
    }

    private static Boolean isItInteger(Long aLong) {
        return null != aLong && aLong > 0 && aLong == (int) aLong.longValue();
    }

    private static Double calcRating(Double speed, Boolean isUsed, Long prodDate) {
        Double k = isUsed ? 0.5D : 1D;
        Double y0 = getYear(DEFAULT_DATE);
        Double y1 = getYear(prodDate);
        Double rating = ratingPercent * speed * k / (y0 - y1 + 1);
        return round(rating,2);
    }

    private static Integer setDefaultPageNumber(Integer pageNumber) {
        return null == pageNumber ? DEFAULT_PAGE_NUMBER : pageNumber;
    }

    public static Double round(Double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
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
            return truncateDateToYear(new Date(before));
        }
    }

    private static Date truncateDateToYear(Date date) {
        Calendar cal = Calendar.getInstance(); // locale-specific
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return new Date(cal.getTimeInMillis() - 1);
    }

    private static Double getYear(Long date) {
        Calendar cal = Calendar.getInstance(); // locale-specific
        cal.setTime(new Date(date));
        return (double) cal.get(Calendar.YEAR);
    }

    private static Double setDefaultMinSpeed(Double speed) {
        if (null == speed || speed < DEFAULT_MIN_SPEED) {
            return DEFAULT_MIN_SPEED;
        } else {
            return round(speed,2);
        }
    }

    private static Double setDefaultMaxSpeed(Double speed) {
        if (null == speed || speed > DEFAULT_MAX_SPEED) {
            return DEFAULT_MAX_SPEED;
        } else {
            return round(speed,2);
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
        if (null == rating || rating < DEFAULT_MIN_RATING) {
            return DEFAULT_MIN_RATING;
        } else {
            return round(rating,2);
        }
    }

    private static Double setDefaultMaxRating(Double rating) {
        if (null == rating || rating > DEFAULT_MAX_RATING) {
            return DEFAULT_MAX_RATING;
        } else {
            return round(rating,2);
        }
    }

    private static ShipOrder setDefaultShipOrder(ShipOrder shipOrder) {
        return null == shipOrder ? ShipOrder.ID : shipOrder;
    }

}






