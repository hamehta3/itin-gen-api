package com.itinapi.graph;

import com.itinapi.itinerary.*;
import com.itinapi.util.Estimator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hamehta3 on 12/31/16.
 */
public class Graph {
    private int [][] costMatrix;
    private long[][] distMatrix;
    private int [][] prevMatrix;
    private int N;
    private Map<String, Integer> iataVertexMap;
    private Map<Integer, String> vertexIataMap;
    private Map<String, Location> locationMap;
    private static final int INF = Integer.MAX_VALUE;
    private String currency;

    private int shortestRouteCost = Integer.MAX_VALUE;
    private int [] shortestRoute;

    public Graph(Itinerary itinerary, List<Route> routes) {
        N = itinerary.getDestinations().size();
        currency = itinerary.getBudget().getCurrency();
        shortestRoute = new int[N];
        initMatrix();
        initVertices(itinerary.getDestinations());
        initEdges(routes);
        initDistances();

        allPairsShortestPaths();
    }

    private void initMatrix() {
        costMatrix = new int[N][N];
        prevMatrix = new int[N][N];
        for (int i=0; i<N; i++) {
            for (int j=0; j<N; j++) {
                costMatrix[i][j] = ((i == j) ? 0 : INF);
                prevMatrix[i][j] = -1;
            }
        }
    }

    private void initVertices(List<Destination> destinations) {
        iataVertexMap = new HashMap<String, Integer>();
        vertexIataMap = new HashMap<Integer, String>();
        locationMap = new HashMap<String, Location>();
        int i=0;
        for (Destination destination : destinations) {
            iataVertexMap.put(destination.getLocation().getAirportCode(), i);
            vertexIataMap.put(i, destination.getLocation().getAirportCode());
            locationMap.put(destination.getLocation().getAirportCode(), destination.getLocation());
            i++;
        }
    }

    private void initEdges(List<Route> routes) {
        for (Route route : routes) {
            int x = iataVertexMap.get(route.getFrom());
            int y = iataVertexMap.get(route.getTo());
            costMatrix[x][y] = Estimator.calcCost(route.getDistance(), route.getNumFlights(), currency);
        }
    }

    private void initDistances() {
        distMatrix = new long[N][N];
        for (int i=0; i<N; i++) {
            for (int j=0; j<N; j++) {
                if (i != j) {
                    distMatrix[i][j] = Estimator.calcDistance(
                            locationMap.get(vertexIataMap.get(i)), locationMap.get(vertexIataMap.get(j)));
                }
            }
        }
    }

    private void allPairsShortestPaths() {
        // Using Floyd Warshall's algorithm
        for (int k=0; k<N; k++) {
            for (int i=0; i<N; i++) {
                for (int j=0; j<N; j++) {
                    if (costMatrix[i][j] > costMatrix[i][k] + costMatrix[k][j]) {
                        costMatrix[i][j] = costMatrix[i][k] + costMatrix[k][j];
                        prevMatrix[i][j] = k;
                    }
                }
            }
        }
    }

    // TODO look into some branch-and-bound techniques, etc.
    private void permuteRoutes(int [] currRoute, int index) {
        if (index == currRoute.length) {
            checkRoute(currRoute);
            return;
        }
        for (int i=index; i<currRoute.length; i++) {
            swap(currRoute, i, index);
            permuteRoutes(currRoute, index+1);
            swap(currRoute, index, i);
        }
    }

    private void swap(int [] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private void checkRoute(int [] currRoute) {
        int cost = 0;
        for (int i=1; i<currRoute.length; i++) {
            cost += costMatrix[currRoute[i-1]][currRoute[i]];
        }
        if (cost < shortestRouteCost) {
            shortestRouteCost = cost;
            System.arraycopy(currRoute, 0, shortestRoute, 0, currRoute.length);
        }
    }

    public List<Segment> getSegments() {
        List<Segment> segments = new ArrayList<Segment>();
        int [] route = new int[N];
        for (int i=0; i<N; i++) {
            route[i] = i;
        }
        permuteRoutes(route,0);
        for (int i=1; i<shortestRoute.length; i++) {
            int fromVertex = shortestRoute[i-1];
            int toVertex = shortestRoute[i];
            Segment segment = new Segment();
            Location from = new Location();
            from.setName(vertexIataMap.get(shortestRoute[i-1]));
            Location to = new Location();
            to.setName(vertexIataMap.get(shortestRoute[i]));
            segment.setFrom(from);
            segment.setTo(to);

            // FIXME. Horrible estimate upon horrible estimate = horrible^2 estimate :|
            segment.setCost(new Cost(4.0*costMatrix[fromVertex][toVertex], currency));
            segment.setDistance(distMatrix[fromVertex][toVertex]);

            if (prevMatrix[fromVertex][toVertex] != -1) {
                Location stopover = new Location();
                stopover.setName(vertexIataMap.get(prevMatrix[fromVertex][toVertex]));
                segment.setStopover(stopover);
                // override distance
                segment.setDistance(
                        distMatrix[fromVertex][prevMatrix[fromVertex][toVertex]] +
                        distMatrix[prevMatrix[fromVertex][toVertex]][toVertex]
                );
            }
            segments.add(segment);
        }
        return segments;
    }


    /*
     * Print functions
     */

    private void print(int [][] matrix) {
        for (int i=0; i<N; i++) {
            for (int j=0; j<N; j++) {
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private void printRoutes() {
        for (int i=0; i<N; i++) {
            System.out.println("Shortest path from "+vertexIataMap.get(i)+" to:");
            for (int j=0; j<N; j++) {
                if (i==j) continue;
                System.out.print(vertexIataMap.get(j)+": "+costMatrix[i][j]);
                if (prevMatrix[i][j] != -1) {
                    System.out.print(" via "+vertexIataMap.get(prevMatrix[i][j]));
                }
                System.out.println();
            }
            System.out.println();
        }
        System.out.println();
    }
}
