package com.space.controller;

import com.space.model.Ship;
import com.space.model.ShipType;
import com.space.service.ShipDataService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
public class SpaceController {
    private Logger logger = LoggerFactory.getLogger(SpaceController.class);

    @Autowired
    private ShipDataService shipDataService;

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Ship not found!")
    public class ResourceNotFoundException extends RuntimeException {}

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Id not valid!")
    public class BadRequestException extends RuntimeException {}


    @RequestMapping(value = "/rest/ships/{id}", method = RequestMethod.GET)
    public Ship getShip(@PathVariable(value = "id") Long id) {
        logger.info("got one ship");
        if (null == id || id <= 0 || id != (int) id.longValue() ) throw new BadRequestException();
        Ship ship = shipDataService.getAloneShip(id);
        if (null == ship) throw new ResourceNotFoundException();
        return shipDataService.getAloneShip(id);
    }

    @RequestMapping(value = "rest/ships", method = RequestMethod.GET)
    public Iterable<Ship> getShipsList(@RequestParam(value = "name", required = false) String name,
                                       @RequestParam(value = "planet", required = false) String planet,
                                       @RequestParam(value = "shipType", required = false) ShipType shipType,
                                       @RequestParam(value = "after", required = false) Long after,
                                       @RequestParam(value = "before", required = false) Long before,
                                       @RequestParam(value = "isUsed", required = false) Boolean isUsed,
                                       @RequestParam(value = "minSpeed", required = false) Double minSpeed,
                                       @RequestParam(value = "maxSpeed", required = false) Double maxSpeed,
                                       @RequestParam(value = "minCrewSize", required = false) Integer minCrewSize,
                                       @RequestParam(value = "maxCrewSize", required = false) Integer maxCrewSize,
                                       @RequestParam(value = "minRating", required = false) Double minRating,
                                       @RequestParam(value = "maxRating", required = false) Double maxRating,
                                       @RequestParam(value = "order", required = false) ShipOrder shipOrder,
                                       @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                       @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        logger.info("got many ships");
        return shipDataService.getShipsList(name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating,
                shipOrder, pageNumber, pageSize).getContent();
    }


    @RequestMapping(value = "rest/ships/count", method = RequestMethod.GET)
    public Long getShipsCount(@RequestParam(value = "name", required = false) String name,
                              @RequestParam(value = "planet", required = false) String planet,
                              @RequestParam(value = "shipType", required = false) ShipType shipType,
                              @RequestParam(value = "after", required = false) Long after,
                              @RequestParam(value = "before", required = false) Long before,
                              @RequestParam(value = "isUsed", required = false) Boolean isUsed,
                              @RequestParam(value = "minSpeed", required = false) Double minSpeed,
                              @RequestParam(value = "maxSpeed", required = false) Double maxSpeed,
                              @RequestParam(value = "minCrewSize", required = false) Integer minCrewSize,
                              @RequestParam(value = "maxCrewSize", required = false) Integer maxCrewSize,
                              @RequestParam(value = "minRating", required = false) Double minRating,
                              @RequestParam(value = "maxRating", required = false) Double maxRating,
                              @RequestParam(value = "order", required = false) ShipOrder shipOrder,
                              @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                              @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        return shipDataService.getShipsCount(name, planet, shipType, after, before, isUsed, minSpeed, maxSpeed, minCrewSize, maxCrewSize, minRating, maxRating);
    }
}