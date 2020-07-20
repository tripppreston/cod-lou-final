DROP DATABASE IF EXISTS HarvestedObject;
CREATE DATABASE HarvestedObject;
USE HarvestedObject;

CREATE TABLE IF NOT EXISTS Harvested (
harvestedType VARCHAR(10) NOT NULL,
harvestedName VARCHAR(50) NOT NULL,
harvestedArea VARCHAR(50) NOT NULL,
harvestedPlanet VARCHAR(20) NOT NULL,
harvestedSize VARCHAR(10) NOT NULL,
harvestedFood VARCHAR(50) NOT NULL,
PRIMARY KEY(harvestedName)





);