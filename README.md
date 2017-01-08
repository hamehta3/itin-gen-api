# itin-gen-api
An API to generate an optimal itinerary from a list of destinations

### Install
Requires MySQL. Scripts to load data are under src/main/sql/.

You will need to create a data source named 'jdbc/openflights' in your application server.

DB settings can be changed at src/main/resources/db.properties.

Build the project using `mvn clean package` and deploy the generated .war file in your server.

### Routes

POST /itin-gen-api/webapi/itinerary

Sample call with data:
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
      "segments":[
        {
          "cost":{"amount": 108, "currency": "USD"},
          "distance": 748,
          "from":{"name": "BCN"},
          "to":{"name": "FRA"}
        },
        {
          "cost":{"amount": 44, "currency": "USD"},
          "distance": 304,
          "from":{"name": "FRA"},
          "to":{"name": "AMS"}
        },
        {
          "cost":{"amount": 52, "currency": "USD"},
          "distance": 274,
          "from":{"name": "AMS"},
          "to":{"name": "CDG"}
        }
      ],
      "totalCost": {
        "amount": 204,
        "currency": "USD"
      }
  ```


### TODO
Tests, integrate with actual pricing service

### Credits
Flight, airport, and airline data obtained from [openflights](https://github.com/jpatokal/openflights)
