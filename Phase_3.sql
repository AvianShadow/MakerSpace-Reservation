CREATE TABLE Customer (
	CID			INT			NOT NULL,
	Name			Varchar(30)		NOT NULL,
	Age			INT			NOT NULL,
	Primary Key(CID),
	CHECK (Age >= 18)
);
CREATE TABLE Supplier (
	SID			INT			NOT NULL,
	Product		varchar(30)		NOT NULL,
	Price			DOUBLE		NOT NULL,
	Primary Key(SID)
);

CREATE TABLE Schedules (
	SID2			INT			NOT NULL,
	CID			INT,
	StartDate		Varchar(10)		NOT NULL,
	EndDate		Varchar(10)		NOT NULL,
	StartTime		Varchar(6)		NOT NULL,
	EndTime		Varchar(6)		NOT NULL,
	Primary Key(SID2),
	Foreign Key(CID) References Customer(CID)
		ON DELETE cascade			ON UPDATE cascade,
	CHECK(StartDate < EndDate OR (StartDate = EndDate AND StartTime <= EndTime))
);

CREATE TABLE Printer (
	PID			INT			NOT NULL,
    SID			INT			NOT NULL,
	FilamentAmt		DOUBLE		NOT NULL		Default(0),
	Filament		varchar(30)		NOT NULL		Default("PLA"),
	Primary Key(PID),
	Foreign Key(SID) References Supplier(SID)
		ON DELETE cascade		ON UPDATE cascade
);


CREATE TABLE Employee (
	EID			INT			NOT NULL,
	Name 			varchar(30)		NOT NULL,

	Primary Key(EID)
);

CREATE TABLE Report (
	RID			INT			NOT NULL,
    EID			INT			NOT NULL,
	SID			INT			NOT NULL,
	ReportDate			Varchar(10)		NOT NULL,
	History			DOUBLE		NOT NULL,
	Transaction		INT,			
	Primary Key(RID),
    Foreign Key(EID) References Employee(EID)
		ON DELETE cascade		ON UPDATE cascade,
	Foreign Key(SID) References Supplier(SID)
		ON DELETE cascade		ON UPDATE cascade
);


CREATE TABLE Reserves (
	SID2	INT,
    CID		INT,
    PID		INT,
    Primary Key(SID2, CID, PID),
    Foreign Key(SID2) References Schedules(SID2)
		ON DELETE cascade		ON UPDATE cascade,
	Foreign Key(CID) References Customer(CID)
		ON DELETE cascade		ON UPDATE cascade,
	Foreign Key(PID) References Printer(PID)
		ON DELETE cascade		ON UPDATE cascade
);


INSERT INTO Customer (CID, Name, Age)
VALUES (1, "James R. Hill", 21);
INSERT INTO Customer (CID, Name, Age)
VALUES (2, "Enrique M. Brown", 35);
INSERT INTO Customer (CID, Name, Age)
VALUES (3, "Lonnie K. Timmons", 19);
INSERT INTO Customer (CID, Name, Age)
VALUES (4, "Paul M. Numbers", 34);
INSERT INTO Customer (CID, Name, Age)
VALUES (5, "Dennis L. Villanueva", 23);

INSERT INTO Supplier (SID, Product, Price)
VALUES (1, "PLA", 15.19);
INSERT INTO Supplier (SID, Product, Price)
VALUES (2, "PLA", 21.00);
INSERT INTO Supplier (SID, Product, Price)
VALUES (3, "PETG", 27.00);
INSERT INTO Supplier (SID, Product, Price)
VALUES (4, "PETG", 18.99);
INSERT INTO Supplier (SID, Product, Price)
VALUES (5, "PETG", 19.99);
INSERT INTO Supplier (SID, Product, Price)
VALUES (6, "ABS", 32.00);



INSERT INTO Schedules (SID2, CID, StartDate, EndDate, StartTime, EndTime)
VALUES (1, 3, "11/17/2022", "11/17/2022", "11:30", "16:30");
INSERT INTO Schedules (SID2, CID, StartDate, EndDate, StartTime, EndTime)
VALUES (2, 5, "11/22/2022", "11/23/2022", "14:30", "12:00");
INSERT INTO Schedules (SID2, CID, StartDate, EndDate, StartTime, EndTime)
VALUES (3, 1, "11/18/2022", "11/19/2022", "10:00", "11:00");
INSERT INTO Schedules (SID2, CID, StartDate, EndDate, StartTime, EndTime)
VALUES (4, 2, "11/24/2022", "11/26/2022", "13:00", "11:00");
INSERT INTO Schedules (SID2, CID, StartDate, EndDate, StartTime, EndTime)
VALUES (5, 4, "11/14/2022", "11/15/2022", "9:30", "12:00");

INSERT INTO Printer (PID, SID, FilamentAmt, Filament)
VALUES (1, 6, 1, "PLA");
INSERT INTO Printer (PID, SID, FilamentAmt, Filament)
VALUES (2, 5, 0.7, "PTEG");
INSERT INTO Printer (PID, SID, FilamentAmt, Filament)
VALUES (3, 4, 0.5, "TPE");
INSERT INTO Printer (PID, SID, FilamentAmt, Filament)
VALUES (4, 3, 0.9, "ABS");
INSERT INTO Printer (PID, SID, FilamentAmt, Filament)
VALUES (5, 2, 0.2, "ASA");
INSERT INTO Printer (PID, SID, FilamentAmt, Filament)
VALUES (6, 1, 1, "PA");

INSERT INTO Employee (EID, Name)
VALUES (1, "Marie T. Hayes");
INSERT INTO Employee (EID, Name)
VALUES (2, "Michael B. Stafford");
INSERT INTO Employee (EID, Name)
VALUES (3, "Wendell M. Rosario");
INSERT INTO Employee (EID, Name)
VALUES (4, "Raymond A. Wilcox");
INSERT INTO Employee (EID, Name)
VALUES (5, "John R. Olvera");

INSERT INTO Report (RID, EID, ReportDate, Transaction, History, SID)
VALUES (4, 1, "11/01/2022", 10, 0.75, 4);
INSERT INTO Report (RID, EID, ReportDate, Transaction, History, SID)
VALUES (3, 2, "11/03/2022", 11, 0.8, 1);
INSERT INTO Report (RID, EID, ReportDate, Transaction, History, SID)
VALUES (5, 3, "10/24/2022", 19, 0.1, 3);
INSERT INTO Report (RID, EID, ReportDate, Transaction, History, SID)
VALUES (1, 4, "11/04/2022", 8, 0.5, 1);
INSERT INTO Report (RID, EID, ReportDate, Transaction, History, SID)
VALUES (6, 5, "10/17/2022", 9, 0.75, 5);
INSERT INTO Report (RID, EID, ReportDate, Transaction, History, SID)
VALUES (2, 3, "10/21/2022", 10, 0.8, 2);

INSERT INTO Reserves (SID2, CID, PID)
VALUES (1, 2, 3);
INSERT INTO Reserves (SID2, CID, PID)
VALUES (2, 1, 2);
INSERT INTO Reserves (SID2, CID, PID)
VALUES (3, 3, 1);
INSERT INTO Reserves (SID2, CID, PID)
VALUES (4, 4, 5);
INSERT INTO Reserves (SID2, CID, PID)
VALUES (5, 5, 4);

SELECT 
	PID, Filament, FilamentAmt
FROM
	Printer
WHERE
	FilamentAmt > 0.5;
        
SELECT
	SID, Price, Product
FROM
	Supplier
ORDER BY
	Price;
        
Select
	PID, SID
From
	Printer
Where
	 SID <= 3;

CREATE VIEW list_usage AS
SELECT Printer.PID, Schedules.SID2, StartDate, EndDate, Filament, FilamentAmt
FROM Schedules, Printer, Reserves
WHERE Printer.PID = Reserves.PID AND Reserves.SID2 = Schedules.SID2;
    
CREATE VIEW accumulated_usage AS
SELECT 	Reserves.PID, Count(Reserves.CID)
FROM	Printer, Customer, Reserves
Where 	Printer.PID = Reserves.PID AND Customer.CID = Reserves.CID
GROUP BY Reserves.PID;

CREATE VIEW Above_avg_price AS
SELECT Product, Price
FROM Supplier
WHERE Price > (SELECT AVG(Price) FROM Supplier)



