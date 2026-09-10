-- ============================================
-- MySQL Event for processing Intermediate Work Orders
-- ============================================

USE IDB;

CREATE EVENT ev_ProcessIntermediateWorkOrders
ON SCHEDULE EVERY 1 MINUTE
DO
    CALL sp_ProcessIntermediateWorkOrders();