package com.itinapi.itinerary;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Created by hamehta3 on 10/15/16.
 */
@XmlRootElement
public class Location {
    private String name;
    private String airportCode;
    private Double x, y;

    public Location() {}

    public Location(String name, String airportCode, Double x, Double y) {
        this.name = name;
        this.airportCode = airportCode;
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    @XmlTransient
    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    @XmlTransient
    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                ", airportCode='" + airportCode + '\'' +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}
