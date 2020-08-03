Environment setup
   -  jdk v8 or higher
   -  maven v3 or higher

Compile and run tests

    mvn package

Run application

    mvn spring-boot:run

CSV Files - you can either drop csv files in `src/main/resources` -or- provide a command line argument of a directory that contains csv files:

    mvn spring-boot:run -Dspring-boot.run.arguments=--csv.dir=/some/csv/dir



Local api endpoints for testing
- Get records sorted by gender - `http://localhost:8080/records/gender`
- Get records sorted by last name - `http://localhost:8080/records/name`
- Get records sorted by birth date - `http://localhost:8080/records/birthdate`

