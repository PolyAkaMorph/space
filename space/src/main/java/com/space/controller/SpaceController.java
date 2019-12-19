package com.space.controller;

import com.space.model.ShipType;
import com.space.model.SpaceShip;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpaceController {
    @RequestMapping(value ="/rest/ships", method = RequestMethod.GET)
    public SpaceShip getShip(@RequestParam(value="id") Integer id) {
        return new SpaceShip(2L, "First one", "Earth", ShipType.MERCHANT, System.currentTimeMillis(),false,50d, 3, 100d);
    }

}
