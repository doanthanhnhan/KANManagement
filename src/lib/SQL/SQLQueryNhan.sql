CREATE TABLE Rooms(
	-- Create columns	
	RoomID varchar(20) UNIQUE NOT NULL,	
	RoomType varchar(20) NOT NULL,	
	PhoneNumber varchar(20),
	RoomOnFloor int,
	RoomArea decimal,
	Active bit NOT NULL,
	-- Create constraint
	CONSTRAINT pk_RoomID PRIMARY KEY (RoomID)	
)

CREATE TABLE CheckInOrders(
	-- Create columns
	ID int IDENTITY(1,1),	
	CheckInID varchar(20) UNIQUE NOT NULL,
	CheckInType varchar(20) NOT NULL,	
	RoomID varchar(20) NOT NULL,
	NumberOfCustomer int NOT NULL,
	CheckInDate date NOT NULL,
	CustomerName nvarchar(50) NOT NULL,
	CustomerID varchar(50) NOT NULL,
	CustomerPassport varchar(50),
	CustomerPhoneNumber varchar (20) NOT NULL,
	CustomerEmail varchar(100),	
	CustomerPackage varchar(100),	
	Active bit NOT NULL,
	-- Create constraint
	CONSTRAINT pk_CheckInOrdersID PRIMARY KEY (ID)
)
DROP TABLE CheckInOrders

CREATE TABLE CheckOutOrders(
	-- Create columns
	ID int IDENTITY(1,1),	
	CheckOutID varchar(20) UNIQUE NOT NULL,
	CheckInID varchar(20) UNIQUE NOT NULL,		
	RoomID varchar(20) NOT NULL,
	CheckInDate date NOT NULL,
	CheckOutDate date NOT NULL,
	CustomerName nvarchar(50) NOT NULL,
	CustomerID varchar(50) NOT NULL,
	CustomerPassport varchar(50),
	CustomerPhoneNumber varchar (20),
	CustomerEmail varchar(100),
	CustomerPackage varchar(100),
	CustomerPayment varchar(20) NOT NULL,
	CustomerBill decimal NOT NULL,	
	Active bit NOT NULL,
	-- Create constraint
	CONSTRAINT pk_CheckOutOrdersID PRIMARY KEY (ID)
)

CREATE TABLE ServicesOrders(
	-- Create columns
	ID int IDENTITY(1,1),	
	OrderID varchar(20) UNIQUE NOT NULL,	
	ServiceName varchar(20) UNIQUE NOT NULL,	
	ServicePrice decimal NOT NULL,
	Active bit NOT NULL,
	-- Create constraint
	CONSTRAINT pk_ServicesOrdersRoomID PRIMARY KEY (ID)	
)