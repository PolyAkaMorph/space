package com.space.service;

import com.space.model.Ship;
import com.space.repository.ShipCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.StreamSupport;

@Service
public class ShipDataService {

    @Autowired
    private ShipCrudRepository shipCrudRepository;

    @Transactional
    public Ship getAloneShip(Long id) {
        return shipCrudRepository.findById(id).orElse(null);
    }

    @Transactional
    public Iterable<Ship> getShipsList(String name) {
        return shipCrudRepository.findShips(name);
    }

    @Transactional
    public long getShipsCount() {
        return StreamSupport.stream(shipCrudRepository.findAll().spliterator(), false).count();
    }
}






