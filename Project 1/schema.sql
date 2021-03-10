CREATE TABLE Person (

    sin CHAR(9) NOT NULL,
    name VARCHAR(30),
    address TEXT,
    phone CHAR(10) NOT NULL,

    CONSTRAINT "Person_pkey" PRIMARY KEY (sin)

);

CREATE TABLE Place (

    name VARCHAR(30) NOT NULL,
    address TEXT NOT NULL,
    gps POINT,
    description TEXT,

    CONSTRAINT "Place_pkey" PRIMARY KEY (name)

);

CREATE TABLE Time_Slot (
    time TIMESTAMP WITHOUT TIME ZONE NOT NULL,

    CONSTRAINT "Time_Slot_pkey" PRIMARY KEY (time)
);

CREATE TABLE Method (
    method VARCHAR(50) NOT NULL,

    CONSTRAINT "Method_pkey" PRIMARY KEY (method)
);

CREATE TABLE Action (
     action VARCHAR(30) NOT NULL,

    CONSTRAINT "Action_pkey" PRIMARY KEY (action)   
);

CREATE TABLE Test_Type (
    testtype VARCHAR(15) NOT NULL,

    CONSTRAINT "Test_Type_pkey" PRIMARY KEY (testtype)   
);

-- IS-A Relationship

CREATE TABLE Test_Centre (

    name VARCHAR(30) NOT NULL REFERENCES Place(name),

    CONSTRAINT "Test_Centre_pkey" PRIMARY KEY (name),

    CONSTRAINT "Test_Centre_fk_Place" FOREIGN KEY (name) REFERENCES Place

);

-- Weak Entities

CREATE TABLE Recon (
    sin CHAR(9) NOT NULL,
    method VARCHAR(50) NOT NULL,
    time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    placename VARCHAR(30) NOT NULL,

    CONSTRAINT "Recon_pkey" PRIMARY KEY (sin, method, time, placename),

    CONSTRAINT "Recon_fk_Person" FOREIGN KEY (sin) REFERENCES Person,
    CONSTRAINT "Recon_fk_Method" FOREIGN KEY (method) REFERENCES Method,
    CONSTRAINT "Recon_fk_TimeSlot" FOREIGN KEY (time) REFERENCES Time_Slot,
    CONSTRAINT "Recon_fk_Place" FOREIGN KEY (placename) REFERENCES Place(name)
);

CREATE TABLE Test (
    sin CHAR(9) NOT NULL,
    time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    testtype VARCHAR(15),
    action VARCHAR(30),
    testcentre VARCHAR(30),

    CONSTRAINT "Test_pkey" PRIMARY KEY (sin, time),

    CONSTRAINT "Test_fk_Person" FOREIGN KEY (sin) REFERENCES Person,
    CONSTRAINT "Test_fk_Time_Slot" FOREIGN KEY (time) REFERENCES Time_Slot,
    CONSTRAINT "Test_fk_Test_Type" FOREIGN KEY (testtype) REFERENCES Test_Type,
    CONSTRAINT "Test_fk_Action" FOREIGN KEY (action) REFERENCES Action,
    CONSTRAINT "Test_fk_Test_Centre" FOREIGN KEY (testcentre) REFERENCES Test_Centre(name)
);

-- Many-to-Many Relationships


CREATE TABLE Bubble (
    p1 CHAR(9) NOT NULL,
    p2 CHAR(9) NOT NULL,

    CONSTRAINT "Bubble_pkey" PRIMARY KEY (p1, p2),

    CONSTRAINT "Bubble_fk_Person1" FOREIGN KEY (p1) REFERENCES Person(sin),
    CONSTRAINT "Bubble_fk_Person2" FOREIGN KEY (p2) REFERENCES Person(sin)
);

CREATE TABLE Offer (
    testtype VARCHAR(15) NOT NULL,
    testcentre VARCHAR(30) NOT NULL,

    CONSTRAINT "Offers_pkey" PRIMARY KEY (testtype, testcentre),

    CONSTRAINT "Offers_fk_Test_Type" FOREIGN KEY (testtype) REFERENCES Test_Type,
    CONSTRAINT "Offers_fk_Test_Centre" FOREIGN KEY (testcentre) REFERENCES Test_Centre(name)
);
