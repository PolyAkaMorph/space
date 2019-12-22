package com.space.service;

import com.space.model.Ship;
import com.space.repository.ShipCrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ShipDataService {

    @Autowired
    private ShipCrudRepository shipCrudRepository;

    @Transactional
    public Ship getAloneShip(Long id) {
        Optional<Ship> optional = shipCrudRepository.findById(id);
        return optional.orElse(null);
    }
}






