package com.example.workorder_scheduler_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "IntermediateDB_WorkOrder")
public class IntermediateDBWorkOrder {

    @Id
    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "recipe_name")
    private String recipeName;

    @Column(name = "read_status")
    private String readStatus;

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public String getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(String readStatus) {
        this.readStatus = readStatus;
    }
}