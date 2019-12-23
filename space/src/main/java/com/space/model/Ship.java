package com.space.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "SHIP")
public class Ship {
    @Id
    @Column(name = "ID")
    private Long id;
    @Basic
    @Column(name = "NAME", length = 50)
    private String name;
    @Basic
    @Column(name = "PLANET", length = 50)
    private String planet;
    @Basic
    @Column(name = "SHIPTYPE")
    @Enumerated(EnumType.STRING)
    private ShipType shipType;
    @Basic
    @Column(name = "PRODDATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date prodDate;
    @Basic
    @Column(name = "ISUSED")
    private Boolean isUsed;


    private Double speed;
    @Basic
    @Column(name = "CREWSIZE")
    private Integer crewSize;
    @Basic
    @Column(name = "RATING")
    private Double rating;




    public Ship() {
    }
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
    public Date getProdDate() {
        return prodDate;
    }
    public Boolean getIsUsed() {
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


    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlanet(String planet) {
        this.planet = planet;
    }

    public void setShipType(ShipType shipType) {
        this.shipType = shipType;
    }

    public void setProdDate(Date prodDate) {
        this.prodDate = prodDate;
    }

    public void setIsUsed(Boolean isUsed) {
        this.isUsed = isUsed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public void setCrewSize(Integer crewSize) {
        this.crewSize = crewSize;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
