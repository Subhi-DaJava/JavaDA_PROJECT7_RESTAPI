CREATE TABLE Bidlist (
                         BidListId INTEGER NOT NULL AUTO_INCREMENT,
                         account VARCHAR(30) NOT NULL,
                         type VARCHAR(30) NOT NULL,
                         bidQuantity DOUBLE,
                         askQuantity DOUBLE,
                         bid DOUBLE ,
                         ask DOUBLE,
                         benchmark VARCHAR(125),
                         bidListDate TIMESTAMP,
                         commentary VARCHAR(125),
                         security VARCHAR(125),
                         status VARCHAR(10),
                         trader VARCHAR(125),
                         book VARCHAR(125),
                         creationName VARCHAR(125),
                         creationDate TIMESTAMP ,
                         revisionName VARCHAR(125),
                         revisionDate TIMESTAMP ,
                         dealName VARCHAR(125),
                         dealType VARCHAR(125),
                         sourceListId VARCHAR(125),
                         side VARCHAR(125),

                         PRIMARY KEY (BidListId)
);

CREATE TABLE Trade (
                       tradeId INTEGER NOT NULL AUTO_INCREMENT,
                       account VARCHAR(20) NOT NULL,
                       type VARCHAR(20) NOT NULL,
                       buyQuantity DOUBLE,
                       sellQuantity DOUBLE,
                       buyPrice DOUBLE ,
                       sellPrice DOUBLE,
                       tradeDate TIMESTAMP,
                       security VARCHAR(125),
                       status VARCHAR(10),
                       trader VARCHAR(125),
                       benchmark VARCHAR(125),
                       book VARCHAR(125),
                       creationName VARCHAR(125),
                       creationDate TIMESTAMP ,
                       revisionName VARCHAR(125),
                       revisionDate TIMESTAMP ,
                       dealName VARCHAR(125),
                       dealType VARCHAR(125),
                       sourceListId VARCHAR(125),
                       side VARCHAR(125),

                       PRIMARY KEY (tradeId)
);

CREATE TABLE Curvepoint (
                            id INTEGER NOT NULL AUTO_INCREMENT,
                            curveid INTEGER,
                            asofdate TIMESTAMP,
                            term DOUBLE ,
                            value DOUBLE ,
                            creationdate TIMESTAMP,

                            PRIMARY KEY (id)
);

CREATE TABLE Rating (
                        id INTEGER NOT NULL AUTO_INCREMENT,
                        moodysRating VARCHAR(20),
                        sandPRating VARCHAR(20),
                        fitchRating VARCHAR(20),
                        orderNumber INTEGER,

                        PRIMARY KEY (id)
);

CREATE TABLE Rulename (
                          id INTEGER NOT NULL AUTO_INCREMENT,
                          name VARCHAR(20),
                          description VARCHAR(30),
                          json VARCHAR(20),
                          template VARCHAR(20),
                          sqlStr VARCHAR(20),
                          sqlPart VARCHAR(20),

                          PRIMARY KEY (id)
);

CREATE TABLE Users (
                       id INTEGER NOT NULL AUTO_INCREMENT,
                       username VARCHAR(125),
                       password VARCHAR(125),
                       fullname VARCHAR(125),
                       role VARCHAR(125),

                       PRIMARY KEY (id)
);