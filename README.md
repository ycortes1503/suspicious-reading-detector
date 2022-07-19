# Suspicious Readings Detector

## Technology
This is a java console application that uses the following key technologies:
- [Java 11](https://www.oracle.com/java/technologies/downloads/)
- [Maven 3.8.4](https://maven.apache.org/download.cgi)
- [Junit 5.8.2](https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api/5.8.2)

## Description
This application identify readings that are either higher or lower than the annual median ± 50%.
For this first iteration, the application only supports csv and xml files names as argument (such as 2016-readings.xml or 2016-readings.csv).
The output is a table with the suspicious readings:

```
| Client              | Month              | Suspicious         | Median
-------------------------------------------------------------------------------
| <clientid>          | <month>            | <reading>          | <median>
```

It can assume there are no tricks in the XML and CSV files. Each client will have 12 readings, all consecutively.

## Improvements

* Add support to handle different inputs such as a database or even a txt file in a remote FTP.
* Test coverage.
* Check code quality
* Add more validations.
* Handle exceptions.


