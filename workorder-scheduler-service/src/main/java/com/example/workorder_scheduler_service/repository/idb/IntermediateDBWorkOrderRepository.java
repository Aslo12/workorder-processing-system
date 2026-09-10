package com.example.workorder_scheduler_service.repository.idb;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.workorder_scheduler_service.entity.IntermediateDBWorkOrder;

public interface IntermediateDBWorkOrderRepository extends JpaRepository<IntermediateDBWorkOrder, String> {

}
