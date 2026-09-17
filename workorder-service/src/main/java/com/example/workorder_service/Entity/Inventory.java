package com.example.workorder_service.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Inventory")
public class Inventory {

    @Id
    @Column(name = "atr_key")
    private String atrKey;

    @Column(name = "MaterialId")
    private String materialId;

    @Column(name = "MaterialCode")
    private String materialCode;

    @Column(name = "WorkOrder")
    private String workOrder;

    @Column(name = "MaterialQty")
    private Integer materialQty;

    @Column(name = "BookingQty")
    private Integer bookingQty;


    public String getAtrKey() {
        return atrKey;
    }

    public void setAtrKey(String atrKey) {
        this.atrKey = atrKey;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public String getMaterialCode() {
        return materialCode;
    }

    public void setMaterialCode(String materialCode) {
        this.materialCode = materialCode;
    }

    public String getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(String workOrder) {
        this.workOrder = workOrder;
    }

    public Integer getMaterialQty() {
        return materialQty;
    }

    public void setMaterialQty(Integer materialQty) {
        this.materialQty = materialQty;
    }

    public Integer getBookingQty() {
        return bookingQty;
    }

    public void setBookingQty(Integer bookingQty) {
        this.bookingQty = bookingQty;
    }
}
