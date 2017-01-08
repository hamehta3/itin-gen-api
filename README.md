# itin-gen-api
An API to generate an optimal itinerary from a list of destinations.

Currently it uses some (very) naive heuristics to estimate costs - i.e. number of flights between a pair of cities and distance. This is an ongoing project, so suggestions and feedback are always welcome!

### Install
Requires Maven and MySQL. Scripts to load data are under src/main/sql/.

You will need to create a data source named 'jdbc/openflights' in your application server.

DB settings can be changed at src/main/resources/db.properties.

Build the project using `mvn clean package` and deploy the generated .war file on your server.

### Routes

POST /itin-gen-api/webapi/itinerary

Sample call with data (json):
  ```json
    {
      "origin": {
        "location": {
          "airportCode": "BOS"
        }
      },
      "end": {
        "location": {
          "airportCode": "JFK"
        }
      },
      "destinations": [
        {
          "location": {
            "airportCode": "BCN"
          }
        },
        {
          "location": {
            "airportCode": "AMS"
          }
        },
        {
          "location": {
            "airportCode": "FRA"
          }
        },
        {
          "location": {
            "airportCode": "CDG"
          }
        }
      ],
      "budget": {
        "amount": 1300,
        "currency": "USD"
      }
    }
  ```
Sample response:
```json
      ...
      "segments": [
        {
          "cost":{"amount": 60, "currency": "USD"},
          "distance": 534,
          "from":{"name": "BCN"},
          "to":{"name": "CDG"}
        },
        {
          "cost":{"amount": 52, "currency": "USD"},
          "distance": 274,
          "from":{"name": "CDG"},
          "to":{"name": "AMS"}
        },
        {
          "cost":{"amount": 44, "currency": "USD"},
          "distance": 304,
          "from":{"name": "AMS"},
          "to":{"name": "FRA"}
        }
      ],
      "totalCost": {
        "amount": 156,
        "currency": "USD"
      }
  ```


### TODO and caveats
* Integrate with actual pricing service
* Optimize algorithm; use more clever heuristics over naive TSP
* No support for origin and final destinations yet
* Currently (only) one stopover supported - handle transit more smartly
* Add support for persistent and asynchronous itineraries
* Tests - coming soon

### Credits
Flight, airport, and airline data obtained from [openflights](https://github.com/jpatokal/openflights)
