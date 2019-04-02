CREATE TABLE Rooms(
	-- Create columns	
	RoomID varchar(100) NOT NULL,
	CustomerID varchar(100) NOT NULL,
	UserName varchar(100) NOT NULL,	
	RoomType varchar(100) NOT NULL,	
	PhoneNumber varchar(20) NOT NULL,
	RoomOnFloor tinyint NOT NULL,
	RoomArea decimal(8,3) NOT NULL,
	RoomStatus varchar(10) NOT NULL,
	DayRemaining int DEFAULT 0,
	BookingDate datetime DEFAULT GETDATE(),
	CheckInDate datetime DEFAULT GETDATE(),
	LeaveDate datetime DEFAULT GETDATE(),
	Clean bit DEFAULT 1,
	Repaired bit DEFAULT 1,
	InProgress bit DEFAULT 0,
	Active bit DEFAULT 1,
	-- Create constraint
	CONSTRAINT pk_RoomID_Rooms PRIMARY KEY (RoomID),
	CONSTRAINT uc_RoomID_Rooms UNIQUE (RoomID)	
)
DROP TABLE Rooms
CREATE TABLE RoomType(
	-- Create columns		
	RoomType varchar(100) NOT NULL,	
	Price decimal(18,3) NOT NULL,
	Discount decimal(3,2) DEFAULT 0,
	Active bit DEFAULT 1,
	-- Create constraint
	CONSTRAINT pk_RoomType_RoomType PRIMARY KEY (RoomType),
	CONSTRAINT uc_RoomType_RoomType UNIQUE (RoomType)
)
CREATE TABLE Customers(
	-- Create columns	
	CustomerID varchar(100) NOT NULL,
	UserName varchar(100) NOT NULL,	
	CustomerFirstName nvarchar(20) NOT NULL,
	CustomerMidName nvarchar(20) ,
	CustomerLastName nvarchar(20) NOT NULL,
	CustomerBirthday date NOT NULL,	
	CustomerPhoneNumber varchar(20),
	CustomerPassport varchar(30),
	CustomerEmail varchar(100),
	Discount decimal(3,2) DEFAULT 0,
	Company nvarchar(100),	
	Active bit DEFAULT 1,
	Sex bit,
	-- Create constraint
	CONSTRAINT pk_CustomerID_Customers PRIMARY KEY (CustomerID),
	CONSTRAINT uc_CustomerID_Customers UNIQUE (CustomerID)
)
DROP TABLE Customers

CREATE TABLE BookingInfo (
	-- Create columns
	BookingID varchar(100) NOT NULL,
	CustomerID varchar(100) NOT NULL,	
	RoomID varchar(100) NOT NULL,
	UserName varchar(100) NOT NULL,
	Note nvarchar(1000),	
	NumberGuest tinyint not null,
	DateBook date not null	
	-- Create constraint
	CONSTRAINT pk_BookingID_BookingInfo PRIMARY KEY (BookingID),
	CONSTRAINT uc_BookingID_BookingInfo UNIQUE (BookingID)
)
DROP TABLE BookingInfo

CREATE TABLE CheckInOrders(
	-- Create columns	
	CheckInID varchar(100) NOT NULL,
	BookingID varchar(100) NOT NULL,
	CustomerID varchar(100) NOT NULL,
	RoomID varchar(100) NOT NULL,
	UserName varchar(100) NOT NULL,
	CheckInType varchar(20) NOT NULL,		
	NumberOfCustomer tinyint NOT NULL,
	CheckInDate datetime NOT NULL,
	LeaveDate datetime NOT NULL,		
	CustomerPackage nvarchar(200),	
	Active bit DEFAULT 1,
	-- Create constraint
	CONSTRAINT pk_CheckInID_CheckInOrders PRIMARY KEY (CheckInID),
	CONSTRAINT uc_CheckInID_CheckInOrders UNIQUE (CheckInID)
)
DROP TABLE CheckInOrders

CREATE TABLE CheckOutOrders(
	-- Create columns		
	CheckOutID varchar(100) NOT NULL,
	CheckInID varchar(100) NOT NULL,
	CustomerID varchar(100) NOT NULL,		
	RoomID varchar(100) NOT NULL,
	UserName varchar(100) NOT NULL,
	CheckInDate date NOT NULL,
	CheckOutDate date NOT NULL,		
	CustomerPayment nvarchar(50) NOT NULL,
	CustomerBill decimal(18,3) NOT NULL,
	Discount decimal(4,3) DEFAULT 0,
	Tax decimal(6,3) DEFAULT 0,	
	Active bit DEFAULT 1 NOT NULL,
	-- Create constraint
	CONSTRAINT pk_CheckOutID_CheckOutOrders PRIMARY KEY (CheckOutID),
	CONSTRAINT uc_CheckOutID_CheckOutOrders UNIQUE (CheckOutID),
	CONSTRAINT uc_CheckInID_CheckOutOrders UNIQUE (CheckInID)
)
DROP TABLE CheckOutOrders

CREATE TABLE ServicesOrders(
	-- Create columns		
	OrderID varchar(100) NOT NULL,
	CustomerID varchar(100) NOT NULL,
	RoomID varchar(100) NOT NULL,
	UserName varchar(100) NOT NULL,	
	ServiceOrderDate datetime NOT NULL,
	ServiceNote nvarchar(200),	
	Active bit DEFAULT 1 NOT NULL,
	-- Create constraint
	CONSTRAINT pk_OrderID_ServiceOrders PRIMARY KEY (OrderID),
	CONSTRAINT uc_OrderID_ServiceOrders UNIQUE (OrderID)	
)
DROP TABLE ServicesOrders

CREATE TABLE ServicesOrderDetails(
	-- Create columns
	ID int IDENTITY(1,1),	
	OrderID varchar(100) NOT NULL,
	ServiceID varchar(100) NOT NULL,
	UserName varchar(100) NOT NULL,	
	ServiceQuantity int NOT NULL,
	Price decimal(18,3) NOT NULL,	
	Discount float,
	Active bit DEFAULT 1 NOT NULL,
	-- Create constraint
	CONSTRAINT pk_ID_ServicesOrderDetails PRIMARY KEY (ID)		
)
DROP TABLE ServicesOrderDetails

CREATE TABLE ServiceType(
	-- Create columns		
	ServiceID varchar(100) NOT NULL,
	UserName varchar(100) NOT NULL,
	ServiceName nvarchar(100) NOT NULL,	
	ServiceUnit nvarchar(20) NOT NULL,
	ServicePrice decimal(18,3) NOT NULL,
	ServiceInventory int NOT NULL,
	ImportQuantity int DEFAULT 0,
	ImportDate datetime DEFAULT GETDATE(),
	ExportQuantity int DEFAULT 0,
	ExportDate datetime DEFAULT GETDATE(),
	ServiceDescription nvarchar(500),
	[Image] varbinary(MAX),	
	Active bit DEFAULT 1 NOT NULL,
	-- Create constraint
	CONSTRAINT pk_ServiceID_ServiceType PRIMARY KEY (ServiceID),
	CONSTRAINT uc_ServiceID_ServiceType UNIQUE (ServiceID),
	CONSTRAINT uc_ServiceName_ServiceType UNIQUE (ServiceName)
)
DROP TABLE ServiceType

CREATE TABLE UserLogs(
	-- Create columns
	ID int IDENTITY(1,1),
	UserName varchar(100) NOT NULL,	
	LogContent nvarchar(200) NOT NULL,
	LogTime datetime NOT NULL,
	Active bit DEFAULT 1 NOT NULL,
	-- Create constraint
	CONSTRAINT pk_ID_UserLogs PRIMARY KEY (ID)
)

DROP TABLE Departments
CREATE TABLE Departments(
	-- Create columns
	DepartmentID varchar(100) NOT NULL,		
	DepartmentName nvarchar(100) NOT NULL,
	Active bit DEFAULT 1 NOT NULL,
	-- Create constraint
	CONSTRAINT pk_DepartmentID_Departments PRIMARY KEY (DepartmentID),
	CONSTRAINT uc_DepartmentID_Departments UNIQUE (DepartmentID)
)

CREATE TABLE Employees(
-- Create columns
	EmployeeID varchar(100) NOT NULL,	
	EmployeeFirstName nvarchar(15) NOT NULL,
	EmployeeMidName nvarchar(25),
	EmployeeLastName nvarchar(15) NOT NULL,
	DepartmentID varchar(100) DEFAULT 'Free',
	PhoneNumber varchar(20),
	[Address] nvarchar(100),
	IDNumber varchar(20),
	HireDate date,
	Job varchar(20),
	EducatedLevel smallint,
	Sex bit NOT NULL,
	Birthday date,
	Salary decimal,
	Bonus decimal,
	Comm decimal,
	Email varchar(100),
	Active bit DEFAULT 1,	
	[Image] varbinary(MAX),
	-- Create constraint
	CONSTRAINT pk_EmployeeID_Employees PRIMARY KEY (EmployeeID),
	CONSTRAINT uc_EmployeeID_Employees UNIQUE (EmployeeID)	
)

CREATE TABLE Users(
	-- Create columns
	ID_User int NOT NULL,
	EmployeeID varchar(100) NOT NULL,
	UserName varchar(100),
	[PassWord] varchar(255),
	Active bit NOT NULL,
	Secret_Question nvarchar(100),
	Secret_Answer nvarchar(100),
	Check_Login int,
	Check_Time date,
	-- Create constraint
	CONSTRAINT pk_ID_User_Users PRIMARY KEY (ID_User),
	CONSTRAINT uc_EmployeeID_Users UNIQUE (EmployeeID),
	CONSTRAINT uc_UserName_Users UNIQUE (UserName)
)

CREATE TABLE [Role](
	-- Create columns
	RoleID int IDENTITY(1,1),	
	EmployeeID varchar(100) NOT NULL,
	Employee_View bit DEFAULT 0,
	Employee_Add bit DEFAULT 0,
	Employee_Edit bit DEFAULT 0,
	Employee_Delete bit DEFAULT 0,
	User_View bit DEFAULT 0,
	User_Add bit DEFAULT 0,
	User_Edit bit DEFAULT 0,
	User_Delete bit DEFAULT 0,
	Booking_View bit DEFAULT 0,
	Booking_Add bit DEFAULT 0,
	Booking_Edit bit DEFAULT 0,
	Booking_Delete bit DEFAULT 0,
	CheckIn_View bit DEFAULT 0,
	CheckIn_Add bit DEFAULT 0,
	CheckIn_Edit bit DEFAULT 0,
	CheckIn_Delete bit DEFAULT 0,
	CheckOut_View bit DEFAULT 0,
	CheckOut_Add bit DEFAULT 0,
	CheckOut_Edit bit DEFAULT 0,
	CheckOut_Delete bit DEFAULT 0,
	Customer_View bit DEFAULT 0,
	Customer_Add bit DEFAULT 0,
	Customer_Edit bit DEFAULT 0,
	Customer_Delete bit DEFAULT 0,
	Department_View bit DEFAULT 0,
	Department_Add bit DEFAULT 0,
	Department_Edit bit DEFAULT 0,
	Department_Delete bit DEFAULT 0,
	Role_View bit DEFAULT 0,
	Role_Add bit DEFAULT 0,
	Role_Edit bit DEFAULT 0,
	Role_Delete bit DEFAULT 0,
	Room_View bit DEFAULT 0,
	Room_Add bit DEFAULT 0,
	Room_Edit bit DEFAULT 0,
	Room_Delete bit DEFAULT 0,
	SODetail_View bit DEFAULT 0,
	SODetail_Add bit DEFAULT 0,
	SODetail_Edit bit DEFAULT 0,
	SODetail_Delete bit DEFAULT 0,
	SOder_View bit DEFAULT 0,
	SOder_Add bit DEFAULT 0,
	SOder_Edit bit DEFAULT 0,
	SOder_Delete bit DEFAULT 0,
	SType_View bit DEFAULT 0,
	SType_Add bit DEFAULT 0,
	SType_Edit bit DEFAULT 0,
	SType_Delete bit DEFAULT 0,
	UserLog_View bit DEFAULT 0,
	UserLog_Add bit DEFAULT 0,
	UserLog_Edit bit DEFAULT 0,
	UserLog_Delete bit DEFAULT 0,
	ReActive_View bit DEFAULT 0,
	ReActive_Add bit DEFAULT 0,
	ReActive_Edit bit DEFAULT 0,
	ReActive_Delete bit DEFAULT 0
	-- Create constraint
	CONSTRAINT pk_RoleID_Role PRIMARY KEY (RoleID),
	CONSTRAINT uc_RoleID_Role UNIQUE (RoleID),
	CONSTRAINT uc_EmployeeID_Role UNIQUE (EmployeeID)
)

CREATE TABLE Bill (
	-- Create columns
	ID int IDENTITY(1,1),	
	RoomID varchar(100) NOT NULL,
	CustomerID varchar(100) NOT NULL,
	UserName varchar(100) NOT NULL,	
	CheckInID varchar(100) NOT NULL,
	CheckOutID varchar(100) NOT NULL,	
	CheckInDate datetime NOT NULL,
	CheckOutDate datetime NOT NULL,
	NoOfDay int NOT NULL,
	RoomPrice decimal(18,3) NOT NULL,
	RoomCharge decimal(18,3) NOT NULL,
	ServiceCharge decimal(18,3) NOT NULL,
	RoomDiscount decimal(18,3) NOT NULL,
	ServiceDiscount decimal(18,3) NOT NULL,
	CustomerDiscount decimal (18,3) NOT NULL,
	TotalBillAmount decimal(18,3) NOT NULL,
	VATAmount decimal(18,3) NOT NULL,
	PayableAmount decimal(18,3) NOT NULL,
	CustomerGive decimal(18,3) NOT NULL,
	CustomerChange decimal(18,3) NOT NULL,
	QRCode varbinary(MAX),
	Active bit DEFAULT 1
	-- Create constraint
	CONSTRAINT pk_ID_Bill PRIMARY KEY (ID)
)

-- CREATE CONSTRAINT FOR TABLES --
ALTER TABLE Users
ADD CONSTRAINT fk_EmployeeID_Users FOREIGN KEY (EmployeeID) REFERENCES Employees(EmployeeID);

ALTER TABLE [Role]
ADD CONSTRAINT fk_EmployeeID_Role FOREIGN KEY (EmployeeID) REFERENCES Employees(EmployeeID);

ALTER TABLE Employees
ADD CONSTRAINT fk_DepartmentID_Employees FOREIGN KEY (DepartmentID) REFERENCES Departments(DepartmentID);

ALTER TABLE Rooms
ADD CONSTRAINT fk_CustomerID_Rooms FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID),
	CONSTRAINT fk_UserName_Rooms FOREIGN KEY (UserName) REFERENCES Users(UserName),
	CONSTRAINT fk_RoomType_Rooms FOREIGN KEY (RoomType) REFERENCES RoomType(RoomType);

ALTER TABLE BookingInfo
ADD CONSTRAINT fk_CustomerID_BookingInfo FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID),
	CONSTRAINT fk_RoomID_BookingInfo FOREIGN KEY (RoomID) REFERENCES Rooms(RoomID),
	CONSTRAINT fk_UserName_BookingInfo FOREIGN KEY (UserName) REFERENCES Users(UserName);


ALTER TABLE CheckInOrders
ADD CONSTRAINT fk_CustomerID_CheckInOrders FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID),
	CONSTRAINT fk_RoomID_CheckInOrders FOREIGN KEY (RoomID) REFERENCES Rooms(RoomID),
	CONSTRAINT fk_BookingID_CheckInOrders FOREIGN KEY (BookingID) REFERENCES BookingInfo(BookingID),
	CONSTRAINT fk_UserName_CheckInOrders FOREIGN KEY (UserName) REFERENCES Users(UserName);

ALTER TABLE CheckOutOrders
ADD CONSTRAINT fk_CustomerID_CheckOutOrders FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID),
	CONSTRAINT fk_CheckInID_CheckOutOrders FOREIGN KEY (CheckInID) REFERENCES CheckInOrders(CheckInID),
	CONSTRAINT fk_RoomID_CheckOutOrders FOREIGN KEY (RoomID) REFERENCES Rooms(RoomID),
	CONSTRAINT fk_UserName_CheckOutOrders FOREIGN KEY (UserName) REFERENCES Users(UserName);

ALTER TABLE ServicesOrders
ADD CONSTRAINT fk_CustomerID_ServiceOrders FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID),
	CONSTRAINT fk_UserName_ServicesOrders FOREIGN KEY (UserName) REFERENCES Users(UserName);

ALTER TABLE ServicesOrderDetails
ADD CONSTRAINT fk_OrderID_ServicesOrderDetails FOREIGN KEY (OrderID) REFERENCES ServicesOrders(OrderID),
	CONSTRAINT fk_ServiceID_ServicesOrderDetails FOREIGN KEY (ServiceID) REFERENCES ServiceType(ServiceID),
	CONSTRAINT fk_UserName_ServicesOrderDetails FOREIGN KEY (UserName) REFERENCES Users(UserName);

ALTER TABLE Bill
ADD CONSTRAINT fk_CustomerID_Bill FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID),
	CONSTRAINT fk_CheckInID_Bill FOREIGN KEY (CheckInID) REFERENCES CheckInOrders(CheckInID),
	CONSTRAINT fk_CheckOutID_Bill FOREIGN KEY (CheckOutID) REFERENCES CheckOutOrders(CheckOutID),
	CONSTRAINT fk_RoomID_Bill FOREIGN KEY (RoomID) REFERENCES Rooms(RoomID),
	CONSTRAINT fk_UserName_Bills FOREIGN KEY (UserName) REFERENCES Users(UserName); 

-- CREATE VIEW --
DROP VIEW view_UserRole
CREATE VIEW view_UserRole AS
SELECT R.*, E.EmployeeFirstName, E.EmployeeMidName, E.EmployeeLastName FROM [Role] R, Employees E WHERE R.EmployeeID=E.EmployeeID
SELECT * FROM view_UserRole

CREATE VIEW view_RoomProperty AS
SELECT RoomType AS 'PropertyName', COUNT(RoomType) AS 'Total' FROM Rooms GROUP BY RoomType
UNION
SELECT RoomStatus, COUNT(RoomStatus) AS 'Total' FROM Rooms GROUP BY RoomStatus
UNION
SELECT CASE WHEN Clean = 1 THEN 'Clean' ELSE 'Not Clean' END AS 'RoomStatus', COUNT(Clean) AS 'Total'
FROM Rooms
GROUP BY Clean
UNION
SELECT CASE WHEN Repaired = 1 THEN 'Repaired' ELSE 'Not Repaired' END AS 'RoomStatus', COUNT(Repaired) AS 'Total'
FROM Rooms
GROUP BY Repaired
UNION
SELECT CASE WHEN InProgress = 1 THEN 'Checking' ELSE 'Not Checking' END AS 'RoomStatus', COUNT(InProgress) AS 'Total'
FROM Rooms
GROUP BY InProgress
SELECT * FROM view_RoomProperty

-- CREATE STORE PROCEDURE --
CREATE PROC sp_Rooms_With_Status
AS
BEGIN
	SELECT R.*, DATEDIFF(HOUR,CI.CheckInDate,GETDATE()) AS 'Days remaining' FROM Rooms R
	FULL JOIN CheckInOrders CI	ON R.RoomID = CI.RoomID
	ORDER BY R.RoomID
	WHERE R.RoomID = CI.RoomID
	ORDER BY RoomID	
END
EXEC sp_Rooms_With_Status

-- CREATE DATA FOR TESTING --
INSERT INTO Employees(EmployeeID, EmployeeFirstName, EmployeeMidName, EmployeeLastName, Sex) VALUES
('KANEMP001', N'Nhân', N'Thanh', N'Đoàn', 1),
('KANEMP002', N'Kiên', N'Thành', N'Nguyễn', 1),
('KANEMP003', N'An', N'Thiên', N'Ngô', 1),
('KANEMP004', N'Đăng', N'Duy', N'Phan', 1),
('KANEMP005', N'Đức', N'Văn', N'Võ', 1),
('KANEMP006', N'Đức', N'Đình Minh', N'Nguyễn', 1)

DELETE FROM Customers
SELECT * FROM Customers
INSERT INTO Customers(CustomerID, UserName, CustomerFirstName, CustomerMidName, CustomerLastName, CustomerBirthday, CustomerPhoneNumber, Sex) VALUES
('CTM-000000000','admin',N'Free',N'',N'Room','1980-01-01','9999999999',1),
('CTM-000000001','admin',N'David',N'',N'Smith','1980-01-01','0909000001',1),
('CTM-000000002','admin',N'Oliver',N'',N'John','1981-01-01','0909000002',1),
('CTM-000000003','admin',N'Jack',N'',N'Williams','1982-01-01','0909000003',1),
('CTM-000000004','admin',N'Harry',N'',N'Brown','1983-01-01','0909000004',1),
('CTM-000000005','admin',N'Charlie',N'',N'Taylor','1984-01-01','0909000005',1),
('CTM-000000006','admin',N'Thomas',N'',N'Davies','1984-01-01','0909000006',1),
('CTM-000000007','admin',N'Amelia',N'',N'Wilson','1984-01-01','0909000007',2),
('CTM-000000008','admin',N'Olivia',N'',N'Evans','1984-01-01','0909000008',2),
('CTM-000000009','admin',N'Isla',N'',N'Tremblay','1984-01-01','0909000009',2),
('CTM-000000010','admin',N'Emily',N'',N'White','1984-01-01','09090000010',2),
('CTM-000000011','admin',N'Noah',N'',N'Smith','1980-01-01','0909000011',1),
('CTM-000000012','admin',N'Liam',N'',N'John','1981-01-01','0909000012',1),
('CTM-000000013','admin',N'Mason',N'',N'Williams','1982-01-01','0909000013',1),
('CTM-000000014','admin',N'Robert',N'',N'Brown','1983-01-01','0909000014',1),
('CTM-000000015','admin',N'Michael',N'',N'Taylor','1984-01-01','0909000015',1),
('CTM-000000016','admin',N'Ethan',N'',N'Jones','1985-01-01','0909000016',1),
('CTM-000000017','admin',N'Mary',N'',N'Miller','1986-01-01','0909000017',2),
('CTM-000000018','admin',N'Patricia',N'',N'Davis','1987-01-01','0909000018',2),
('CTM-000000019','admin',N'Jennifer',N'',N'Garcia','1988-01-01','0909000019',2),
('CTM-000000020','admin',N'Elizabeth',N'',N'Rodriguez','1989-01-01','09090000020',2),
('CTM-000000021','admin',N'David',N'',N'Murphy','1970-01-01','0909000021',1),
('CTM-000000022','admin',N'Oliver',N'',N'O"Kelly','1971-01-01','0909000022',1),
('CTM-000000023','admin',N'Jack',N'',N'O"Sullivan','1972-01-01','0909000023',1),
('CTM-000000024','admin',N'Harry',N'',N'Walsh','1973-01-01','0909000024',1),
('CTM-000000025','admin',N'Charlie',N'',N'Roy','1974-01-01','0909000025',1),
('CTM-000000026','admin',N'Thomas',N'',N'Gelbero','1954-01-01','0909000026',1),
('CTM-000000027','admin',N'Linda',N'',N'Tremblay','1954-01-01','0909000027',2),
('CTM-000000028','admin',N'Barbara',N'',N'Lee','1954-01-01','0909000028',2),
('CTM-000000029','admin',N'Susan',N'',N'Gagnon','1964-01-01','0909000029',2),
('CTM-000000030','admin',N'Margaret',N'',N'Anderson','1964-01-01','09090000030',2),
INSERT INTO Users(ID_User, EmployeeID, UserName, [PassWord], Active, Serect_Question, Serect_Answer, Check_Login, Check_Time) VALUES
()
SELECT * FROM Users

INSERT INTO RoomType(RoomType, Price, Discount) VALUES 
('Single', 100, 0.12),
('Double', 200, 0.13),
('Triple', 250, 0.14),
('Family', 300, 0.15),
('Deluxe', 1000, 0.16)

SELECT * FROM Rooms
DELETE FROM Rooms
INSERT INTO Rooms (RoomID, RoomType, PhoneNumber, RoomOnFloor, RoomArea, RoomStatus, Clean, 
Repaired, InProgress, CustomerID, UserName, DayRemaining, BookingDate, CheckInDate, LeaveDate) 
VALUES 
('R0101', 'Single', '67890101',1,20,'Available',1,1,0, 'CTM-000000000', 'admin', 0, GETDATE(), GETDATE(), GETDATE()),
('R0102', 'Double', '67890102',1,30,'Available',1,1,0, 'CTM-000000000', 'admin', 0, GETDATE(), GETDATE(), GETDATE()),
('R0103', 'Triple', '67890103',1,40,'Available',1,1,0, 'CTM-000000000', 'admin', 0, GETDATE(), GETDATE(), GETDATE()),
('R0104', 'Family', '67890102',1,50,'Available',1,1,0, 'CTM-000000000', 'admin', 0, GETDATE(), GETDATE(), GETDATE()),
('R0105', 'Deluxe', '67890102',1,60,'Available',1,1,0, 'CTM-000000000', 'admin', 0, GETDATE(), GETDATE(), GETDATE()),
('R0106', 'Single', '67890101',1,20,'Available',1,1,0, 'CTM-000000000', 'admin', 0, GETDATE(), GETDATE(), GETDATE()),
('R0107', 'Double', '67890102',1,30,'Available',1,1,0, 'CTM-000000000', 'admin', 0, GETDATE(), GETDATE(), GETDATE()),
('R0108', 'Triple', '67890103',1,40,'Available',1,1,0, 'CTM-000000000', 'admin', 0, GETDATE(), GETDATE(), GETDATE()),
('R0109', 'Family', '67890102',1,50,'Available',1,1,0, 'CTM-000000000', 'admin', 0, GETDATE(), GETDATE(), GETDATE()),
('R0110', 'Deluxe', '67890102',1,60,'Available',1,1,0, 'CTM-000000000', 'admin', 0, GETDATE(), GETDATE(), GETDATE()),

SELECT R.*, C.CustomerFirstName+' '+C.CustomerMidName+ ' ' +C.CustomerLastName AS 'CustomerFullName',
	DATEDIFF(HOUR,GETDATE(),R.LeaveDate)/24 AS 'Day_Leave',
	DATEDIFF(HOUR,R.BookingDate,GETDATE())/24 AS 'Day_Booking', 
	RT.Price, RT.Discount
	FROM Rooms R, Customers C, RoomType RT
	WHERE R.CustomerID = C.CustomerID AND R.RoomType = RT.RoomType AND R.RoomID='R0101'
SELECT * FROM Rooms
DELETE FROM Rooms
DELETE FROM BookingInfo    

SELECT R.*, RT.Price, RT.Discount, CI.CheckInDate, CI.LeaveDate,
	DATEDIFF(DAY,CI.CheckInDate,CI.LeaveDate) AS 'DayStay', 
	DATEDIFF(DAY,CI.CheckInDate,GETDATE()) AS 'DayUntilNow',
	DATEDIFF(DAY,GETDATE(),CI.LeaveDate) AS 'DayRemaining' 
	FROM Rooms R
	INNER JOIN CheckInOrders CI	ON R.RoomID = CI.RoomID
	INNER JOIN RoomType RT ON R.RoomType = RT.RoomType

SELECT R.*, RT.Price, RT.Discount, CI.CheckInDate, CI.LeaveDate,
	DATEDIFF(DAY,CI.CheckInDate,CI.LeaveDate) AS 'DayStay', 
	DATEDIFF(DAY,CI.CheckInDate,GETDATE()) AS 'DayUntilNow',
	DATEDIFF(DAY,GETDATE(),CI.LeaveDate) AS 'DayRemaining' 
	FROM Rooms R
	INNER JOIN CheckInOrders CI	ON R.RoomID = CI.RoomID
	INNER JOIN RoomType RT ON R.RoomType = RT.RoomType
UNION
SELECT DISTINCT R.*, RT.Price, RT.Discount, CI.CheckInDate, CI.LeaveDate,
	CASE WHEN R.RoomStatus DATEDIFF(DAY,CI.CheckInDate,CI.LeaveDate) AS 'DayStay', 
	DATEDIFF(DAY,CI.CheckInDate,GETDATE()) AS 'DayUntilNow',
	DATEDIFF(DAY,GETDATE(),CI.LeaveDate) AS 'DayRemaining' 
	FROM Rooms R
	LEFT JOIN CheckInOrders CI	ON R.RoomID = CI.RoomID
	INNER JOIN RoomType RT ON R.RoomType = RT.RoomType
	WHERE CI.LeaveDate >= GETDATE() OR CI.LeaveDate IS NULL OR R.RoomStatus = ANY (SELECT RoomStatus FROM Rooms)

SELECT DISTINCT * FROM CheckInOrders CI
WHERE CI.LeaveDate >= GETDATE() OR CI.RoomID = ANY

SELECT CustomerID, COUNT(CustomerID) FROM Rooms WHERE RoomStatus='Occupied'
GROUP BY CustomerID

SELECT * FROM ServiceType
DELETE FROM ServiceType
INSERT INTO ServiceType(ServiceID, UserName, ServiceName, ServiceUnit, ServicePrice, ServiceInventory,ImportQuantity, ImPortDate) VALUES
('KANService001', 'admin', N'Laundry', N'time', 0, 99999, 99999,'2019-03-01'),
('KANService002', 'admin', N'Fruit basket', N'set', 10, 99999, 99999,'2019-03-01'),
('KANService003', 'admin', N'Heineiken 330ml', N'can', 5, 99999, 99999,'2019-03-01'),
('KANService004', 'admin', N'Cocacola 330ml', N'can', 2, 99999, 99999,'2019-03-01'),
('KANService005', 'admin', N'Seven UP', N'can', 2, 99999, 99999,'2019-03-01'),
('KANService006', 'admin', N'Aquafina 500ml', N'bottle', 1.5, 99999, 99999,'2019-03-01')

SELECT * FROM ServicesOrders
DELETE FROM ServicesOrders
INSERT INTO ServicesOrders(OrderID, CustomerID, RoomID, UserName, ServiceOrderDate) VALUES
('Order001','CTM-000000001', 'R0201', 'admin', '2019-02-20'),
('Order002','CTM-000000002', 'R0202', 'admin', '2019-02-20')

SELECT * FROM ServicesOrderDetails
DELETE FROM ServicesOrderDetails
INSERT INTO ServicesOrderDetails(OrderID, ServiceID, UserName, ServiceQuantity, Price, Discount) VALUES
('Order001','KANService002', 'admin', 5, 50, 0.1),
('Order001','KANService003', 'admin', 10, 50, 0.2),
('Order001','KANService004', 'admin', 5, 10, 0.3),
('Order002','KANService002', 'admin', 7, 70, 0.1),
('Order002','KANService004', 'admin', 15, 30, 0.3),
('Order002','KANService006', 'admin', 10, 15, 0.05),

SELECT SOD.OrderID, SOD.ServiceQuantity, SOD.Price, SOD.Discount, ST.*, SO.ServiceOrderDate, SO.CustomerID, SO.RoomID, CIO.CheckInDate, CIO.CheckInID
FROM ServicesOrderDetails SOD 
INNER JOIN ServiceType ST ON SOD.ServiceID = ST.ServiceID
INNER JOIN ServicesOrders SO ON SOD.OrderID = SO.OrderID 
INNER JOIN CheckInOrders CIO ON SO.RoomID = CIO.RoomID
WHERE CIO.CheckInID='CI-BK-2019-03-28-00-17-05-57000' AND CIO.RoomID='R0101' AND SOD.Active=1
WHERE SOD.OrderID='Order001' 

INSERT INTO BookingInfo(BookingID, CustomerID, RoomID, UserName, NumberGuest, DateBook) VALUES
('1902200001', 'CTM-000000001', 'R0201', 'admin', 2, '2019-02-20'),
('1902200002', 'CTM-000000002', 'R0202', 'admin', 1, '2019-02-20'),
('1902200003', 'CTM-000000003', 'R0203', 'admin', 3, '2019-02-20'),
('1903090006', 'CTM-000000001', 'R0202', 'admin', 1, '2019-03-09'),
('1903090007', 'CTM-000000002', 'R0204', 'admin', 2, '2019-03-09'),
('1903090008', 'CTM-000000003', 'R0104', 'admin', 4, '2019-03-09'),
('1903090009', 'CTM-000000004', 'R0203', 'admin', 4, '2019-03-09'),
('1903090010', 'CTM-000000005', 'R0105', 'admin', 2, '2019-03-09'),
('1903120001', 'CTM-000000001', 'R0101', 'admin', 1, '2019-03-20'),
('1903120002', 'CTM-000000002', 'R0201', 'admin', 2, '2019-03-20'),
('1903120003', 'CTM-000000003', 'R0103', 'admin', 4, '2019-03-20'),
('1903120004', 'CTM-000000004', 'R0205', 'admin', 4, '2019-03-20'),
('1903120005', 'CTM-000000005', 'R0102', 'admin', 2, '2019-03-20')
SELECT * FROM BookingInfo
DELETE FROM BookingInfo WHERE userName LIKE 'user01'

INSERT INTO CheckInOrders(CheckInID, BookingID, CustomerID, RoomID, UserName, CheckInType, NumberOfCustomer, CheckInDate, LeaveDate) VALUES
('CI1902200001', '1902200001', 'CTM-000000001', 'R0201', 'admin', 'Reception', 1, '2019-02-20 08:00:00', '2019-02-23 08:00:00'),
('CI1902200002', '1902200002', 'CTM-000000002', 'R0202', 'admin', 'Reception', 1, '2019-02-20 08:10:00', '2019-02-24 08:00:00'),
('CI1902200003', '1902200003', 'CTM-000000003', 'R0203', 'admin', 'Reception', 1, '2019-02-20 08:20:00', '2019-02-25 08:00:00'),
('CI1903120006', '1903090006', 'CTM-000000005', 'R0204', 'admin', 'Reception', 1, '2019-03-09 08:00:00', '2019-03-26 08:00:00'),
('CI1903120007', '1903090007', 'CTM-000000004', 'R0205', 'admin', 'Reception', 2, '2019-03-09 09:00:00', '2019-03-27 08:00:00'),
('CI1903120008', '1903090008', 'CTM-000000003', 'R0201', 'admin', 'Reception', 4, '2019-03-09 08:30:00', '2019-03-28 08:00:00'),
('CI1903120009', '1903090009', 'CTM-000000002', 'R0206', 'admin', 'Reception', 4, '2019-03-09 07:00:00', '2019-04-01 08:00:00'),
('CI1903120010', '1903090010', 'CTM-000000008', 'R0202', 'admin', 'Reception', 2, '2019-03-09 10:15:00', '2019-04-02 08:00:00')

SELECT * FROM CheckInOrders WHERE RoomID IN (SELECT RoomID FROM Rooms WHERE RoomStatus = 'Occupied') AND RoomID = 'R0801'

SELECT RoomID FROM Rooms WHERE RoomStatus = 'Occupied'
DELETE FROM CheckInOrders

SELECT R.*, U.UserName FROM [Role] R, Users U WHERE R.EmployeeID=U.EmployeeID

SELECT * FROM UserLogs

SELECT * FROM RoomType WHERE RoomType NOT IN ('Deluxe','Double', 'Family', 'Single', 'Triple')

DELETE FROM CheckInOrders
DELETE FROM BookingInfo
DELETE FROM Rooms
ALTER TABLE Rooms
ALTER COLUMN DayRemaining int DEFAULT 0;

SELECT * FROM CheckOutOrders
DELETE FROM CheckOutOrders

SELECT * FROM Bill

SELECT B.*, 
CASE WHEN C.CustomerMidName <> '' THEN C.CustomerFirstName+' '+C.CustomerMidName+ ' ' +C.CustomerLastName
ELSE C.CustomerFirstName+' ' +C.CustomerLastName END AS 'CustomerFullName'
FROM Bill B, Customers C
WHERE B.CustomerID = C.CustomerID
SELECT C.CustomerFirstName+' '+C.CustomerMidName+ ' ' +C.CustomerLastName FROM Customers C