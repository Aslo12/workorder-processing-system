-- ============================================
-- ProductionDB to ReportDB Triggers
-- ============================================

USE ProductionDB;


-- ============================================
-- INSERT Trigger
-- ============================================

DELIMITER $$

CREATE TRIGGER tr_TransferDataFromPdsToReportDB
AFTER INSERT ON WorkOrder
FOR EACH ROW
BEGIN

    INSERT INTO ReportDB.WorkOrder
    (
        order_number,
        order_key,
        recipe_name,
        bom_name,
        planned_qty,
        produced_qty,
        status
    )
    VALUES
    (
        NEW.order_number,
        NEW.order_key,
        NEW.recipe_name,
        NEW.bom_name,
        NEW.planned_qty,
        NEW.produced_qty,
        NEW.status
    );

END$$

DELIMITER ;


-- ============================================
-- UPDATE Trigger
-- ============================================

DELIMITER $$

CREATE TRIGGER tr_TransferDataFromPdsToReportDB_Update
AFTER UPDATE ON WorkOrder
FOR EACH ROW
BEGIN

    UPDATE ReportDB.WorkOrder
    SET
        order_key = NEW.order_key,
        recipe_name = NEW.recipe_name,
        bom_name = NEW.bom_name,
        planned_qty = NEW.planned_qty,
        produced_qty = NEW.produced_qty,
        status = NEW.status
    WHERE order_number = NEW.order_number;

END$$

DELIMITER ;


-- ============================================
-- DELETE Trigger
-- ============================================

DELIMITER $$

CREATE TRIGGER tr_TransferDataFromPdsToReportDB_Delete
AFTER DELETE ON WorkOrder
FOR EACH ROW
BEGIN

    DELETE FROM ReportDB.WorkOrder
    WHERE order_number = OLD.order_number;

END$$

DELIMITER ;



-- ============================================
-- INSERT Trigger
-- ============================================

DELIMITER $$

CREATE TRIGGER tr_TransferInventoryToReportDB
AFTER INSERT ON Inventory
FOR EACH ROW
BEGIN

    INSERT INTO ReportDB.Inventory
    (
        atr_key,
        MaterialId,
        MaterialCode,
        WorkOrder,
        MaterialQty,
        BookingQty
    )
    VALUES
    (
        NEW.atr_key,
        NEW.MaterialId,
        NEW.MaterialCode,
        NEW.WorkOrder,
        NEW.MaterialQty,
        NEW.BookingQty
    );

END$$

DELIMITER ;


-- ============================================
-- UPDATE Trigger
-- ============================================

DELIMITER $$

CREATE TRIGGER tr_UpdateInventoryToReportDB
AFTER UPDATE ON Inventory
FOR EACH ROW
BEGIN

    UPDATE ReportDB.Inventory
    SET
        MaterialId = NEW.MaterialId,
        MaterialCode = NEW.MaterialCode,
        WorkOrder = NEW.WorkOrder,
        MaterialQty = NEW.MaterialQty,
        BookingQty = NEW.BookingQty
    WHERE atr_key = NEW.atr_key;

END$$

DELIMITER ;

-- ============================================
-- DELETE Trigger
-- ============================================

DELIMITER $$

CREATE TRIGGER tr_DeleteInventoryToReportDB
AFTER DELETE ON Inventory
FOR EACH ROW
BEGIN

    DELETE FROM ReportDB.Inventory
    WHERE atr_key = OLD.atr_key;

END$$

DELIMITER ;