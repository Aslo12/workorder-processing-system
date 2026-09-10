# Work Order Processing System

A Spring Boot-based Work Order Processing System that demonstrates communication between multiple databases, scheduled processing, MySQL stored procedures/events, and synchronization between ProductionDB and ReportDB.

## Project Overview

This project processes Work Orders through multiple stages:

1. A Work Order is created through **Service 1**.
2. Service 1 stores the Work Order in **ProductionDB** with status `RELEASED`.
3. **Service 2** continuously checks ProductionDB for released Work Orders.
4. Service 2 transfers the Work Order to **IDB.IntermediateDB_WorkOrder** with `read_status = 1`.
5. Service 2 updates the ProductionDB Work Order status to `WAITING`.
6. A MySQL Event automatically executes the IDB processing procedure.
7. The procedure updates the ProductionDB Work Order status to `RUNNING`.
8. The procedure updates IDB `read_status` from `1` to `2`.
9. Database triggers synchronize Work Order changes from ProductionDB to ReportDB.

## Architecture

```text
                    +----------------------+
                    |      Client/User     |
                    +----------+-----------+
                               |
                               | POST /workorder/download
                               v
                    +----------------------+
                    |      Service 1       |
                    |   Work Order API     |
                    +----------+-----------+
                               |
                               | INSERT
                               v
                    +----------------------+
                    |    ProductionDB      |
                    |      WorkOrder       |
                    |   status=RELEASED    |
                    +----------+-----------+
                               |
                               | Scheduler
                               | checks every 5 sec
                               v
                    +----------------------+
                    |      Service 2       |
                    | Work Order Scheduler  |
                    +----------+-----------+
                               |
                               | INSERT
                               v
                    +----------------------+
                    |         IDB          |
                    | IntermediateDB_      |
                    |      WorkOrder       |
                    | read_status = 1      |
                    +----------+-----------+
                               |
                               | MySQL Event
                               | every 1 minute
                               v
                    +----------------------+
                    | Stored Procedure     |
                    | sp_ProcessIntermediate|
                    |     WorkOrders()     |
                    +----------+-----------+
                               |
                 +-------------+-------------+
                 |                           |
                 v                           v
        ProductionDB                    IDB
        status=RUNNING             read_status=2


ProductionDB WorkOrder
        |
        | INSERT / UPDATE / DELETE
        | MySQL Triggers
        v
+----------------------+
|      ReportDB        |
|      WorkOrder       |
+----------------------+
```

## Project Structure

```text
workorder-processing-system/
│
├── workorder-service/
│   └── Spring Boot Service 1
│
├── workorder-scheduler-service/
│   └── Spring Boot Service 2
│
├── database/
│   ├── 01_ProductionDB.sql
│   ├── 02_IDB.sql
│   ├── 03_IDB_Procedure.sql
│   ├── 04_IDB_Event.sql
│   └── 05_ReportDB_Triggers.sql
│
└── README.md
```

## Service 1 - Work Order Service

Service 1 is responsible for accepting Work Order information from the user/client.

### Endpoint

```http
POST /workorder/download
```

### Example Request

```json
{
    "orderNumber": "WO1001",
    "orderKey": "KEY1001",
    "recipeName": "Recipe_A",
    "bomName": "BOM_A",
    "plannedQty": 100,
    "producedQty": 0
}
```

### Processing

When a Work Order is received:

```text
Client
  ↓
POST /workorder/download
  ↓
Service 1
  ↓
ProductionDB.WorkOrder
  ↓
status = RELEASED
```

Service 1 automatically sets the Work Order status to:

```text
RELEASED
```

## Service 2 - Work Order Scheduler Service

Service 2 acts as a bridge between **ProductionDB** and **IDB**.

It continuously checks ProductionDB for Work Orders having:

```text
status = RELEASED
```

The scheduler runs every 5 seconds.

### Processing Flow

```text
ProductionDB.WorkOrder
        |
        | status = RELEASED
        v
Service 2 Scheduler
        |
        | Insert
        v
IDB.IntermediateDB_WorkOrder
        |
        | read_status = 1
        v
ProductionDB.WorkOrder
        |
        | status = WAITING
        v
Completed
```

### IDB Record

The transferred record contains:

* `order_number`
* `recipe_name`
* `read_status`

The initial `read_status` is:

```text
1
```

After the IDB processing is completed, it becomes:

```text
2
```

## Database Structure

### ProductionDB

Database:

```text
ProductionDB
```

Table:

```text
WorkOrder
```

Columns:

| Column       | Description                           |
| ------------ | ------------------------------------- |
| order_number | Primary key of the Work Order         |
| order_key    | Work Order key                        |
| recipe_name  | Recipe associated with the Work Order |
| bom_name     | Bill of Material name                 |
| planned_qty  | Planned quantity                      |
| produced_qty | Produced quantity                     |
| status       | Current Work Order status             |

### IDB

Database:

```text
IDB
```

Table:

```text
IntermediateDB_WorkOrder
```

Columns:

| Column       | Description       |
| ------------ | ----------------- |
| order_number | Work Order number |
| recipe_name  | Recipe name       |
| read_status  | Processing status |

### ReportDB

Database:

```text
ReportDB
```

Table:

```text
WorkOrder
```

ReportDB maintains a synchronized copy of the ProductionDB WorkOrder data.

## MySQL Stored Procedure

The following stored procedure processes records from IDB:

```text
sp_ProcessIntermediateWorkOrders()
```

### Processing

It checks records where:

```text
read_status = 1
```

Then:

1. Updates the corresponding ProductionDB Work Order status to `RUNNING`.
2. Updates the IDB `read_status` from `1` to `2`.
3. Uses a transaction to process both operations.

Flow:

```text
IDB read_status = 1
        |
        v
Stored Procedure
        |
        +----> ProductionDB status = RUNNING
        |
        +----> IDB read_status = 2
```

## MySQL Event

A MySQL Event is configured to automatically execute the stored procedure.

Event:

```text
ev_ProcessIntermediateWorkOrders
```

Schedule:

```text
Every 1 minute
```

The event executes:

```sql
CALL sp_ProcessIntermediateWorkOrders();
```

This removes the need to manually execute the stored procedure.

## ProductionDB to ReportDB Synchronization

MySQL triggers are used to keep ReportDB synchronized with ProductionDB.

### INSERT Trigger

```text
tr_TransferDataFromPdsToReportDB
```

When a new Work Order is inserted into:

```text
ProductionDB.WorkOrder
```

the same record is inserted into:

```text
ReportDB.WorkOrder
```

### UPDATE Trigger

```text
tr_TransferDataFromPdsToReportDB_Update
```

When a Work Order is updated in ProductionDB, the corresponding Work Order in ReportDB is updated.

### DELETE Trigger

```text
tr_TransferDataFromPdsToReportDB_Delete
```

When a Work Order is deleted from ProductionDB, the corresponding record is deleted from ReportDB.

### Synchronization Flow

```text
ProductionDB.WorkOrder
        |
        +---- INSERT ----> ReportDB.WorkOrder
        |
        +---- UPDATE ----> ReportDB.WorkOrder
        |
        +---- DELETE ----> ReportDB.WorkOrder
```

## Complete Work Order Lifecycle

The complete processing flow is:

```text
1. Client sends Work Order
            ↓
2. Service 1 receives request
            ↓
3. Work Order inserted into ProductionDB
            ↓
4. Status = RELEASED
            ↓
5. Service 2 Scheduler detects RELEASED order
            ↓
6. Order inserted into IDB
            ↓
7. IDB read_status = 1
            ↓
8. ProductionDB status = WAITING
            ↓
9. MySQL Event executes every 1 minute
            ↓
10. Stored Procedure processes IDB record
            ↓
11. ProductionDB status = RUNNING
            ↓
12. IDB read_status = 2
```

At the same time:

```text
ProductionDB
     |
     | INSERT / UPDATE / DELETE
     ↓
MySQL Triggers
     |
     ↓
ReportDB
```

## Technologies Used

* Java 17
* Spring Boot 4.1.1
* Spring Web MVC
* Spring Data JPA
* Hibernate
* MySQL 8
* Maven
* REST API
* MySQL Stored Procedures
* MySQL Events
* MySQL Triggers
* Eclipse IDE

## Key Spring Boot Concepts Used

### Multiple Data Sources

Service 2 connects to two databases:

```text
ProductionDB
IDB
```

Separate data sources, entity manager factories, transaction managers, and repository packages are configured for each database.

### Scheduling

Spring's scheduling mechanism is used to continuously process released Work Orders.

```java
@Scheduled(fixedDelay = 5000)
```

### JPA

Spring Data JPA is used for database operations through repositories instead of writing SQL for every basic CRUD operation.

### Transactions

The ProductionDB status update uses a transaction manager to ensure the update query executes inside an active transaction.

## How to Run

### 1. Create Databases

Open MySQL Workbench and execute the SQL files from the `database` folder in the following order:

```text
01_ProductionDB.sql
02_IDB.sql
03_IDB_Procedure.sql
04_IDB_Event.sql
05_ReportDB_Triggers.sql
```

### 2. Configure MySQL

Update the database credentials in the Spring Boot `application.properties` files according to your local MySQL configuration.

### 3. Run Service 1

Start the:

```text
workorder-service
```

Spring Boot application.

### 4. Run Service 2

Start the:

```text
workorder-scheduler-service
```

Spring Boot application.

### 5. Send a Work Order

Use Postman or another REST client to call:

```http
POST /workorder/download
```

with a JSON Work Order request.

### 6. Verify Processing

Check:

```sql
SELECT * FROM ProductionDB.WorkOrder;
```

and:

```sql
SELECT * FROM IDB.IntermediateDB_WorkOrder;
```

The Work Order will move through the following statuses:

```text
RELEASED
   ↓
WAITING
   ↓
RUNNING
```

The IDB `read_status` will move:

```text
1
↓
2
```

## Learning Objectives

This project demonstrates practical implementation of:

* Spring Boot REST APIs
* Spring Data JPA
* Multiple database connections
* Multiple EntityManagerFactory configurations
* Multiple transaction managers
* Scheduled processing
* Database-to-database communication
* MySQL stored procedures
* MySQL scheduled events
* MySQL triggers
* Transaction management
* Work Order lifecycle processing
* ProductionDB and ReportDB synchronization
