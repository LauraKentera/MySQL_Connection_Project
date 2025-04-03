-- MySQL dump 10.13  Distrib 5.5.15, for Win32 (x86)
--
-- Host: localhost    Database: pe1
-- ------------------------------------------------------
-- Server version	5.1.59-community

/*!40101 SET @OLD_CHARACTER_SET_CLIENT = @@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS = @@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION = @@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE = @@TIME_ZONE */;
/*!40103 SET TIME_ZONE = '+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0 */;
/*!40101 SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE = 'NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES = @@SQL_NOTES, SQL_NOTES = 0 */;

DROP DATABASE IF EXISTS travel23;
CREATE DATABASE travel23;
USE travel23;

--
-- Table structure for table `equipment`
--

DROP TABLE IF EXISTS `equipment`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
-- Table: equipment
CREATE TABLE `equipment`
(
    `EquipID`              int(11)      NOT NULL AUTO_INCREMENT,
    `EquipmentName`        varchar(50)  NOT NULL,
    `EquipmentDescription` varchar(100) NOT NULL,
    `EquipmentCapacity`    int(11)      NOT NULL,
    PRIMARY KEY (`EquipID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equipment`
--

LOCK TABLES `equipment` WRITE;
/*!40000 ALTER TABLE `equipment`
    DISABLE KEYS */;
INSERT INTO `equipment`
VALUES (568, 'Continental', 'Passenger and Cargo', 400),
       (894, 'Bus 264', 'Coach', 35),
       (1256, 'Airbus 300', 'Long Range', 150),
       (3644, 'Boeing 767', 'Short Range', 100),
       (5634, 'Boeing 727', 'Short Range', 100),
       (7624, 'Bus 345', 'Coach', 35),
       (8596, 'Boeing 727', 'Short Range', 100);
/*!40000 ALTER TABLE `equipment`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `locations`
--

DROP TABLE IF EXISTS `locations`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
-- Table: locations
CREATE TABLE `locations`
(
    `LocationCode` char(3)      NOT NULL,
    `Location`     varchar(100) NOT NULL,
    PRIMARY KEY (`LocationCode`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `locations`
--

LOCK TABLES `locations` WRITE;
/*!40000 ALTER TABLE `locations`
    DISABLE KEYS */;
INSERT INTO `locations`
VALUES ('BOS', 'Boston'),
       ('BUF', 'Buffalo'),
       ('BUR', 'Burbank'),
       ('JFK', 'New York'),
       ('LAS', 'Las Vegas'),
       ('LGA', 'New York'),
       ('NAS', 'Nassau'),
       ('ROC', 'Rochester');
/*!40000 ALTER TABLE `locations`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `passenger`
--

DROP TABLE IF EXISTS `passenger`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
-- Table: passenger
CREATE TABLE `passenger`
(
    `PassengerID` int(11)     NOT NULL AUTO_INCREMENT,
    `FName`       varchar(50) NOT NULL,
    `LName`       varchar(50) NOT NULL,
    `Street`      varchar(50) NOT NULL,
    `Zip`         varchar(5)  NOT NULL,
    PRIMARY KEY (`PassengerID`),
    KEY `Zip` (`Zip`),
    CONSTRAINT `passenger_ibfk_1` FOREIGN KEY (`Zip`) REFERENCES `zips` (`Zip`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `passenger`
--

LOCK TABLES `passenger` WRITE;
/*!40000 ALTER TABLE `passenger`
    DISABLE KEYS */;
INSERT INTO `passenger`
VALUES (1, 'Ken', 'Bennet', '12 Marway Circle', '14624'),
       (2, 'Patti', 'Hughes', '280 Commerce Dr', '14623'),
       (3, 'Dale', 'Payne', '34 Foley Dr', '14551'),
       (4, 'Dan', 'Callahan', '320 West Craig Hill', '14626'),
       (5, 'Rich', 'Gleason', '232 Industrial Park Dr', '13340'),
       (6, 'Scott', 'Kier', '150 Highland Ave.', '14618'),
       (7, 'Mark', 'Lucas', '425 Old Center Macedon Rd', '14450'),
       (8, 'Scott', 'Wilson', '70 Bermar Park', '14624'),
       (9, 'Terry', 'Brown', '100 Pennsylvania Ave', '01701'),
       (10, 'Curtis', 'Brown', '100 Ajax Rd', '14624');
/*!40000 ALTER TABLE `passenger`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phones`
--

DROP TABLE IF EXISTS `phones`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
-- Table: phones
CREATE TABLE `phones`
(
    `PassengerID` int(11)     NOT NULL,
    `PhoneNum`    varchar(15) NOT NULL,
    `PhoneType`   varchar(50) NOT NULL,
    PRIMARY KEY (`PassengerID`, `PhoneNum`),
    CONSTRAINT `phones_ibfk_1` FOREIGN KEY (`PassengerID`) REFERENCES `passenger` (`PassengerID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phones`
--

LOCK TABLES `phones` WRITE;
/*!40000 ALTER TABLE `phones`
    DISABLE KEYS */;
INSERT INTO `phones`
VALUES (1, '585-475-1440', 'Home'),
       (2, '585-874-4956', 'Home'),
       (3, '585-325-6530', 'Cell'),
       (3, '585-454-3290', 'Home'),
       (4, '585-254-8080', 'Home'),
       (5, '585-442-0450', 'Home'),
       (6, '585-461-6898', 'Home'),
       (7, '585-264-3135', 'Home'),
       (8, '585-463-3420', 'Cell'),
       (8, '585-538-6822', 'Home'),
       (9, '612-576-9985', 'Home'),
       (10, '585-263-3905', 'Cell'),
       (10, '585-593-5860', 'Home');
/*!40000 ALTER TABLE `phones`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff`
--

DROP TABLE IF EXISTS `staff`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
-- Table: staff (Fixed foreign key)
CREATE TABLE `staff`
(
    `Date`    date         NOT NULL,
    `TripNum` varchar(10)  NOT NULL,
    `Role`    varchar(20)  NOT NULL,
    `Name`    varchar(100) NOT NULL,
    PRIMARY KEY (`Date`, `TripNum`, `Role`),
    CONSTRAINT `staff_ibfk_1` FOREIGN KEY (`Date`, `TripNum`) REFERENCES `trip` (`Date`, `TripNum`) ON DELETE CASCADE
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff`
--

LOCK TABLES `staff` WRITE;
/*!40000 ALTER TABLE `staff`
    DISABLE KEYS */;
INSERT INTO `staff`
VALUES ('2023-08-14', '3030', 'Pilot', 'Greg Zalewski'),
       ('2023-09-07', '546', 'Driver', 'Brian Page'),
       ('2023-09-11', '4567', 'Engineer', 'Howard Vogel'),
       ('2023-10-10', '3030', 'CoPilot', 'Dan Gnagy'),
       ('2023-10-10', '3030', 'Pilot', 'Brad Raushey'),
       ('2023-10-11', '1027', 'CoPilot', 'Lorraine LeBan'),
       ('2023-10-11', '1027', 'Pilot', 'Molly Connor'),
       ('2023-11-04', '6432', 'Driver', 'Pam Stewart');
/*!40000 ALTER TABLE `staff`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trip`
--

DROP TABLE IF EXISTS `trip`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
-- Table: trip
CREATE TABLE `trip`
(
    `Date`             date        NOT NULL,
    `TripNum`          varchar(10) NOT NULL,
    `ArrivalTime`      varchar(10) DEFAULT NULL,
    `ArrivalLocCode`   char(3)     DEFAULT NULL,
    `DepartureTime`    varchar(10) DEFAULT NULL,
    `DepartureLocCode` char(3)     DEFAULT NULL,
    `EstArrivalTime`   varchar(10) DEFAULT NULL,
    `EstDepartureTime` varchar(10) DEFAULT NULL,
    `EquipID`          int(11)     NOT NULL,
    PRIMARY KEY (`Date`, `TripNum`),
    CONSTRAINT `trip_ibfk_1` FOREIGN KEY (`ArrivalLocCode`) REFERENCES `locations` (`LocationCode`),
    CONSTRAINT `trip_ibfk_2` FOREIGN KEY (`DepartureLocCode`) REFERENCES `locations` (`LocationCode`),
    CONSTRAINT `trip_ibfk_3` FOREIGN KEY (`EquipID`) REFERENCES `equipment` (`EquipID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trip`
--

LOCK TABLES `trip` WRITE;
/*!40000 ALTER TABLE `trip`
    DISABLE KEYS */;
INSERT INTO `trip`
VALUES ('2023-08-14', '3030', '2:15 PM', 'NAS', '1:00 PM', 'BOS', '2:00 PM', '1:00 PM', 8596),
       ('2023-09-07', '546', '11:45 PM', 'ROC', '3:00 PM', 'JFK', '11:30 PM', '3:00 PM', 894),
       ('2023-09-11', '4567', '8:30 AM', 'BUF', '6:00 AM', 'ROC', '7:30 AM', '7:15 AM', 568),
       ('2023-10-10', '3030', NULL, NULL, '1:00 PM', 'BOS', '2:00 PM', '1:00 PM', 5634),
       ('2023-10-11', '1027', NULL, NULL, '10:00 AM', 'BUF', '2:00 PM', '10:00 AM', 1256),
       ('2023-11-04', '6432', NULL, NULL, '1:00 PM', 'JFK', '8:00 PM', '1:00 PM', 7624);
/*!40000 ALTER TABLE `trip`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trip_directory`
--

DROP TABLE IF EXISTS `trip_directory`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
-- Table: trip_directory
CREATE TABLE `trip_directory`
(
    `TripNum`          varchar(10) NOT NULL,
    `TripType`         char(1)     NOT NULL,
    `ArrivalTime`      varchar(10) NOT NULL,
    `ArrivalLocCode`   char(3)     NOT NULL,
    `DepartureTime`    varchar(10) NOT NULL,
    `DepartureLocCode` char(3)     NOT NULL,
    PRIMARY KEY (`TripNum`),
    CONSTRAINT `trip_directory_ibfk_1` FOREIGN KEY (`ArrivalLocCode`) REFERENCES `locations` (`LocationCode`),
    CONSTRAINT `trip_directory_ibfk_2` FOREIGN KEY (`DepartureLocCode`) REFERENCES `locations` (`LocationCode`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trip_directory`
--

LOCK TABLES `trip_directory` WRITE;
/*!40000 ALTER TABLE `trip_directory`
    DISABLE KEYS */;
INSERT INTO `trip_directory`
VALUES ('1027', 'P', '2:00 PM', 'LAS', '1:00 PM', 'BUF'),
       ('3030', 'P', '2:00 PM', 'NAS', '1:00 PM', 'BOS'),
       ('4567', 'T', '7:30 AM', 'BUF', '6:00 AM', 'ROC'),
       ('546', 'B', '11:30 PM', 'ROC', '3:00 PM', 'JFK'),
       ('6432', 'B', '8:00 PM', 'ROC', '1:00 PM', 'JFK'),
       ('8794', 'T', '11:00 PM', 'BUR', '8:00 AM', 'LAS');
/*!40000 ALTER TABLE `trip_directory`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `trip_people`
--

DROP TABLE IF EXISTS `trip_people`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
-- Table: trip_people (Fixed foreign key)
CREATE TABLE `trip_people`
(
    `TripNum`     varchar(10) NOT NULL,
    `Date`        date        NOT NULL,
    `PassengerID` int(11)     NOT NULL,
    PRIMARY KEY (`TripNum`, `Date`, `PassengerID`),
    CONSTRAINT `trip_people_ibfk_1` FOREIGN KEY (`Date`, `TripNum`) REFERENCES `trip` (`Date`, `TripNum`) ON DELETE CASCADE,
    CONSTRAINT `trip_people_ibfk_2` FOREIGN KEY (`PassengerID`) REFERENCES `passenger` (`PassengerID`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;

/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `trip_people`
--

LOCK TABLES `trip_people` WRITE;
/*!40000 ALTER TABLE `trip_people`
    DISABLE KEYS */;
INSERT INTO `trip_people`
VALUES ('3030', '2023-08-14', 9),
       ('546', '2023-09-07', 5),
       ('4567', '2023-09-11', 6),
       ('4567', '2023-09-11', 7),
       ('3030', '2023-10-10', 1),
       ('3030', '2023-10-10', 2),
       ('1027', '2023-10-11', 3),
       ('1027', '2023-10-11', 4),
       ('1027', '2023-10-11', 8),
       ('6432', '2023-11-04', 10);
/*!40000 ALTER TABLE `trip_people`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tripcodes`
--

DROP TABLE IF EXISTS `tripcodes`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
-- Table: tripcodes
CREATE TABLE `tripcodes`
(
    `TripType` char(1)     NOT NULL,
    `TypeName` varchar(20) NOT NULL,
    PRIMARY KEY (`TripType`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tripcodes`
--

LOCK TABLES `tripcodes` WRITE;
/*!40000 ALTER TABLE `tripcodes`
    DISABLE KEYS */;
INSERT INTO `tripcodes`
VALUES ('B', 'Bus'),
       ('P', 'Plane'),
       ('T', 'Train');
/*!40000 ALTER TABLE `tripcodes`
    ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `zips`
--

DROP TABLE IF EXISTS `zips`;
/*!40101 SET @saved_cs_client = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
-- Table: zips
CREATE TABLE `zips`
(
    `Zip`   varchar(5)   NOT NULL,
    `City`  varchar(100) NOT NULL,
    `State` char(2)      NOT NULL,
    PRIMARY KEY (`Zip`)
) ENGINE = InnoDB
  DEFAULT CHARSET = latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `zips`
--

LOCK TABLES `zips` WRITE;
/*!40000 ALTER TABLE `zips`
    DISABLE KEYS */;
INSERT INTO `zips`
VALUES ('01701', 'Framingham', 'MA'),
       ('13340', 'Frankfort', 'NY'),
       ('14450', 'Fairport', 'NY'),
       ('14551', 'Sodus', 'NY'),
       ('14618', 'Rochester', 'NY'),
       ('14623', 'Rochester', 'NY'),
       ('14624', 'Rochester', 'NY'),
       ('14626', 'Rochester', 'NY');
/*!40000 ALTER TABLE `zips`
    ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE = @OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE = @OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT = @OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS = @OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION = @OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES = @OLD_SQL_NOTES */;

-- Dump completed on 2011-11-30  9:45:21

SHOW TABLES;
DESC trip;
DESC passenger;
DESC staff;


SELECT td.TripNum       AS "Trip Number",
       td.DepartureTime AS "Scheduled Departure",
       dep.Location     AS "Departure City",
       td.ArrivalTime   AS "Scheduled Arrival",
       arr.Location     AS "Arrival City",
       tc.TypeName      AS "Trip Type"
FROM trip_directory td
         JOIN locations dep ON td.DepartureLocCode = dep.LocationCode
         JOIN locations arr ON td.ArrivalLocCode = arr.LocationCode
         JOIN tripcodes tc ON td.TripType = tc.TripType;

# Question 1
SELECT p.FName  AS "First Name",
       p.LName  AS "Last Name",
       p.Street AS "Street Address",
       z.City   AS "City",
       p.Zip    AS "Zip Code",
       z.State  AS "State"
FROM passenger p
         JOIN zips z ON p.Zip = z.Zip
ORDER BY p.LName ASC, p.FName ASC;


# Question 2
SELECT td.TripNum          AS "Trip Number",
       td.DepartureTime    AS "Departure Time",
       td.DepartureLocCode AS "Departure Location Code"
FROM trip_directory td
         JOIN tripcodes tc ON td.TripType = tc.TripType
WHERE tc.TypeName IN ('Bus', 'Train');

# Question 3
SELECT CONCAT(p.LName, ', ', p.FName) AS "Passenger"
FROM trip_people tp
         JOIN passenger p ON tp.PassengerID = p.PassengerID
WHERE MONTH(tp.Date) IN (8, 10)
ORDER BY p.LName, p.FName;

# Question 4
SELECT l.Location        AS "Location",
       COUNT(td.TripNum) AS "Number of Departures"
FROM trip_directory td
         JOIN locations l ON td.DepartureLocCode = l.LocationCode
GROUP BY l.Location
ORDER BY COUNT(td.TripNum) DESC;


# Question 5
SELECT *
FROM trip t
         JOIN locations dep ON t.DepartureLocCode = dep.LocationCode
         JOIN locations arr ON t.ArrivalLocCode = arr.LocationCode
WHERE dep.Location = 'Rochester'
  AND arr.Location = 'Buffalo';

SELECT *
FROM staff s
         JOIN trip t ON s.Date = t.Date AND s.TripNum = t.TripNum;

SELECT s.Name       AS "Staff Name",
       s.Role       AS "Role",
       s.Date       AS "Trip Date",
       dep.Location AS "Departure City",
       arr.Location AS "Arrival City"
FROM staff s
         JOIN trip t ON s.Date = t.Date AND s.TripNum = t.TripNum
         JOIN locations dep ON t.DepartureLocCode = dep.LocationCode
         JOIN locations arr ON t.ArrivalLocCode = arr.LocationCode
WHERE dep.Location = 'Rochester'
  AND arr.Location = 'Buffalo'
  AND MONTH(t.Date) = 8
ORDER BY s.Date;

# Question 6 -
# Molly Connor is from Rochester. Who if anyone, will she meet from her town when she works on a trip,
# and during what trip number (provide trip number in one column and person's name as
# "firstname lastname" in the other named "Person(s) to meet")?
SELECT *
FROM staff
WHERE Name = 'Molly Connor';

# Check All Passengers on Trip 1027
SELECT DISTINCT p.PassengerID, p.FName, p.LName, z.City
FROM trip_people tp
         JOIN passenger p ON tp.PassengerID = p.PassengerID
         JOIN zips z ON p.Zip = z.Zip
WHERE tp.TripNum = '1027'
  AND tp.Date = '2023-10-11';

# Check Rochester Passengers
SELECT DISTINCT p.PassengerID, p.FName, p.LName, z.City
FROM trip_people tp
         JOIN passenger p ON tp.PassengerID = p.PassengerID
         JOIN zips z ON p.Zip = z.Zip
WHERE tp.TripNum = '1027'
  AND tp.Date = '2023-10-11'
  AND z.City = 'Rochester';

# Final query
SELECT tp.TripNum                    AS "Trip Number",
       CONCAT(p.FName, ' ', p.LName) AS "Person(s) to meet"
FROM trip_people tp
         JOIN passenger p ON tp.PassengerID = p.PassengerID
         JOIN zips z ON p.Zip = z.Zip
WHERE tp.TripNum IN (SELECT TripNum FROM staff WHERE Name = 'Molly Connor')
  AND z.City = 'Rochester'
ORDER BY tp.TripNum, p.LName, p.FName;


# Question 7
# Check If Any Trips Exist for Planes
SELECT DISTINCT tc.TypeName, t.TripNum
FROM trip t
         JOIN trip_directory td ON t.TripNum = td.TripNum
         JOIN tripcodes tc ON td.TripType = tc.TripType
WHERE tc.TypeName = 'Plane';

# Check If There Are Rochester Passengers on Any Trips
SELECT DISTINCT p.PassengerID, p.FName, p.LName, z.City
FROM passenger p
         JOIN zips z ON p.Zip = z.Zip
WHERE z.City = 'Rochester';

# Check If Rochester Passengers Are on a Plane Trip
SELECT DISTINCT p.PassengerID, p.FName, p.LName, tp.TripNum, tc.TypeName
FROM trip_people tp
         JOIN passenger p ON tp.PassengerID = p.PassengerID
         JOIN zips z ON p.Zip = z.Zip
         JOIN trip t ON tp.TripNum = t.TripNum AND tp.Date = t.Date
         JOIN trip_directory td ON t.TripNum = td.TripNum
         JOIN tripcodes tc ON td.TripType = tc.TripType
WHERE z.City = 'Rochester'
  AND tc.TypeName = 'Plane';

SELECT DISTINCT p.LName AS "Last Name",
                p.FName AS "First Name"
FROM trip_people tp
         JOIN passenger p ON tp.PassengerID = p.PassengerID
         JOIN zips z ON p.Zip = z.Zip
         JOIN trip t ON tp.TripNum = t.TripNum AND tp.Date = t.Date
         JOIN trip_directory td ON t.TripNum = td.TripNum
         JOIN tripcodes tc ON td.TripType = tc.TripType
WHERE z.City = 'Rochester'
  AND tc.TypeName = 'Plane'
ORDER BY p.LName, p.FName;

# Question 8
SELECT DISTINCT e.EquipmentDescription AS "Equipment Description"
FROM trip_people tp
         JOIN passenger p ON tp.PassengerID = p.PassengerID
         JOIN trip t ON tp.TripNum = t.TripNum AND tp.Date = t.Date
         JOIN equipment e ON t.EquipID = e.EquipID
WHERE p.FName = 'Mark'
  AND p.LName = 'Lucas';

# Question 9
SELECT COUNT(DISTINCT tp.PassengerID) AS "Number of Bus Passengers"
FROM trip_people tp
         JOIN trip t ON tp.TripNum = t.TripNum AND tp.Date = t.Date
         JOIN trip_directory td ON t.TripNum = td.TripNum
         JOIN tripcodes tc ON td.TripType = tc.TripType
WHERE tc.TypeName = 'Bus';

# Question 10
SELECT DISTINCT tp.TripNum AS "Trip Number",
                tp.Date    AS "Trip Date"
FROM trip_people tp
         JOIN trip t ON tp.TripNum = t.TripNum AND tp.Date = t.Date
         JOIN trip_directory td ON t.TripNum = td.TripNum
         JOIN tripcodes tc ON td.TripType = tc.TripType
         JOIN phones ph ON tp.PassengerID = ph.PassengerID
WHERE tc.TypeName = 'Plane'
  AND ph.PhoneType = 'Cell'
ORDER BY tp.Date, tp.TripNum;

# Question 11
SELECT e.EquipID        AS "Equipment ID",
       e.EquipmentName  AS "Equipment Name",
       COUNT(t.TripNum) AS "Number of Trips"
FROM trip t
         JOIN equipment e ON t.EquipID = e.EquipID
GROUP BY e.EquipID, e.EquipmentName
ORDER BY "Number of Trips" DESC;

# Question 12
SELECT DISTINCT EquipID
FROM trip;

SELECT *
FROM equipment;

SELECT e.EquipID       AS "Equipment ID",
       e.EquipmentName AS "Equipment Name"
FROM equipment e
         LEFT JOIN trip t ON e.EquipID = t.EquipID
WHERE t.EquipID IS NULL;

# Question 13
# During the period of August, what types of transportation had more than one trip?
# All trips in August
SELECT *
FROM trip
WHERE MONTH(Date) = 8;

# KIND OF TRIP AND ITS NUM
SELECT t.TripNum,
       t.Date,
       td.TripType
FROM trip t
         JOIN trip_directory td ON t.TripNum = td.TripNum
WHERE MONTH(t.Date) = 8;

# More than one trip
SELECT tc.TypeName      AS "Transportation Type",
       COUNT(t.TripNum) AS "Number of Trips"
FROM trip t
         JOIN trip_directory td ON t.TripNum = td.TripNum
         JOIN tripcodes tc ON td.TripType = tc.TripType
WHERE MONTH(t.Date) = 8
GROUP BY tc.TypeName
HAVING COUNT(t.TripNum) > 1;

-- Drop existing table (optional, for reset)
DROP TABLE IF EXISTS User;

-- Create table with Id as a username (string)
CREATE TABLE User (
                      Id               VARCHAR(50) PRIMARY KEY,  -- now a username
                      FirstName        VARCHAR(50),
                      LastName         VARCHAR(50),
                      Password         VARCHAR(255),
                      Role             VARCHAR(20),
                      OrganizationUnit VARCHAR(50)
);

-- Insert users with string usernames
INSERT INTO User (Id, FirstName, LastName, Password, Role, OrganizationUnit)
VALUES
    ('admin1', 'Alice', 'Admin', 'JAvlGPq9JyTdtvBO6x2llnRI1+gxwIyPqCKAn3THIKk=', 'Admin', 'HQ'),
    ('editor1', 'Bob', 'Editor', 's41h3e8CDZwF2+gJ3nRKnJgbqXMuaxMl29EC2kEeMi4=', 'Editor', 'Ops'),
    ('general1', 'Carol', 'General', 'mEbU7vszm2Ut1nZ8PM9RBIrFLbFnH0W8zqjDOfzfuEE=', 'General', 'Staff');

-- Update passwords (if needed, matched by role or name since Id is now INT)
UPDATE User SET Password = 'JAvlGPq9JyTdtvBO6x2llnRI1+gxwIyPqCKAn3THIKk='
WHERE id='admin1';

UPDATE User SET Password = '715aH7lQVeDlbMz5ikHnhKEywU5/bhuiRDAvDnKym68='
WHERE id='editor1';

UPDATE User SET Password = 'Ran9jWI7djFyD8F577IVd213ZmQM4jJu0T9Pal0mBH0='
WHERE id='general1';


