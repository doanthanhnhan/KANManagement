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
	CONSTRAINT pk_RoomID_Rooms PRIMARY KEY (RoomID),
	CONSTRAINT uc_RoomID_Rooms UNIQUE (RoomID)	
)
DROP TABLE Rooms
CREATE TABLE RoomType(
	-- Create columns		
	RoomType varchar(20) NOT NULL,	
	Price decimal(18,3) NOT NULL,
	Discount decimal(3,2) DEFAULT 0,
	Active bit DEFAULT 1,
	-- Create constraint
	CONSTRAINT pk_RoomType_RoomType PRIMARY KEY (RoomType),
	CONSTRAINT uc_RoomType_RoomType UNIQUE (RoomType)
)
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
	CONSTRAINT pk_BookingID_BookingInfo PRIMARY KEY (BookingID),
	CONSTRAINT uc_BookingID_BookingInfo UNIQUE (BookingID)
)
DROP TABLE BookingInfo

CREATE TABLE CheckInOrders(
	-- Create columns	
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
	CONSTRAINT pk_CheckInID_CheckInOrders PRIMARY KEY (CheckInID),
	CONSTRAINT uc_CheckInID_CheckInOrders UNIQUE (CheckInID)
)
DROP TABLE CheckInOrders

CREATE TABLE CheckOutOrders(
	-- Create columns		
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
	CONSTRAINT pk_CheckOutID_CheckOutOrders PRIMARY KEY (CheckOutID),
	CONSTRAINT uc_CheckOutID_CheckOutOrders UNIQUE (CheckOutID),
	CONSTRAINT uc_CheckInID_CheckOutOrders UNIQUE (CheckInID)
)
DROP TABLE CheckOutOrders

CREATE TABLE ServicesOrders(
	-- Create columns		
	OrderID varchar(50) NOT NULL,
	CustomerID varchar(30) NOT NULL,
	RoomID varchar(10) NOT NULL,
	UserName varchar(20) NOT NULL,	
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
	OrderID varchar(50) NOT NULL,
	ServiceID varchar(50) NOT NULL,
	UserName varchar(20) NOT NULL,	
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
	ServiceID varchar(50) NOT NULL,
	UserName varchar(20) NOT NULL,
	ServiceName nvarchar(100) NOT NULL,	
	ServiceUnit nvarchar(20) NOT NULL,
	ServicePrice decimal(18,3) NOT NULL,
	ServiceInventory int NOT NULL,
	InputDate datetime NOT NULL,
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
	UserName varchar(20) NOT NULL,	
	LogContent nvarchar(200) NOT NULL,
	LogTime datetime NOT NULL,
	Active bit DEFAULT 1 NOT NULL,
	-- Create constraint
	CONSTRAINT pk_ID_UserLogs PRIMARY KEY (ID)
)

DROP TABLE Departments
CREATE TABLE Departments(
	-- Create columns
	DepartmentID varchar(20) NOT NULL,		
	DepartmentName nvarchar(50) NOT NULL,
	Active bit DEFAULT 1 NOT NULL,
	-- Create constraint
	CONSTRAINT pk_DepartmentID_Departments PRIMARY KEY (DepartmentID),
	CONSTRAINT uc_DepartmentID_Departments UNIQUE (DepartmentID)
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
	[Image] varbinary(MAX),
	-- Create constraint
	CONSTRAINT pk_EmployeeID_Employees PRIMARY KEY (EmployeeID),
	CONSTRAINT uc_EmployeeID_Employees UNIQUE (EmployeeID),
	CONSTRAINT df_DepartmentID_Employees DEFAULT 'Free' FOR DepartmentID
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
	CONSTRAINT pk_ID_User_Users PRIMARY KEY (ID_User),
	CONSTRAINT uc_EmployeeID_Users UNIQUE (EmployeeID),
	CONSTRAINT uc_UserName_Users UNIQUE (UserName)
)

CREATE TABLE [Role](
	-- Create columns
	RoleID int IDENTITY(1,1),	
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
	UserLog_Delete bit DEFAULT 0
	-- Create constraint
	CONSTRAINT pk_RoleID_Role PRIMARY KEY (RoleID),
	CONSTRAINT uc_RoleID_Role UNIQUE (RoleID),
	CONSTRAINT uc_EmployeeID_Role UNIQUE (EmployeeID)
)

-- CREATE CONSTRAINT FOR TABLES --
ALTER TABLE Users
ADD CONSTRAINT fk_EmployeeID_Users FOREIGN KEY (EmployeeID) REFERENCES Employees(EmployeeID);

ALTER TABLE [Role]
ADD CONSTRAINT fk_EmployeeID_Role FOREIGN KEY (EmployeeID) REFERENCES Employees(EmployeeID);


ALTER TABLE Employees
ADD CONSTRAINT fk_RoleID_Employees FOREIGN KEY (RoleID) REFERENCES [Role](RoleID),	
	CONSTRAINT fk_DepartmentID_Employees FOREIGN KEY (DepartmentID) REFERENCES Departments(DepartmentID);


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

-- CREATE VIEW --
DROP VIEW view_UserRole
CREATE VIEW view_UserRole AS
SELECT R.*, E.EmployeeFirstName, E.EmployeeMidName, E.EmployeeLastName FROM [Role] R, Employees E WHERE R.EmployeeID=E.EmployeeID
SELECT * FROM view_UserRole

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

DELETE FROM Customers
SELECT * FROM Customers
INSERT INTO Customers(CustomerID, UserName, CustomerFirstName, CustomerMidName, CustomerLastName, CustomerBirthday, CustomerPhoneNumber, Sex) VALUES
('KANCUS001','admin',N'David',N'',N'Smith','1980-01-01','0909000001',1),
('KANCUS002','admin',N'Oliver',N'',N'John','1981-01-01','0909000002',1),
('KANCUS003','admin',N'Jack',N'',N'Williams','1982-01-01','0909000003',1),
('KANCUS004','admin',N'Harry',N'',N'Brown','1983-01-01','0909000004',1),
('KANCUS005','admin',N'Charlie',N'',N'Taylor','1984-01-01','0909000005',1),
('KANCUS006','admin',N'Thomas',N'',N'Davies','1984-01-01','0909000006',1),
('KANCUS007','admin',N'Amelia',N'',N'Wilson','1984-01-01','0909000007',2),
('KANCUS008','admin',N'Olivia',N'',N'Evans','1984-01-01','0909000008',2),
('KANCUS009','admin',N'Isla',N'',N'Tremblay','1984-01-01','0909000009',2),
('KANCUS010','admin',N'Emily',N'',N'White','1984-01-01','09090000010',2),
('KANCUS011','admin',N'Noah',N'',N'Smith','1980-01-01','0909000011',1),
('KANCUS012','admin',N'Liam',N'',N'John','1981-01-01','0909000012',1),
('KANCUS013','admin',N'Mason',N'',N'Williams','1982-01-01','0909000013',1),
('KANCUS014','admin',N'Robert',N'',N'Brown','1983-01-01','0909000014',1),
('KANCUS015','admin',N'Michael',N'',N'Taylor','1984-01-01','0909000015',1),
('KANCUS016','admin',N'Ethan',N'',N'Jones','1985-01-01','0909000016',1),
('KANCUS017','admin',N'Mary',N'',N'Miller','1986-01-01','0909000017',2),
('KANCUS018','admin',N'Patricia',N'',N'Davis','1987-01-01','0909000018',2),
('KANCUS019','admin',N'Jennifer',N'',N'Garcia','1988-01-01','0909000019',2),
('KANCUS020','admin',N'Elizabeth',N'',N'Rodriguez','1989-01-01','09090000020',2),
('KANCUS021','admin',N'David',N'',N'Murphy','1970-01-01','0909000021',1),
('KANCUS022','admin',N'Oliver',N'',N'O"Kelly','1971-01-01','0909000022',1),
('KANCUS023','admin',N'Jack',N'',N'O"Sullivan','1972-01-01','0909000023',1),
('KANCUS024','admin',N'Harry',N'',N'Walsh','1973-01-01','0909000024',1),
('KANCUS025','admin',N'Charlie',N'',N'Roy','1974-01-01','0909000025',1),
('KANCUS026','admin',N'Thomas',N'',N'Gelbero','1954-01-01','0909000026',1),
('KANCUS027','admin',N'Linda',N'',N'Tremblay','1954-01-01','0909000027',2),
('KANCUS028','admin',N'Barbara',N'',N'Lee','1954-01-01','0909000028',2),
('KANCUS029','admin',N'Susan',N'',N'Gagnon','1964-01-01','0909000029',2),
('KANCUS030','admin',N'Margaret',N'',N'Anderson','1964-01-01','09090000030',2),
INSERT INTO Users(ID_User, EmployeeID, UserName, [PassWord], Active, Serect_Question, Serect_Answer, Check_Login, Check_Time) VALUES
()
SELECT * FROM Users

INSERT INTO RoomType(RoomType, Price, Discount) VALUES 
('Single', 100, 0.0),
('Double', 200, 0.2),
('Triple', 250, 0.2),
('Family', 300, 0.3),
('Deluxe', 1000, 0.5)

SELECT * FROM Rooms
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
('R0205', 'Family', '67890205',2,50,'Available',0,1,1, 'KANCUS002', 'admin',0),
('R0106', 'Single', '67890106',1,20,'Available',1,1,1, 'KANCUS006', 'admin', 0),
('R0107', 'Double', '67890107',1,30,'Reserved',0,1,1, 'KANCUS007', 'admin', 0),
('R0108', 'Triple', '67890108',1,40,'Occupied',1,0,0, 'KANCUS008', 'admin', 8),
('R0109', 'Family', '67890109',1,50,'Out',1,1,0, 'KANCUS009', 'admin', 0),
('R0110', 'Deluxe', '67890110',1,60,'Available',0,0,1, 'KANCUS010', 'admin', 0),
('R0206', 'Deluxe', '67890206',2,60,'Occupied',1,1,0, 'KANCUS010', 'admin',25),
('R0207', 'Single', '67890207',2,20,'Out',0,1,0, 'KANCUS009', 'admin',0),
('R0208', 'Triple', '67890208',2,40,'Reserved',1,1,0, 'KANCUS008', 'admin',0),
('R0209', 'Double', '67890209',2,30,'Available',1,0,0, 'KANCUS007', 'admin',0),
('R0210', 'Family', '678902105',2,50,'Available',0,1,1, 'KANCUS006', 'admin',0),
('R0301', 'Single', '67890301',3,20,'Available',1,1,1, 'KANCUS001', 'admin', 0),
('R0302', 'Double', '67890302',3,30,'Reserved',0,1,1, 'KANCUS002', 'admin', 0),
('R0303', 'Triple', '67890303',3,40,'Occupied',1,0,0, 'KANCUS003', 'admin', 3),
('R0304', 'Family', '67890304',3,50,'Out',1,1,0, 'KANCUS004', 'admin', 0),
('R0305', 'Deluxe', '67890305',3,60,'Occupied',0,0,1, 'KANCUS009', 'admin', 15),
('R0306', 'Deluxe', '67890306',3,60,'Occupied',1,1,0, 'KANCUS005', 'admin',5),
('R0307', 'Single', '67890307',3,20,'Out',0,1,0, 'KANCUS003', 'admin',0),
('R0308', 'Triple', '67890308',3,40,'Reserved',1,1,0, 'KANCUS001', 'admin',0),
('R0309', 'Double', '67890309',3,30,'Available',1,0,0, 'KANCUS004', 'admin',0),
('R0310', 'Family', '67890310',3,50,'Occupied',0,1,1, 'KANCUS002', 'admin',9),
('R0401', 'Single', '67890401',4,20,'Available',1,1,1, 'KANCUS006', 'admin', 0),
('R0402', 'Double', '67890402',4,30,'Reserved',0,1,1, 'KANCUS007', 'admin', 0),
('R0403', 'Triple', '67890403',4,40,'Occupied',1,0,0, 'KANCUS008', 'admin', 8),
('R0404', 'Family', '67890404',4,50,'Out',1,1,0, 'KANCUS009', 'admin', 0),
('R0405', 'Deluxe', '67890405',4,60,'Available',0,0,1, 'KANCUS010', 'admin', 0),
('R0406', 'Deluxe', '67890406',4,60,'Occupied',1,1,0, 'KANCUS010', 'admin',25),
('R0407', 'Single', '67890407',4,20,'Out',0,1,0, 'KANCUS009', 'admin',0),
('R0408', 'Triple', '67890408',4,40,'Reserved',1,1,0, 'KANCUS008', 'admin',0),
('R0409', 'Double', '67890409',4,30,'Available',1,0,0, 'KANCUS007', 'admin',0),
('R0410', 'Family', '67890410',4,50,'Available',0,1,1, 'KANCUS006', 'admin',0),
('R0501', 'Single', '67890501',5,20,'Available',1,1,1, 'KANCUS001', 'admin', 0),
('R0502', 'Double', '67890502',5,30,'Reserved',0,1,1, 'KANCUS002', 'admin', 0),
('R0503', 'Triple', '67890503',5,40,'Occupied',1,0,0, 'KANCUS003', 'admin', 8),
('R0504', 'Family', '67890504',5,50,'Out',1,1,0, 'KANCUS004', 'admin', 0),
('R0505', 'Deluxe', '67890505',5,60,'Occupied',0,0,1, 'KANCUS005', 'admin', 30),
('R0506', 'Deluxe', '67890506',5,60,'Occupied',1,1,0, 'KANCUS006', 'admin',25),
('R0507', 'Single', '67890507',5,20,'Out',0,1,0, 'KANCUS007', 'admin',0),
('R0508', 'Triple', '67890508',5,40,'Reserved',1,1,0, 'KANCUS008', 'admin',0),
('R0509', 'Double', '67890509',5,30,'Occupied',1,0,0, 'KANCUS009', 'admin',8),
('R0510', 'Family', '67890510',5,50,'Available',0,1,1, 'KANCUS010', 'admin',0),
('R0601', 'Single', '67890601',6,20,'Available',1,1,1, 'KANCUS011', 'admin', 0),
('R0602', 'Double', '67890602',6,30,'Reserved',0,1,1, 'KANCUS012', 'admin', 0),
('R0603', 'Triple', '67890603',6,40,'Occupied',1,0,0, 'KANCUS013', 'admin', 3),
('R0604', 'Family', '67890604',6,50,'Out',1,1,0, 'KANCUS014', 'admin', 0),
('R0605', 'Deluxe', '67890605',6,60,'Occupied',0,0,1, 'KANCUS015', 'admin', 15),
('R0606', 'Deluxe', '67890606',6,60,'Occupied',1,1,0, 'KANCUS016', 'admin',5),
('R0607', 'Single', '67890607',6,20,'Out',0,1,0, 'KANCUS017', 'admin',0),
('R0608', 'Triple', '67890608',6,40,'Reserved',1,1,0, 'KANCUS018', 'admin',0),
('R0609', 'Double', '67890609',6,30,'Occupied',1,0,0, 'KANCUS019', 'admin',2),
('R0610', 'Family', '67890610',6,50,'Occupied',0,1,1, 'KANCUS020', 'admin',9),
('R0701', 'Single', '67890701',7,20,'Occupied',1,1,1, 'KANCUS021', 'admin', 4),
('R0702', 'Double', '67890702',7,30,'Reserved',0,1,1, 'KANCUS022', 'admin', 0),
('R0703', 'Triple', '67890703',7,40,'Occupied',1,0,0, 'KANCUS023', 'admin', 8),
('R0704', 'Family', '67890704',7,50,'Out',1,1,0, 'KANCUS024', 'admin', 0),
('R0705', 'Deluxe', '67890705',7,60,'Available',0,0,1, 'KANCUS025', 'admin', 0),
('R0706', 'Deluxe', '67890706',7,60,'Occupied',1,1,0, 'KANCUS026', 'admin',25),
('R0707', 'Single', '67890707',7,20,'Out',0,1,0, 'KANCUS027', 'admin',0),
('R0708', 'Triple', '67890708',7,40,'Reserved',1,1,0, 'KANCUS028', 'admin',0),
('R0709', 'Double', '67890709',7,30,'Available',1,0,0, 'KANCUS029', 'admin',0),
('R0710', 'Family', '67890710',7,50,'Occupied',0,1,1, 'KANCUS030', 'admin',10),
('R0801', 'Single', '67890801',8,20,'Occupied',1,1,1, 'KANCUS016', 'admin', 11),
('R0802', 'Double', '67890802',8,30,'Reserved',0,1,1, 'KANCUS027', 'admin', 0),
('R0803', 'Triple', '67890803',8,40,'Occupied',1,0,0, 'KANCUS008', 'admin', 8),
('R0804', 'Family', '67890804',8,50,'Out',1,1,0, 'KANCUS009', 'admin', 0),
('R0805', 'Deluxe', '67890805',8,60,'Available',0,0,1, 'KANCUS014', 'admin', 0),
('R0806', 'Deluxe', '67890806',8,60,'Occupied',1,1,0, 'KANCUS016', 'admin',25),
('R0807', 'Single', '67890807',8,20,'Out',0,1,0, 'KANCUS019', 'admin',0),
('R0808', 'Triple', '67890808',8,40,'Reserved',1,1,0, 'KANCUS028', 'admin',0),
('R0809', 'Double', '67890809',8,30,'Available',1,0,0, 'KANCUS027', 'admin',0),
('R0810', 'Family', '67890810',8,50,'Available',0,1,1, 'KANCUS026', 'admin',0),

SELECT R.*, C.CustomerFirstName+' '+C.CustomerMidName+ ' ' +C.CustomerLastName AS 'CustomerFullName' 
FROM Rooms R, Customers C
WHERE R.CustomerID = C.CustomerID
SELECT * FROM Rooms
DELETE FROM Rooms

SELECT CustomerID, COUNT(CustomerID) FROM Rooms WHERE RoomStatus='Occupied'
GROUP BY CustomerID

SELECT * FROM ServiceType
DELETE FROM ServiceType
INSERT INTO ServiceType(ServiceID, UserName, ServiceName, ServiceUnit, ServicePrice, ServiceInventory, InputDate) VALUES
('KANService001', 'admin', N'Laundry', N'time', 0, 99999, '2019-03-01'),
('KANService002', 'admin', N'Fruit basket', N'set', 10, 99999, '2019-03-01'),
('KANService003', 'admin', N'Heineiken 330ml', N'can', 5, 99999, '2019-03-01'),
('KANService004', 'admin', N'Cocacola 330ml', N'can', 2, 99999, '2019-03-01'),
('KANService005', 'admin', N'Seven UP', N'can', 2, 99999, '2019-03-01'),
('KANService006', 'admin', N'Aquafina 500ml', N'bottle', 1.5, 99999, '2019-03-01')

SELECT * FROM ServicesOrders
DELETE FROM ServicesOrders
INSERT INTO ServicesOrders(OrderID, CustomerID, RoomID, UserName, ServiceOrderDate) VALUES
('Order001','KANCUS001', 'R0201', 'admin', '2019-02-20'),
('Order002','KANCUS002', 'R0202', 'admin', '2019-02-20')

SELECT * FROM ServicesOrderDetails
DELETE FROM ServicesOrderDetails
INSERT INTO ServicesOrderDetails(OrderID, ServiceID, UserName, ServiceQuantity, Price, Discount) VALUES
('Order001','KANService002', 'admin', 5, 50, 0.1),
('Order001','KANService003', 'admin', 10, 50, 0.2),
('Order001','KANService004', 'admin', 5, 10, 0.3),
('Order002','KANService002', 'admin', 7, 70, 0.1),
('Order002','KANService004', 'admin', 15, 30, 0.3),
('Order002','KANService006', 'admin', 10, 15, 0.05),

SELECT SOD.OrderID, SOD.ServiceQuantity, SOD.Price, SOD.Discount, ST.* FROM ServicesOrderDetails SOD 
INNER JOIN ServiceType ST
ON SOD.ServiceID = ST.ServiceID
WHERE SOD.OrderID='Order001' 

INSERT INTO BookingInfo(BookingID, CustomerID, RoomID, UserName, NumberGuest, DateBook) VALUES
('1902200001', 'KANCUS001', 'R0201', 'admin', 2, '2019-02-20'),
('1902200002', 'KANCUS002', 'R0202', 'admin', 1, '2019-02-20'),
('1902200003', 'KANCUS003', 'R0203', 'admin', 3, '2019-02-20'),
('1903090006', 'KANCUS001', 'R0202', 'admin', 1, '2019-03-09'),
('1903090007', 'KANCUS002', 'R0204', 'admin', 2, '2019-03-09'),
('1903090008', 'KANCUS003', 'R0104', 'admin', 4, '2019-03-09'),
('1903090009', 'KANCUS004', 'R0203', 'admin', 4, '2019-03-09'),
('1903090010', 'KANCUS005', 'R0105', 'admin', 2, '2019-03-09'),
('1903120001', 'KANCUS001', 'R0101', 'admin', 1, '2019-03-20'),
('1903120002', 'KANCUS002', 'R0201', 'admin', 2, '2019-03-20'),
('1903120003', 'KANCUS003', 'R0103', 'admin', 4, '2019-03-20'),
('1903120004', 'KANCUS004', 'R0205', 'admin', 4, '2019-03-20'),
('1903120005', 'KANCUS005', 'R0102', 'admin', 2, '2019-03-20')
SELECT * FROM BookingInfo
DELETE FROM BookingInfo WHERE userName LIKE 'user01'

INSERT INTO CheckInOrders(CheckInID, BookingID, CustomerID, RoomID, UserName, CheckInType, NumberOfCustomer, CheckInDate, LeaveDate) VALUES
('CI1902200001', '1902200001', 'KANCUS001', 'R0201', 'admin', 'Reception', 1, '2019-02-20 08:00:00', '2019-02-23 08:00:00'),
('CI1902200002', '1902200002', 'KANCUS002', 'R0202', 'admin', 'Reception', 1, '2019-02-20 08:10:00', '2019-02-24 08:00:00'),
('CI1902200003', '1902200003', 'KANCUS003', 'R0203', 'admin', 'Reception', 1, '2019-02-20 08:20:00', '2019-02-25 08:00:00'),
('CI1903120006', '1903090006', 'KANCUS005', 'R0202', 'admin', 'Reception', 1, '2019-03-09 08:00:00', '2019-03-26 08:00:00'),
('CI1903120007', '1903090007', 'KANCUS004', 'R0204', 'admin', 'Reception', 2, '2019-03-09 09:00:00', '2019-03-27 08:00:00'),
('CI1903120008', '1903090008', 'KANCUS003', 'R0104', 'admin', 'Reception', 4, '2019-03-09 08:30:00', '2019-03-28 08:00:00'),
('CI1903120009', '1903090009', 'KANCUS002', 'R0203', 'admin', 'Reception', 4, '2019-03-09 07:00:00', '2019-04-01 08:00:00'),
('CI1903120010', '1903090010', 'KANCUS001', 'R0105', 'admin', 'Reception', 2, '2019-03-09 10:15:00', '2019-04-02 08:00:00'),
('CI1903120001', '1903120001', 'KANCUS001', 'R0101', 'admin', 'Booking', 1, '2019-03-20', '2019-03-21 08:00:00'),
('CI1903120002', '1903120002', 'KANCUS002', 'R0201', 'admin', 'Booking', 2, '2019-03-20', '2019-03-22 08:00:00'),
('CI1903120003', '1903120003', 'KANCUS003', 'R0103', 'admin', 'Booking', 4, '2019-03-20', '2019-03-25 08:00:00'),
('CI1903120004', '1903120004', 'KANCUS004', 'R0205', 'admin', 'Booking', 4, '2019-03-20', '2019-03-27 08:00:00'),
('CI1903120005', '1903120005', 'KANCUS005', 'R0102', 'admin', 'Booking', 2, '2019-03-20', '2019-03-30 08:00:00')

SELECT * FROM CheckInOrders
DELETE FROM CheckInOrders

SELECT R.*, U.UserName FROM [Role] R, Users U WHERE R.EmployeeID=U.EmployeeID


