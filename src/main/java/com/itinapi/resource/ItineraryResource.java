package com.itinapi.resource;

import com.itinapi.dao.DAOFactory;
import com.itinapi.dao.OpenFlightsDAO;
import com.itinapi.graph.Graph;
import com.itinapi.itinerary.*;

import javax.naming.NamingException;
import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hamehta3 on 11/20/16.
 */
@Path("itinerary")
@Consumes("application/json")
@Produces("application/json")
public class ItineraryResource {

    private Itinerary itinerary;
    OpenFlightsDAO openFlightsDAO;

    {  // TODO ideally we want to move this init code out of here (init it just once)
        DAOFactory daoFactory = null;
        try {
            daoFactory = DAOFactory.getInstance();
        } catch (NamingException e) {
            System.err.println(e.getMessage());
        }
        openFlightsDAO = new OpenFlightsDAO(daoFactory);
    }

    public void setItinerary(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

    @POST
    public Itinerary create(Itinerary itinerary) throws NamingException {
        setItinerary(itinerary);
        List<String> airportCodes = new ArrayList<String>();
        for (Destination destination : this.itinerary.getDestinations()) {
            airportCodes.add(destination.getLocation().getAirportCode());
        }
        openFlightsDAO.updateLocations(this.itinerary.getDestinations());
        List<Route> routes = openFlightsDAO.routes(airportCodes);
        itinerary.generate(routes);
        return this.itinerary;
    }
}
