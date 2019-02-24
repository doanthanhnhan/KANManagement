create table BookingInfo (
	BookingID nvarchar(10) primary key,
	FirstName nvarchar(50) not null,
	LastName nvarchar(50) not null,
	Email varchar(100) not null,
	Phone varchar(10) not null,
	Note nvarchar(1000),
	Company nvarchar(100),
	RoomType nvarchar(15) not null,
	NumberGuest tinyint not null,
	DateBook date not null,
	BookDrive bit,
	Flight varchar(50)
)