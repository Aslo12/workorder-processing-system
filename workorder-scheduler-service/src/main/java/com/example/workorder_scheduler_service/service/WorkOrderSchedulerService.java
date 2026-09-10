package com.example.workorder_scheduler_service.service;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.workorder_scheduler_service.entity.IntermediateDBWorkOrder;
import com.example.workorder_scheduler_service.entity.WorkOrder;
import com.example.workorder_scheduler_service.repository.idb.IntermediateDBWorkOrderRepository;
import com.example.workorder_scheduler_service.repository.production.WorkOrderRepository;



@Service
public class WorkOrderSchedulerService {

	private final IntermediateDBWorkOrderRepository intermediateRepository;
	private final WorkOrderRepository workOrderRepository;
	public WorkOrderSchedulerService(
	        WorkOrderRepository workOrderRepository,
	        IntermediateDBWorkOrderRepository intermediateRepository) {

	    this.workOrderRepository = workOrderRepository;
	    this.intermediateRepository = intermediateRepository;
	}

    public List<WorkOrder> getReleasedWorkOrders() {
        return workOrderRepository.findByStatus("RELEASED");
    }
    @Transactional("productionTransactionManager")
    @Scheduled(fixedDelay = 10000)
    public void processReleasedOrders() {

        List<WorkOrder> releasedOrders =
                workOrderRepository.findByStatus("RELEASED");

        System.out.println("Released orders found: " + releasedOrders.size());

        for (WorkOrder workOrder : releasedOrders) {

            IntermediateDBWorkOrder intermediate =
                    new IntermediateDBWorkOrder();

            intermediate.setOrderNumber(workOrder.getOrderNumber());
            intermediate.setRecipeName(workOrder.getRecipeName());
            intermediate.setReadStatus("1");

            intermediateRepository.save(intermediate);

            System.out.println(
                    "Order inserted into IDB: "
                    + workOrder.getOrderNumber()
            );
            
            int updatedRows = workOrderRepository.updateStatus(
                    workOrder.getOrderNumber(),
                    "WAITING"
            );

            System.out.println(
                    "Order status updated to WAITING. Rows updated: "
                    + updatedRows
            );
        }
    }
}