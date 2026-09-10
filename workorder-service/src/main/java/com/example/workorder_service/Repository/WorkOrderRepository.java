package com.example.workorder_service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.workorder_service.Entity.WorkOrder;

public interface WorkOrderRepository extends JpaRepository<WorkOrder, String> {
	
	

}
