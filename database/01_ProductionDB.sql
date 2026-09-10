-- ============================================
-- Production Database
-- ============================================

CREATE DATABASE IF NOT EXISTS ProductionDB;

USE ProductionDB;

CREATE TABLE IF NOT EXISTS WorkOrder (
    order_number VARCHAR(50) PRIMARY KEY,
    order_key VARCHAR(50),
    recipe_name VARCHAR(100),
    bom_name VARCHAR(100),
    planned_qty INT,
    produced_qty INT,
    status VARCHAR(30) DEFAULT 'RELEASED'
);