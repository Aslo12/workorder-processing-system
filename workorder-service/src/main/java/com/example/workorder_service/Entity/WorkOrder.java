package com.example.workorder_service.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="WorkOrder")
public class WorkOrder {
	
	    @Id
	    @Column(name = "order_number")
	    private String orderNumber;

	    @Column(name = "order_key")
	    private String orderKey;

	    @Column(name = "recipe_name")
	    private String recipeName;

	    @Column(name = "bom_name")
	    private String bomName;

	    @Column(name = "planned_qty")
	    private Integer plannedQty;

	    @Column(name = "produced_qty")
	    private Integer producedQty;

	    @Column(name = "status")
	    private String status;

		public String getOrderNumber() {
			return orderNumber;
		}

		public void setOrderNumber(String orderNumber) {
			this.orderNumber = orderNumber;
		}

		public String getOrderKey() {
			return orderKey;
		}

		public void setOrderKey(String orderKey) {
			this.orderKey = orderKey;
		}

		public String getRecipeName() {
			return recipeName;
		}

		public void setRecipeName(String recipeName) {
			this.recipeName = recipeName;
		}

		public String getBomName() {
			return bomName;
		}

		public void setBomName(String bomName) {
			this.bomName = bomName;
		}

		public Integer getPlannedQty() {
			return plannedQty;
		}

		public void setPlannedQty(Integer plannedQty) {
			this.plannedQty = plannedQty;
		}

		public Integer getProducedQty() {
			return producedQty;
		}

		public void setProducedQty(Integer producedQty) {
			this.producedQty = producedQty;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}
	    

}
