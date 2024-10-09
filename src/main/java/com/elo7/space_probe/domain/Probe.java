package com.elo7.space_probe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "module")
public class Probe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Pattern(regexp="[S|W|E|N]{1}") 
    private String direction;

    @Embedded
    private Position position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "planet_id", referencedColumnName = "id", nullable = false)
    private Planet planet;

    @Deprecated // hibernate only
    public Probe() {}

    public Probe(String name, Integer x, Integer y, String direction, Planet planet) {
        this.name = name;
        this.position = new Position(x, y);
        this.direction = direction;
        this.planet = planet;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDirection(){
        return direction;
    }

    public void setDirection(String direction){
        this.direction = direction;
    }

    public Integer getXPosition() {
        return position.getX();
    }

    public Integer getYPosition() {
        return position.getY();
    }

    public Integer getPlanetId() {
        return planet.getId();
    }


    public void setPosition(Integer x, Integer y) {
        this.position = new Position(x,y);
    }

}
