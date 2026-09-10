-- ============================================
-- Intermediate Database (IDB)
-- ============================================

CREATE DATABASE IF NOT EXISTS IDB;

USE IDB;

CREATE TABLE IF NOT EXISTS IntermediateDB_WorkOrder (
    order_number VARCHAR(50) PRIMARY KEY,
    recipe_name VARCHAR(100),
    read_status VARCHAR(10)
);