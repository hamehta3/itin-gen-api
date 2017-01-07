package com.itinapi.itinerary;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by hamehta3 on 10/15/16.
 */
@XmlRootElement
public class Segment {
    private Location from;
    private Location to;
    private Location stopover;
    private Date date;
    private Cost cost;
    private Long distance;

    public Segment() {}

    public Location getFrom() {
        return from;
    }

    public void setFrom(Location from) {
        this.from = from;
    }

    public Location getTo() {
        return to;
    }

    public void setTo(Location to) {
        this.to = to;
    }

    public Location getStopover() {
        return stopover;
    }

    public void setStopover(Location stopover) {
        this.stopover = stopover;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Cost getCost() {
        return cost;
    }

    public void setCost(Cost cost) {
        this.cost = cost;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }
}
