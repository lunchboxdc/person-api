Requirements
    jdk v8 or higher
    mvn v3 or higher

Compile and run tests

    mvn package

Run application

    mvn spring-boot:run

CSV Files - you can either drop csv files in `src/main/resources` -or- provide a command line argument of a directory that contains csv files:

    mvn spring-boot:run -Dspring-boot.run.arguments=--csv.dir=/some/csv/dir

