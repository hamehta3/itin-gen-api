package com.itinapi.dao;

import com.itinapi.itinerary.Destination;
import com.itinapi.itinerary.ItineraryException;
import com.itinapi.itinerary.Location;
import com.itinapi.itinerary.Route;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hamehta3 on 10/29/16.
 */
public class OpenFlightsDAO {
    private static final int DEST_LIMIT = 10;  // Currently only 10 supported
    private static final String SQL_GET_AIRPORT_COORDINATES =
            "SELECT iata,name,x,y FROM airports WHERE iata IN (?,?,?,?,?,?,?,?,?,?)";  // Hacky
    private static final String SQL_GET_ROUTES =
            "SELECT r.src_ap, r.dst_ap, POW((a1.x - a2.x), 2) + POW((a1.y - a2.y), 2) AS distance, count(*) AS flights " +
                    "FROM routes r, airports a1, airports a2 WHERE r.src_ap = a1.iata AND r.dst_ap = a2.iata AND " +
                    "r.src_ap IN (?,?,?,?,?,?,?,?,?,?) AND r.dst_ap IN (?,?,?,?,?,?,?,?,?,?) " +
                    "GROUP BY r.src_ap, r.dst_ap, a1.x, a1.y, a2.x, a2.y ORDER BY flights DESC, distance";

    private DAOFactory daoFactory;

    public OpenFlightsDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public void updateLocations(List<Destination> destinations) {
        if (destinations.size() == 0) {
            return;
        }
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_GET_AIRPORT_COORDINATES);
            Map<String, Destination> destMap = new HashMap<String, Destination>();
            List<String> airportCodes = new ArrayList<String>();
            for (Destination destination : destinations) {
                String airportCode = destination.getLocation().getAirportCode();
                airportCodes.add(airportCode);
                destMap.put(airportCode, destination);
            }
            populate(statement, airportCodes, 0);
            //System.out.println("OpenFlightsDAO updateLocations before executeQuery");
            resultSet = statement.executeQuery();
            int count = 0;
            while (resultSet.next()) {
                Destination destination = destMap.get(resultSet.getString(1));
                destination.getLocation().setName(resultSet.getString(2));
                destination.getLocation().setX(resultSet.getDouble(3));
                destination.getLocation().setY(resultSet.getDouble(4));
                count++;
            }
            if (count != destinations.size()) {
                throw new ItineraryException(2, "Request contains invalid location(s)");
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            closeConnections(connection, statement, resultSet);
        }
    }

    public List<Route> routes(List<String> airportCodes) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = daoFactory.getConnection();
            statement = connection.prepareStatement(SQL_GET_ROUTES);
            populate(statement, airportCodes, 0);
            populate(statement, airportCodes, DEST_LIMIT);
            resultSet = statement.executeQuery();
            List<Route> routes = new ArrayList<Route>();
            while (resultSet.next()) {
                Route route = new Route(resultSet.getString(1), resultSet.getString(2),
                        resultSet.getDouble(3), resultSet.getInt(4));
                routes.add(route);
            }
            return routes;
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            closeConnections(connection, statement, resultSet);
        }
    }

    private void populate(PreparedStatement statement, List<String> items, int offset) throws SQLException {
        for (int i=0; i<DEST_LIMIT; i++) {
            statement.setString(offset+i+1, (i < items.size()) ? items.get(i) : null);
        }
    }

    private void closeConnections(Connection connection, PreparedStatement statement, ResultSet resultSet) {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }
}
