package com.example.workorder_scheduler_service.repository.production;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.workorder_scheduler_service.entity.WorkOrder;

public interface WorkOrderRepository extends JpaRepository<WorkOrder, String>{

	List<WorkOrder> findByStatus(String status);
	
	@Modifying
    @Query("UPDATE WorkOrder w SET w.status = :status WHERE w.orderNumber = :orderNumber")
    int updateStatus(
            @Param("orderNumber") String orderNumber,
            @Param("status") String status);
}
