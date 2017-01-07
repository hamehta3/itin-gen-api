package com.itinapi.itinerary;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by hamehta3 on 10/15/16.
 */
@XmlRootElement
public class Destination {
    private Location location;
    private Date date;

    public Destination() {}

    public Destination(Location location) {
        this.location = location;
    }

    public Destination(Location location, Date date) {
        this.location = location;
        this.date = date;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
