package com.itinapi.util;

import com.itinapi.itinerary.Location;

/**
 * Created by hamehta3 on 12/31/16.
 */
public class Estimator {

    public static long calcDistance(Location location1, Location location2) {
        // Haversine method
        double R = 3959;  // miles
        long distance = 0L;
        double lat1, lat2, lon1, lon2, dLat, dLon;
        double a, c;

        lat1 = Math.toRadians(location1.getX());
        lat2 = Math.toRadians(location2.getX());
        dLat = lat2-lat1;
        lon1 = Math.toRadians(location1.getY());
        lon2 = Math.toRadians(location2.getY());
        dLon = lon2-lon1;

        a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(lat1) * Math.cos(lat2) * Math.sin(dLon/2) * Math.sin(dLon/2);
        c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        distance = Math.round(R * c);
        return distance;
    }

    public static int calcCost(double distance, int numFlights, String currency) {
        return (int) ((Math.sqrt(distance) * 10) / ((numFlights == 0) ? 1 : numFlights));
    }
}
