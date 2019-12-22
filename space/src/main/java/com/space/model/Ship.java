package com.space.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "SHIP")
public class Ship {
    private Long id;
    private String name;
    private String planet;
    private ShipType shipType;
    private Date prodDate;
    private Boolean isUsed;
    private Double speed;
    private Integer crewSize;
    private Double rating;

    public Ship() {
    }

    @Id
    @Column(name = "ID")
    // @SequenceGenerator(name="EMPLOYEES_SEQ", sequenceName="EMPLOYEES_SEQ",allocationSize=1)
    public Long getId() {
        return id;
    }
    @Basic
    @Column(name = "NAME")
    public String getName() {
        return name;
    }
    @Basic
    @Column(name = "PLANET")
    public String getPlanet() {
        return planet;
    }
    @Basic
    @Column(name = "SHIPTYPE")
    @Enumerated(EnumType.STRING)
    public ShipType getShipType() {
        return shipType;
    }
    @Basic
    @Column(name = "PRODDATE")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getProdDate() {
        return prodDate;
    }
    @Basic
    @Column(name = "ISUSED")
    public Boolean getIsUsed() {
        return isUsed;
    }
    @Basic
    @Column(name = "SPEED")
    public Double getSpeed() {
        return speed;
    }
    @Basic
    @Column(name = "CREWSIZE")
    public Integer getCrewSize() {
        return crewSize;
    }
    @Basic
    @Column(name = "RATING")
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
