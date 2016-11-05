CREATE TABLE city (
    ID int NOT NULL
    , Name char(35) NOT NULL DEFAULT ''
    , CountryCode char(3) NOT NULL DEFAULT ''
    , District char(20) NOT NULL DEFAULT ''
    , Population int NOT NULL DEFAULT '0'
    , PRIMARY KEY (ID)
);

CREATE TABLE countrylanguage (
    CountryCode char(3) NOT NULL DEFAULT ''
    , Language char(30) NOT NULL DEFAULT ''
    , IsOfficial char(1)  NOT NULL DEFAULT 'F'
    , Percentage decimal(4,1) NOT NULL DEFAULT '0.0'
    , PRIMARY KEY (CountryCode, Language)
    , CHECK(IsOfficial IN ('T','F'))
);

CREATE TABLE country (
    Code char(3) NOT NULL DEFAULT ''
    , Name char(52) NOT NULL DEFAULT ''
    , Continent varchar(20) NOT NULL DEFAULT 'Asia'
    , Region char(26) NOT NULL DEFAULT ''
    , SurfaceArea decimal(10,2) NOT NULL DEFAULT '0.00'
    , IndepYear smallint DEFAULT NULL
    , Population int NOT NULL DEFAULT '0'
    , LifeExpectancy decimal(3,1) DEFAULT NULL
    , GNP decimal(10,2) DEFAULT NULL
    , GNPOld decimal(10,2) DEFAULT NULL
    , LocalName char(45) NOT NULL DEFAULT ''
    , GovernmentForm char(45) NOT NULL DEFAULT ''
    , HeadOfState char(60) DEFAULT NULL
    , Capital int DEFAULT NULL
    , Code2 char(2) NOT NULL DEFAULT ''
    , PRIMARY KEY (Code)
    , CHECK(Continent in ('Asia','Europe','North America','Africa','Oceania','Antarctica','South America'))
);

