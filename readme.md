### How to run this ###
1. Change to project directory.
2. sbt "run 8080"

### How to call the service ###
- curl --header "Content-type: application/json" -d "{\"floor\": 2}" http://localhost:8080/requestElevator/
- curl --header "Content-type: application/json" -d "{\"floor\": 5}" http://localhost:8080/requestFloor/
- curl http://localhost:8080/requestedFloors/
- curl http://localhost:8080/nextFloor/

### TODOs ###
- Figure out if we want to track the elevator floor and direction here and add a real strategy for servicing the floors in some kind of rational order, or if that's going to be external to this service.
- Decide if this should handle multiple elevators, time of day, etc.
- Add some kind of testing. Don't know what strategy the company uses or how anything else will interface with this.
