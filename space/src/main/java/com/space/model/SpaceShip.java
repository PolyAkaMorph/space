package com.space.model;

public class SpaceShip {
    private Long id;
    private String name;
    private String planet;
    private ShipType shipType;
    private Long prodDate;
    private Boolean isUsed;
    private Double speed;
    private Integer crewSize;
    private Double rating;

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getPlanet() {
        return planet;
    }
    public ShipType getShipType() {
        return shipType;
    }
    public Long getProdDate() {
        return prodDate;
    }
    public Boolean isUsed() {
        return isUsed;
    }
    public Double getSpeed() {
        return speed;
    }
    public Integer getCrewSize() {
        return crewSize;
    }
    public Double getRating() {
        return rating;
    }

    public SpaceShip(Long id, String name, String planet, ShipType shipType, Long prodDate, Boolean isUsed, Double speed, Integer crewSize, Double rating) {
        this.id = id;
        this.name = name;
        this.planet = planet;
        this.shipType = shipType;
        this.prodDate = prodDate;
        this.isUsed = isUsed;
        this.speed = speed;
        this.crewSize = crewSize;
        this.rating = rating;
    }
}
