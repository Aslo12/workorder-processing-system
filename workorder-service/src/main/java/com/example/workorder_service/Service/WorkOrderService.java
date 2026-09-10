package com.example.workorder_service.Service;

import org.springframework.stereotype.Service;

import com.example.workorder_service.Entity.WorkOrder;
import com.example.workorder_service.Repository.WorkOrderRepository;

@Service
public class WorkOrderService {
	
	private final WorkOrderRepository workOrderRepository;
	public WorkOrderService(WorkOrderRepository workOrderRepository)
	{
		this.workOrderRepository=workOrderRepository;
	}

	public WorkOrder createWorkOrder(WorkOrder workOrder) {

        workOrder.setStatus("RELEASED");

        return workOrderRepository.save(workOrder);
    }
}
