USE ReportDB;

CREATE TABLE Inventory (
    atr_key CHAR(36) PRIMARY KEY,
    MaterialId VARCHAR(100),
    MaterialCode VARCHAR(100),
    WorkOrder VARCHAR(50),
    MaterialQty INT,
    BookingQty INT
);
