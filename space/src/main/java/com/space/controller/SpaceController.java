package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipDataService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class SpaceController {
    private Logger logger = LoggerFactory.getLogger(SpaceController.class);

    @Autowired
    private ShipDataService shipDataService;


    @RequestMapping(value = "/rest/ships/{id}", method = RequestMethod.GET)
    public Ship getShip(@PathVariable(value = "id") Long id) {
        logger.info("got one ship");
        return shipDataService.getAloneShip(id);
    }

    @RequestMapping(value = "rest/ships", method = RequestMethod.GET)
    public Iterable<Ship> getShipsList(@RequestParam(value = "name", required = false) String name,
                                       @RequestParam(value = "planet", required = false) String planet,
                                       @RequestParam(value = "shipType", required = false) ShipType shipType,
                                       @RequestParam(value = "after", required = false) Long after,
                                       @RequestParam(value = "before", required = false) Long before) {
        logger.info("got many ships");
        return shipDataService.getShipsListStable(name, planet);
        //return shipDataService.getShipsList(name,planet, shipType,after,before);
    }


    @RequestMapping(value = "rest/ships/count", method = RequestMethod.GET)
    public Long getShipsCount() {
        return shipDataService.getShipsCount();
    }
}