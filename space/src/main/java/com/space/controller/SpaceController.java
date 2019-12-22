package com.space.controller;

import com.space.model.Ship;
import com.space.service.ShipDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SpaceController {
    @Autowired
    private ShipDataService shipDataService;


    @RequestMapping(value = "/rest/ships/{id}", method = RequestMethod.GET)
    public Ship getShip(@PathVariable(value = "id") Long id) {
        return shipDataService.getAloneShip(id);
    }

    @RequestMapping(value = "rest/ships", method = RequestMethod.GET)
    public Iterable<Ship> getAllShips(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "planet", required = false) String planet) {
        return shipDataService.getShipsList(name);
    }


    @RequestMapping(value = "rest/ships/count", method = RequestMethod.GET)
    public Long getShipsCount() {
        return shipDataService.getShipsCount();
    }
}