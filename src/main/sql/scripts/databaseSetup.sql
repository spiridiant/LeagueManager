CREATE TABLE Sponsor
(
    SID    INTEGER PRIMARY KEY,
    Name   VARCHAR NOT NULL,
    Slogan VARCHAR
);

CREATE TABLE Division
(
    Division_Name VARCHAR,
    Conference    VARCHAR,
    FOREIGN KEY (Division_Name)
        REFERENCES Team
);

CREATE TABLE Team
(
    Name          VARCHAR,
    Cap_Space     VARCHAR,
    Arena         VARCHAR,
    City          VARCHAR,
    Division_Name VARCHAR NOT NULL,
    PRIMARY KEY (Name, City)
);

League
(
Name 				VARCHAR PRIMARY KEY,
  	Revenue 			INTEGER,
  	Commissioner		VARCHAR
);

CREATE TABLE Location
(
    Venue VARCHAR PRIMARY KEY,
    City  VARCHAR PRIMARY KEY
);

CREATE TABLE Session
(
    Venue VARCHAR,
    Date  DATE,
    PRIMARY KEY (Venue)
        FOREIGN KEY (Venue)
        REFERENCES Location
);

CREATE TABLE Season
(
    Start_Date DATE,
    LName      VARCHAR,
    End_Date   DATE,
    PRIMARY KEY (Start_Date, LName),
    FOREIGN KEY (LName)
        REFERENCE League
);

CREATE TABLE Staff
(
    StID   INTEGER PRIMARY KEY,
    Name   VARCHAR NOT NULL,
    Salary INTEGER
)

CREATE TABLE Doctor
(
    StID  INTEGER PRIMARY KEY,
    Field VARCHAR
);

CREATE TABLE Coach
(
    StID            INTEGER PRIMARY KEY,
    number_of_rings INTEGER
);

CREATE TABLE Trainer
(
    StID      INTEGER PRIMARY KEY,
    Specialty VARCHAR
);

CREATE TABLE Manager
(
    StID                INTEGER PRIMARY KEY,
    Years_of_Experience INTEGER
);



CREATE TABLE Signed_Contract
(
    Bonus       INTEGER,
    PID         INTEGER NOT NULL,
    Length      INTEGER,
    Value       INTEGER,
    Signed_Date DATE,
    CID         INTEGER PRIMARY KEY,
    FOREIGN KEY (PID) REFERENCES Player
        ON DELETE SET NULL
        ON UPDATE CASCADE
);
CREATE TABLE Player_Plays_for_Team
(
    Debut_Year    DATE,
    Date_of_Birth DATE,
    Height        INTEGER,
    Name          VARCHAR NOT NULL,
    Jersey#       INTEGER,
    PID           INTEGER PRIMARY KEY,
    TName         VARCHAR,
    City          VARCHAR,
    FOREIGN KEY (TName, City) REFERENCES Team
        ON DELETE SET NULL
        ON UPDATE CASCADE
);

CREATE TABLE Sponsor_Sponsors_Team
(
    Sponsored_Amount INTEGER,
    SID              INTEGER,
    TName            VARCHAR,
    City             VARCHAR,
    PRIMARY KEY (TName, City, SID)
        FOREIGN KEY (SID) REFERENCES Sponsor
        FOREIGN KEY (TName, City) REFERENCES TEAM
);

CREATE TABLE Works_For
(
    StID  INTEGER,
    TName VARCHAR,
    City  VARCHAR,
    PRIMARY KEY (StID, TName, City)
        FOREIGN KEY (StID) REFERENCES Staff
        FOREIGN KEY (TName, City) REFERENCES Team
);

CREATE TABLE Team_Plays_Team
(
    HomeTeamName VARCHAR,
    HomeTeamCity VARCHAR,
    AwayTeamName VARCHAR,
    AwayTeamCity VARCHAR,
    Date         DATE,
    Venue        VARCHAR,
    PRIMARY KEY (HomeTeamName, HomeTeamCity, AwayTeamName, AwayTeamCity, Date, Venue)
        FOREIGN KEY (HomeTeamName, HomeTeamCity, AwayTeamName, AwayTeamCity)
        REFERENCES Team
        FOREIGN KEY (Date, Venue) REFERENCES Game
);

CREATE TABLE Happens_In
(
    Start_Date date,
    CName      VARCHAR,
    Venue      VARCHAR,
    Play_Date  VARCHAR,
    PRIMARY KEY (Start_Date, CName, Venue, Play_Date),
    FOREIGN KEY (Start_Date) REFERENCES Season,
    FOREIGN KEY (Venue, Date) REFERENCES Game
);

CREATE TABLE Wins_Season
(
    Start_Date date,
    TName      VARCHAR,
    City       VARCHAR,
    PRIMARY KEY (Start_Date, TName, City),
    FOREIGN KEY (Start_Date) REFERENCES Season,
    FOREIGN KEY (TName, City) REFERENCES Team
);



-- Sponsor table
INSERT INTO Sponsor
VALUES (1, 'EcoFusion Solutions', 'Harmony in Every Innovation – Where Green Meets Ingenuity'),
       (2, 'Quantum Quench Beverages', 'Taste the Future, One Quantum Leap at a Time!'),
       (3, 'Galactic Gearworks', 'Universe\'s Finest Creations, Engineered for the Stars!'),
       (4, 'Paws & Play Pet Resorts', 'Where Tails Wag and Whiskers Purr – Your Pet\'s Paradise!'),
       (5, 'InfiniTech Labs', 'Limitless Possibilities, Crafted in Every Code!');

-- Division table
INSERT INTO Division
VALUES ('Atlantic', 'Eastern'),
       ('Central', 'Eastern'),
       ('Pacific', 'Western'),
       ('Southwest', 'Western'),
       ('Southeast', 'Eastern');

-- Team table
INSERT INTO Team
VALUES ('Toronto Raptors', 136000000, 'ScotiaBank Arena', 'Toronto', 'Atlantic'),
       ('Indiana Pacers', 136123345, 'Gainbridge Fieldhouse', 'Indiana', 'Central'),
       ('Oklahoma City Thunder', 123123123, 'Paycom Center', 'Oklahoma City', 'Northwest'),
       ('Los Angeles Lakers', 139242132, 'Crypto Arena', 'Los Angeles', 'Pacific'),
       ('Los Angeles Clippers', 139242134, 'Crypto Arena', 'Los Angeles', 'Pacific');

-- League table
INSERT INTO League
VALUES ('NBA', 1234000123, 'Adam Silver'),
       ('WNBA', 123400012, 'Cathy Engelbert'),
       ('G-League', 1234001, 'Shareef Abdur-Rahim'),
       ('NBA', 2234000123, 'David Stern'),
       ('NBA', 9234000123, 'Lebron James');

-- Location table
INSERT INTO Location
VALUES ('Madison Square Garden', 'New York City'),
       ('Crypto Arena', 'Los Angeles'),
       ('ScotiaBank Arena', 'Toronto'),
       ('Paycom Center', 'Oklahoma City'),
       ('Gainbridge Fieldhouse', 'Indiana');

INSERT INTO Session
VALUES ('Madison Square Garden', '2022-02-03'),
       ('Crypto Arena', '2012-05-13'),
       ('Scotia Bank Arena', '2011-09-08'),
       ('Paycom Center', '2022-01-14'),
       ('Gainbridge Fieldhouse', '2006-05-23');

INSERT INTO Season
VALUES ('2022-08-23', 'NBA', '2023-06-20'),
       ('2020-08-27', 'NBA', '2023-06-23'),
       ('2022-08-26', 'NBA', '2023-06-26'),
       ('2022-08-24', 'NBA', '2023-06-29'),
       ('2022-08-29', 'NBA', '2023-06-21');

INSERT INTO Staff
VALUES
    (1, 'John Smith', 50000), (2, 'Jane Doe', 55000), (3, 'Michael Johnson', 60000), (4, 'Emily Davis', 48000), (5, 'Christopher Lee', 52000);

INSERT INTO Doctor
VALUES (1, 'Cardiology'),
       (2, 'Pediatrics'),
       (3, 'Neurology'),
       (4, 'Orthopedics'),
       (5, 'Psychiatry');

INSERT INTO Coach
VALUES (1, 3),
       (2, 5),
       (3, 2),
       (4, 4),
       (5, 6);

INSERT INTO Trainer
VALUES (1, 'Strength and Conditioning'),
       (2, 'Sports Rehabilitation'),
       (3, 'Nutrition and Dietetics'),
       (4, 'Yoga and Flexibility'),
       (5, 'High-Intensity Interval Training');



INSERT INTO Manager
VALUES (1, 15),
       (2, 12),
       (3, 20),
       (4, 18),
       (5, 22);

INSERT INTO Signed_Contract
VALUES (50000, 1, 5, 1000000, '2023-10-20', 1),
       (60000, 2, 4, 800000, '2023-10-21', 2),
       (70000, 3, 6, 1200000, '2023-10-22', 3),
       (55000, 4, 3, 700000, '2023-10-23', 4),
       (65000, 5, 7, 1500000, '2023-10-24', 5);


INSERT INTO Player_Plays_For_Team
VALUES ('2018-06-21', '1999-02-28', 201, 'Luka Doncic', 77, 2473875, 'Mavericks', 'Dallas'),
       ('2015-06-25', '1993-03-14', 203, 'Stephen Curry', 30, 1729634, 'Warriors', 'Golden State'),
       ('2014-06-26', '1988-12-30', 206, 'LeBron James', 23, 1248756, 'Lakers', 'Los Angeles'),
       ('2013-06-27', '1989-03-29', 201, 'Anthony Davis', 3, 1342351, 'Lakers', 'Los Angeles'),
       ('2016-06-23', '1994-03-16', 193, 'Giannis Antetokounmpo', 34, 1534897, 'Bucks', 'Milwaukee');

INSERT INTO Sponsor_Sponsors_Team
VALUES (872913, 37, 'Bucks', 'Milwaukee'),
       (561024, 19, 'Knicks', 'New York'),
       (315789, 25, 'Clippers', 'Los Angeles'),
       (999999, 12, 'Heat', 'Miami'),
       (746821, 30, 'Suns', 'Phoenix');

INSERT INTO Works_For
VALUES (1, 'Bulls', 'Chicago'),
       (7, 'Lakers', 'Los Angeles'),
       (22, 'Celtics', 'Boston'),
       (11, 'Raptors', 'Toronto'),
       (4, 'Nuggets', 'Denver');


INSERT INTO Team_Plays_Team
VALUES ('Bulls', 'Chicago', 'Hornets', 'Charlotte', '2022-03-26', 'United Center'),
       ('Rockets', 'Houston', 'Grizzlies', 'Memphis', '2023-08-14', 'FedEx Forum'),
       ('Suns', 'Phoenix', 'Warriors', 'San Francisco', '2024-06-02', 'Chase Center'),
       ('Timberwolves', 'Minneapolis', 'Jazz', 'Salt Lake City', '2023-11-19', 'Vivint Arena'),
       ('Thunder', 'Oklahoma City', 'Mavericks', 'Dallas', '2024-04-27', 'American Airlines Center');

INSERT INTO Happens_In
VALUES ('2022-02-03', 'Chicago', 'United Center', '1967-01-04'),
       ('1967-01-04', 'Detroit', 'Little Caesars Arena', '2000-12-20'),
       ('2000-11-13', 'Washington', 'Capital One Arena', '2010-05-28'),
       ('1987-10-05', 'Los Angeles', 'Staples Center', '1998-03-17'),
       ('2021-04-05', 'Charlotte', 'Spectrum Center', '2003-09-11');

INSERT INTO Wins_Season
VALUES ('2022-02-03', 'Bulls', 'Chicago'),
       ('1967-01-04', 'Pistons', 'Detroit'),
       ('2000-11-13', 'Wizards', 'Washington'),
       ('1987-10-05', 'Lakers', 'Los Angeles'),
       ('2021-04-05', 'Hornets', 'Charlotte');

