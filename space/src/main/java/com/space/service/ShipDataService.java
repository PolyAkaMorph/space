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
    private static final Long minDate = 26192235660000L;
    private static final Long maxDate = 33134734859999L;

    @Autowired
    private ShipCrudRepository shipCrudRepository;

    @Transactional
    public Ship getAloneShip(Long id) {
        return shipCrudRepository.findById(id).orElse(null);
    }

    @Transactional
    public Iterable<Ship> getShipsList(String name, String planet, ShipType shipType, Long after, Long before) {
        return shipCrudRepository.getShipsList(name, planet, shipType.toString(),setDefaultAfter(after), setDefaultBefore(before));
    }

    @Transactional
    public Iterable<Ship> getShipsListStable(String name, String planet) {
        return shipCrudRepository.getShipsStable(name,planet);
    }

    @Transactional
    public long getShipsCount() {
        return StreamSupport.stream(shipCrudRepository.findAll().spliterator(), false).count();
    }

    private static Date setDefaultAfter(Long after) {
        if (null == after || minDate > after) {
            return new Date(minDate);
        } else {
            return new Date(after);
        }
    }

    private static Date setDefaultBefore(Long before) {
        //todo - truncate time to end of year
        if (null == before || maxDate < before) {
            return new Date(minDate);
        } else {
            return new Date(before);
        }
    }

}






