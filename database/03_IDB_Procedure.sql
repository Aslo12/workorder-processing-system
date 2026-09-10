-- ============================================
-- Procedure to process Intermediate Work Orders
-- ============================================

USE IDB;

DELIMITER $$

CREATE PROCEDURE sp_ProcessIntermediateWorkOrders()
BEGIN

    START TRANSACTION;

    -- Update ProductionDB WorkOrder status
    UPDATE ProductionDB.WorkOrder w
    JOIN IDB.IntermediateDB_WorkOrder i
        ON w.order_number = i.order_number
    SET w.status = 'RUNNING'
    WHERE i.read_status = '1'
      AND w.order_number = i.order_number;

    -- Mark IDB work order as processed
    UPDATE IDB.IntermediateDB_WorkOrder i
    JOIN ProductionDB.WorkOrder w
        ON i.order_number = w.order_number
    SET i.read_status = '2'
    WHERE i.read_status = '1'
      AND i.order_number = w.order_number;

    COMMIT;

END$$

DELIMITER ;