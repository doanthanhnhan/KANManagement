CREATE TABLE Rooms(
	-- Create columns	
	RoomID varchar(10) UNIQUE NOT NULL,	
	RoomType varchar(20) NOT NULL,	
	PhoneNumber varchar(20) NOT NULL,
	RoomOnFloor int NOT NULL,
	RoomArea decimal NOT NULL,
	RoomStatus varchar(10) NOT NULL,
	Clean bit DEFAULT 1,
	Repaired bit DEFAULT 0,
	InProgress bit DEFAULT 0,
	Active bit DEFAULT 1 NOT NULL,
	-- Create constraint
	CONSTRAINT pk_RoomID PRIMARY KEY (RoomID)	
)

CREATE TABLE Customers(
	-- Create columns	
	CustomerID varchar(30) UNIQUE NOT NULL,	
	CustomerFirstName varchar(20) NOT NULL,
	CustomerMidName varchar(20) ,
	CustomerLastName varchar(20) NOT NULL,
	CustomerBirthday date NOT NULL,	
	CustomerPhoneNumber varchar(20) NOT NULL,
	CustomerPassport varchar(30),
	CustomerEmail varchar(100),	
	Active bit DEFAULT 1 NOT NULL,
	-- Create constraint
	CONSTRAINT pk_CustomerID PRIMARY KEY (CustomerID)
)
CREATE TABLE CheckInOrders(
	-- Create columns
	ID int IDENTITY(1,1),	
	CheckInID varchar(50) UNIQUE NOT NULL,
	CustomerID varchar(30) NOT NULL,
	RoomID varchar(10) NOT NULL,
	CheckInType varchar(20) NOT NULL,	
	NumberOfCustomer int NOT NULL,
	CheckInDate datetime NOT NULL,		
	CustomerPackage varchar(200),	
	Active bit DEFAULT 1 NOT NULL,
	-- Create constraint
	CONSTRAINT pk_CheckInOrdersID PRIMARY KEY (ID)
)
DROP TABLE CheckInOrders

CREATE TABLE CheckOutOrders(
	-- Create columns
	ID int IDENTITY(1,1),	
	CheckOutID varchar(50) UNIQUE NOT NULL,
	CheckInID varchar(50) UNIQUE NOT NULL,
	CustomerID varchar(30) NOT NULL,		
	RoomID varchar(10) NOT NULL,
	CheckInDate date NOT NULL,
	CheckOutDate date NOT NULL,		
	CustomerPayment varchar(50) NOT NULL,
	CustomerBill decimal NOT NULL,	
	Active bit DEFAULT 1 NOT NULL,
	-- Create constraint
	CONSTRAINT pk_CheckOutOrdersID PRIMARY KEY (ID)
)

CREATE TABLE ServicesOrders(
	-- Create columns
	ID int IDENTITY(1,1),	
	OrderID varchar(50) UNIQUE NOT NULL,
	RoomID varchar(10) NOT NULL,	
	ServiceID varchar(50) NOT NULL,
	ServiceQuantity decimal NOT NULL,
	ServiceOrderTime datetime NOT NULL,
	ServiceNote nvarchar(200),	
	Active bit DEFAULT 1 NOT NULL,
	-- Create constraint
	CONSTRAINT pk_ServicesOrdersRoomID PRIMARY KEY (ID)	
)

CREATE TABLE ServiceType(
	-- Create columns
	ID int IDENTITY(1,1),	
	ServiceID varchar(50) UNIQUE NOT NULL,
	ServiceName varchar(200) NOT NULL,	
	ServiceUnit varchar(10) NOT NULL,
	ServicePrice decimal NOT NULL,	
	Active bit DEFAULT 1 NOT NULL,
	-- Create constraint
	CONSTRAINT pk_ServiceID PRIMARY KEY (ID)	
)

CREATE TABLE UserLogs(
	-- Create columns
	ID int IDENTITY(1,1),
	UserName varchar(20) NOT NULL,	
	LogContent nvarchar(200) NOT NULL,
	LogTime datetime NOT NULL,
	Active bit DEFAULT 1 NOT NULL,
	-- Create constraint
	CONSTRAINT pk_UserLogID PRIMARY KEY (ID)
)

CREATE TABLE Departments(
	-- Create columns
	DepartmentID varchar(10) UNIQUE NOT NULL,	
	DepartmentName nvarchar(50) NOT NULL,
	Active bit DEFAULT 1 NOT NULL,
	-- Create constraint
	CONSTRAINT pk_DepartmentID PRIMARY KEY (DepartmentID)
)

CREATE TABLE Employees(
-- Create columns
	EmployeeID varchar(10) UNIQUE NOT NULL,	
	EmployeeFirstName nvarchar(15) NOT NULL,
	EmployeeMidName nvarchar(25),
	EmployeeLastName nvarchar(15) NOT NULL,
	DepartmentID varchar(10),
	PhoneNumber varchar(20),
	HireDate date,
	Job varchar(20),
	EducatedLevel smallint,
	Sex bit NOT NULL,
	Birthday date NOT NULL,
	Salary decimal,
	Bonus decimal,
	Comm decimal,
	Email varchar(100),
	Active bit DEFAULT 1,
	-- Create constraint
	CONSTRAINT pk_EmployeeID PRIMARY KEY (EmployeeID)
)

-- CREATE DATA FOR TESTING --
INSERT INTO Rooms (RoomID, RoomType, PhoneNumber, RoomOnFloor, RoomArea, RoomStatus, Clean, Repaired, InProgress) VALUES 
('R0101', 'Single', '67890101',1,20,'Available',1,1,1),
('R0102', 'Double', '67890102',1,30,'Reserved',0,1,1),
('R0103', 'Triple', '67890103',1,40,'Occupied',1,0,0),
('R0104', 'Family', '67890104',1,50,'Out',1,1,0),
('R0105', 'Special', '67890105',1,60,'Available',0,0,1),
('R0201', 'Special', '67890201',2,60,'Occupied',1,1,0),
('R0202', 'Single', '67890202',2,20,'Out',0,1,0),
('R0203', 'Triple', '67890203',2,40,'Reserved',1,1,0),
('R0204', 'Double', '67890204',2,30,'Available',1,0,0),
('R0205', 'Family', '67890205',2,50,'Available',0,1,1)
DELETE FROM Rooms
