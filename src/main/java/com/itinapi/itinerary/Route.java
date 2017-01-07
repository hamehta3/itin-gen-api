package com.itinapi.itinerary;

/**
 * Created by hamehta3 on 12/26/16.
 */
public class Route {
    private String from;
    private String to;
    private Double distance;
    private Integer numFlights;

    public Route() {}

    public Route(String from, String to, Double distance, Integer numFlights) {
        this.from = from;
        this.to = to;
        this.distance = distance;
        this.numFlights = numFlights;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Integer getNumFlights() {
        return numFlights;
    }

    public void setNumFlights(Integer numFlights) {
        this.numFlights = numFlights;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Route{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", distance=" + distance +
                ", numFlights=" + numFlights +
                '}';
    }
}
