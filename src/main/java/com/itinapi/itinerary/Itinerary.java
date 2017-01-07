package com.itinapi.itinerary;

import com.itinapi.graph.Graph;

import javax.naming.NamingException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by hamehta3 on 10/15/16.
 */
@XmlRootElement
public class Itinerary {
    private Destination origin;
    private Destination end;
    private List<Destination> destinations;
    private List<Segment> segments;
    private Cost budget;
    private Cost totalCost;

    public Itinerary() {}

    public Itinerary(Destination origin, Destination end, List<Destination> destinations, Cost budget) {
        this.origin = origin;
        this.end = end;
        this.destinations = destinations;
        this.budget = budget;
    }

    public Destination getOrigin() {
        return origin;
    }

    public void setOrigin(Destination origin) {
        this.origin = origin;
    }

    public Destination getEnd() {
        return end;
    }

    public void setEnd(Destination end) {
        this.end = end;
    }

    public List<Destination> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<Destination> destinations) {
        this.destinations = destinations;
    }

    public List<Segment> getSegments() {
        return segments;
    }

    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }

    public Cost getBudget() {
        return budget;
    }

    public void setBudget(Cost budget) {
        this.budget = budget;
    }

    public Cost getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Cost totalCost) {
        this.totalCost = totalCost;
    }

    public void generate(List<Route> routes) {
        Graph graph = new Graph(this, routes);
        this.segments = graph.getSegments();
        double totalCost = 0.0;
        this.totalCost = new Cost(totalCost, budget.getCurrency());
        for (Segment segment : this.segments) {
            totalCost += segment.getCost().getAmount();
        }
        this.totalCost.setAmount(totalCost);
    }
}
