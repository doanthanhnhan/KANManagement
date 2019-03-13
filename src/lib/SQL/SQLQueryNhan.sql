CREATE TABLE Rooms(
	-- Create columns	
	RoomID varchar(10) NOT NULL,
	CustomerID varchar(30) NOT NULL,
	UserName varchar(20) NOT NULL,	
	RoomType varchar(20) NOT NULL,	
	PhoneNumber varchar(20) NOT NULL,
	RoomOnFloor tinyint NOT NULL,
	RoomArea decimal(8,3) NOT NULL,
	RoomStatus varchar(10) NOT NULL,
	DayRemaining tinyint DEFAULT 0 NOT NULL,
	Clean bit DEFAULT 1,
	Repaired bit DEFAULT 1,
	InProgress bit DEFAULT 0,
	Active bit DEFAULT 1,
	-- Create constraint
	CONSTRAINT pk_RoomID PRIMARY KEY (RoomID),
	CONSTRAINT uc_RoomID UNIQUE (RoomID)	
)
DROP TABLE Rooms

CREATE TABLE Customers(
	-- Create columns	
	CustomerID varchar(30) NOT NULL,
	UserName varchar(20) NOT NULL,	
	CustomerFirstName varchar(20) NOT NULL,
	CustomerMidName varchar(20) ,
	CustomerLastName varchar(20) NOT NULL,
	CustomerBirthday date NOT NULL,	
	CustomerPhoneNumber varchar(20) NOT NULL,
	CustomerPassport varchar(30),
	CustomerEmail varchar(100),
	Company nvarchar(100),	
	Active bit DEFAULT 1,
	Sex bit,
	-- Create constraint
	CONSTRAINT pk_CustomerID PRIMARY KEY (CustomerID),
	CONSTRAINT uc_CustomerID UNIQUE (CustomerID)
)
DROP TABLE Customers

CREATE TABLE BookingInfo (
	-- Create columns
	BookingID varchar(30) NOT NULL,
	CustomerID varchar(30) NOT NULL,	
	RoomID varchar(10) NOT NULL,
	UserName varchar(20) NOT NULL,
	Note nvarchar(1000),	
	NumberGuest tinyint not null,
	DateBook date not null,
	BookDrive bit,
	Flight varchar(50)
	-- Create constraint
	CONSTRAINT pk_BookingID PRIMARY KEY (BookingID),
	CONSTRAINT uc_BookingID UNIQUE (BookingID)
)
DROP TABLE BookingInfo

CREATE TABLE CheckInOrders(
	-- Create columns
	ID int IDENTITY(1,1),	
	CheckInID varchar(50) NOT NULL,
	BookingID varchar(30) NOT NULL,
	CustomerID varchar(30) NOT NULL,
	RoomID varchar(10) NOT NULL,
	UserName varchar(20) NOT NULL,
	CheckInType varchar(20) NOT NULL,		
	NumberOfCustomer tinyint NOT NULL,
	CheckInDate datetime NOT NULL,
	LeaveDate datetime NOT NULL,		
	CustomerPackage varchar(200),	
	Active bit DEFAULT 1,
	-- Create constraint
	CONSTRAINT pk_CheckInOrdersID PRIMARY KEY (ID),
	CONSTRAINT uc_CheckInID UNIQUE (CheckInID)
)
DROP TABLE CheckInOrders

CREATE TABLE CheckOutOrders(
	-- Create columns
	ID int IDENTITY(1,1),	
	CheckOutID varchar(50) NOT NULL,
	CheckInID varchar(50) NOT NULL,
	CustomerID varchar(30) NOT NULL,		
	RoomID varchar(10) NOT NULL,
	UserName varchar(20) NOT NULL,
	CheckInDate date NOT NULL,
	CheckOutDate date NOT NULL,		
	CustomerPayment varchar(50) NOT NULL,
	CustomerBill decimal(18,3) NOT NULL,	
	Active bit DEFAULT 1 NOT NULL,
	-- Create constraint
	CONSTRAINT pk_CheckOutOrdersID PRIMARY KEY (ID),
	CONSTRAINT uc_CheckOutID_CheckOutOrders UNIQUE (CheckOutID),
	CONSTRAINT uc_CheckInID_CheckOutOrders UNIQUE (CheckInID)
)
DROP TABLE CheckOutOrders

CREATE TABLE ServicesOrders(
	-- Create columns
	ID int IDENTITY(1,1),	
	OrderID varchar(50) NOT NULL,
	CustomerID varchar(30) NOT NULL,
	UserName varchar(20) NOT NULL,	
	ServiceOrderDate datetime NOT NULL,
	ServiceNote nvarchar(200),	
	Active bit DEFAULT 1 NOT NULL,
	-- Create constraint
	CONSTRAINT pk_ServicesOrdersRoomID PRIMARY KEY (ID),
	CONSTRAINT uc_OrderID UNIQUE (OrderID)	
)
DROP TABLE ServicesOrders

CREATE TABLE ServicesOrderDetails(
	-- Create columns
	ID int IDENTITY(1,1),	
	OrderDetailID varchar(50) NOT NULL,
	OrderID varchar(50) NOT NULL,
	ServiceID varchar(50) NOT NULL,
	UserName varchar(20) NOT NULL,	
	ServiceQuantity int NOT NULL,
	Price decimal(18,3) NOT NULL,	
	Discount float,
	Active bit DEFAULT 1 NOT NULL,
	-- Create constraint
	CONSTRAINT pk_ServicesOrderDetails PRIMARY KEY (ID),
	CONSTRAINT uc_OrderDetailID UNIQUE (OrderDetailID)		
)
DROP TABLE ServicesOrderDetails

CREATE TABLE ServiceType(
	-- Create columns
	ID int IDENTITY(1,1),	
	ServiceID varchar(50) NOT NULL,
	UserName varchar(20) NOT NULL,
	ServiceName nvarchar(100) NOT NULL,	
	ServiceUnit nvarchar(20) NOT NULL,
	ServicePrice decimal(18,3) NOT NULL,
	ServiceInventory float NOT NULL,
	InputDate datetime NOT NULL,
	ServiceDescription nvarchar(500),
	[Image] varbinary(MAX),	
	Active bit DEFAULT 1 NOT NULL,
	-- Create constraint
	CONSTRAINT pk_ServiceType PRIMARY KEY (ID),
	CONSTRAINT uc_ServiceID UNIQUE (ServiceID),
	CONSTRAINT uc_ServiceName UNIQUE (ServiceName)
)
DROP TABLE ServiceType

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
	DepartmentID varchar(10) NOT NULL,		
	DepartmentName nvarchar(50) NOT NULL,
	Active bit DEFAULT 1 NOT NULL,
	-- Create constraint
	CONSTRAINT pk_DepartmentID PRIMARY KEY (DepartmentID),
	CONSTRAINT uc_DepartmentID UNIQUE (DepartmentID)
)

CREATE TABLE Employees(
-- Create columns
	EmployeeID varchar(20) NOT NULL,	
	EmployeeFirstName nvarchar(15) NOT NULL,
	EmployeeMidName nvarchar(25),
	EmployeeLastName nvarchar(15) NOT NULL,
	DepartmentID varchar(20),
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
	RoleID varchar(20),
	[Image] varbinary(MAX),
	-- Create constraint
	CONSTRAINT pk_EmployeeID PRIMARY KEY (EmployeeID),
	CONSTRAINT uc_EmployeeID_Employees UNIQUE (EmployeeID)
)

CREATE TABLE Users(
	-- Create columns
	ID_User int NOT NULL,
	EmployeeID varchar(20) NOT NULL,
	UserName varchar(20),
	[PassWord] varchar(255),
	Active bit NOT NULL,
	Secret_Question nvarchar(100),
	Secret_Answer nvarchar(100),
	Check_Login int,
	Check_Time date,
	-- Create constraint
	CONSTRAINT pk_ID_User PRIMARY KEY (ID_User),
	CONSTRAINT uc_EmployeeID_Users UNIQUE (EmployeeID),
	CONSTRAINT uc_UserName_Users UNIQUE (UserName)
)

CREATE TABLE [Role](
	-- Create columns
	RoleID varchar(20) NOT NULL,	
	EmployeeID varchar(20) NOT NULL,
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
	Departmentg_Edit bit DEFAULT 0,
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
	UserLog_Delete bit DEFAULT 0
	-- Create constraint
	CONSTRAINT pk_RoleID PRIMARY KEY (RoleID),
	CONSTRAINT uc_RoleID_Role UNIQUE (RoleID),
	CONSTRAINT uc_EmployeeID_Role UNIQUE (EmployeeID)
)

-- CREATE CONSTRAINT FOR TABLES --
ALTER TABLE Users
ADD CONSTRAINT fk_EmployeeID_Users FOREIGN KEY (EmployeeID) REFERENCES Employees(EmployeeID);

ALTER TABLE [Role]
ADD CONSTRAINT fk_EmployeeID_Role FOREIGN KEY (EmployeeID) REFERENCES Employees(EmployeeID);

ALTER TABLE Employees
ADD CONSTRAINT fk_RoleID_Employees FOREIGN KEY (RoleID) REFERENCES [Role](RoleID);

ALTER TABLE Rooms
ADD CONSTRAINT fk_CustomerID_Rooms FOREIGN KEY (CustomerID) REFERENCES Customers(CustomerID),
	CONSTRAINT fk_UserName_Rooms FOREIGN KEY (UserName) REFERENCES Users(UserName);

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

-- CREATE VIEW --

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
-- CREATE DATA FOR TESTING --
INSERT INTO Employees(EmployeeID, EmployeeFirstName, EmployeeMidName, EmployeeLastName, Sex) VALUES
('KANEMP001', N'Nhân', N'Thanh', N'Đoàn', 1),
('KANEMP002', N'Kiên', N'Thành', N'Nguyễn', 1),
('KANEMP003', N'An', N'Thiên', N'Ngô', 1),
('KANEMP004', N'Đăng', N'Duy', N'Phan', 1),
('KANEMP005', N'Đức', N'Văn', N'Võ', 1),
('KANEMP006', N'Đức', N'Đình Minh', N'Nguyễn', 1)

INSERT INTO Customers(CustomerID, UserName, CustomerFirstName, CustomerMidName, CustomerLastName, CustomerBirthday, CustomerPhoneNumber) VALUES
('KANCUS001','admin',N'David',N'',N'Smith','1980-01-01','0909000001'),
('KANCUS002','admin',N'Oliver',N'',N'John','1981-01-01','0909000002'),
('KANCUS003','admin',N'Jack',N'',N'Williams','1982-01-01','0909000003'),
('KANCUS004','admin',N'Harry',N'',N'Brown','1983-01-01','0909000004'),
('KANCUS005','admin',N'Jacob',N'',N'Taylor','1984-01-01','0909000005')
INSERT INTO Users(ID_User, EmployeeID, UserName, [PassWord], Active, Serect_Question, Serect_Answer, Check_Login, Check_Time) VALUES
()

INSERT INTO Rooms (RoomID, RoomType, PhoneNumber, RoomOnFloor, RoomArea, RoomStatus, Clean, Repaired, InProgress, CustomerID, UserName, DayRemaining) VALUES 
('R0101', 'Single', '67890101',1,20,'Available',1,1,1, 'KANCUS001', 'admin', 0),
('R0102', 'Double', '67890102',1,30,'Reserved',0,1,1, 'KANCUS002', 'admin', 0),
('R0103', 'Triple', '67890103',1,40,'Occupied',1,0,0, 'KANCUS003', 'admin', 3),
('R0104', 'Family', '67890104',1,50,'Out',1,1,0, 'KANCUS004', 'admin', 0),
('R0105', 'Deluxe', '67890105',1,60,'Available',0,0,1, 'KANCUS005', 'admin', 0),
('R0201', 'Deluxe', '67890201',2,60,'Occupied',1,1,0, 'KANCUS005', 'admin',5),
('R0202', 'Single', '67890202',2,20,'Out',0,1,0, 'KANCUS003', 'admin',0),
('R0203', 'Triple', '67890203',2,40,'Reserved',1,1,0, 'KANCUS001', 'admin',0),
('R0204', 'Double', '67890204',2,30,'Available',1,0,0, 'KANCUS004', 'admin',0),
('R0205', 'Family', '67890205',2,50,'Available',0,1,1, 'KANCUS002', 'admin',0)

SELECT R.*, C.CustomerFirstName+' '+C.CustomerMidName+ ' ' +C.CustomerLastName AS 'CustomerFullName' 
FROM Rooms R, Customers C
WHERE R.CustomerID = C.CustomerID
SELECT * FROM Rooms
DELETE FROM Rooms

SELECT * FROM ServiceType
INSERT INTO ServiceType(ServiceID, UserName, ServiceName, ServiceUnit, ServicePrice, ServiceInventory, InputDate) VALUES
('KANService001', 'admin', N'Laundry', N'time', 0, 99999, '2019-03-01'),
('KANService002', 'admin', N'Fruit basket', N'set', 10, 99999, '2019-03-01'),
('KANService003', 'admin', N'Heineiken 330ml', N'can', 5, 99999, '2019-03-01'),
('KANService004', 'admin', N'Cocacola 330ml', N'can', 2, 99999, '2019-03-01'),
('KANService005', 'admin', N'Seven UP', N'can', 2, 99999, '2019-03-01'),
('KANService006', 'admin', N'Aquafina 500ml', N'bottle', 1.5, 99999, '2019-03-01')

INSERT INTO BookingInfo(BookingID, CustomerID, RoomID, UserName, NumberGuest, DateBook) VALUES
('1902200001', 'KANCUS001', 'R0201', 'user01', 2, '2019-02-20'),
('1902200002', 'KANCUS002', 'R0202', 'user01', 1, '2019-02-20'),
('1902200003', 'KANCUS003', 'R0203', 'user01', 3, '2019-02-20'),
('1903090006', 'KANCUS001', 'R0202', 'user01', 1, '2019-03-09'),
('1903090007', 'KANCUS002', 'R0204', 'user01', 2, '2019-03-09'),
('1903090008', 'KANCUS003', 'R0104', 'user01', 4, '2019-03-09'),
('1903090009', 'KANCUS004', 'R0203', 'user01', 4, '2019-03-09'),
('1903090010', 'KANCUS005', 'R0105', 'user01', 2, '2019-03-09'),
('1903120001', 'KANCUS001', 'R0101', 'admin', 1, '2019-03-20'),
('1903120002', 'KANCUS002', 'R0201', 'admin', 2, '2019-03-20'),
('1903120003', 'KANCUS003', 'R0103', 'admin', 4, '2019-03-20'),
('1903120004', 'KANCUS004', 'R0205', 'admin', 4, '2019-03-20'),
('1903120005', 'KANCUS005', 'R0102', 'admin', 2, '2019-03-20')
SELECT * FROM BookingInfo
DELETE FROM BookingInfo WHERE userName LIKE 'user01'

INSERT INTO CheckInOrders(CheckInID, BookingID, CustomerID, RoomID, UserName, CheckInType, NumberOfCustomer, CheckInDate, DaysStay) VALUES
('CI1902200001', '1902200001', 'KANCUS001', 'R0201', 'user01', 'Reception', 1, '2019-02-20 08:00:00', 3),
('CI1902200002', '1902200002', 'KANCUS002', 'R0202', 'user01', 'Reception', 1, '2019-02-20 08:10:00', 6),
('CI1902200003', '1902200003', 'KANCUS003', 'R0203', 'user01', 'Reception', 1, '2019-02-20 08:20:00', 9),
('CI1903120006', '1903090006', 'KANCUS005', 'R0202', 'user01', 'Reception', 1, '2019-03-09 08:00:00', 3),
('CI1903120007', '1903090007', 'KANCUS004', 'R0204', 'user01', 'Reception', 2, '2019-03-09 09:00:00', 4),
('CI1903120008', '1903090008', 'KANCUS003', 'R0104', 'user01', 'Reception', 4, '2019-03-09 08:30:00', 5),
('CI1903120009', '1903090009', 'KANCUS002', 'R0203', 'user01', 'Reception', 4, '2019-03-09 07:00:00', 6),
('CI1903120010', '1903090010', 'KANCUS001', 'R0105', 'user01', 'Reception', 2, '2019-03-09 10:15:00', 7),
('CI1903120001', '1903120001', 'KANCUS001', 'R0101', 'admin', 'Booking', 1, '2019-03-20', 3),
('CI1903120002', '1903120002', 'KANCUS002', 'R0201', 'admin', 'Booking', 2, '2019-03-20', 4),
('CI1903120003', '1903120003', 'KANCUS003', 'R0103', 'admin', 'Booking', 4, '2019-03-20', 5),
('CI1903120004', '1903120004', 'KANCUS004', 'R0205', 'admin', 'Booking', 4, '2019-03-20', 6),
('CI1903120005', '1903120005', 'KANCUS005', 'R0102', 'admin', 'Booking', 2, '2019-03-20', 7)

SELECT * FROM CheckInOrders
DELETE FROM CheckInOrders

