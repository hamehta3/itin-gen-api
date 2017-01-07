package com.itinapi.itinerary;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by hamehta3 on 1/7/17.
 */
public class ItineraryException extends WebApplicationException {
    public ItineraryException(int errorCode, String message) {
        super(Response.status(500).
                entity(new ErrorMessage(500,errorCode,message)).type(MediaType.APPLICATION_JSON).build());
    }
}
