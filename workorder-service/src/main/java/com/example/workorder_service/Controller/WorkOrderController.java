package com.example.workorder_service.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.workorder_service.Entity.WorkOrder;
import com.example.workorder_service.Service.WorkOrderService;

@RestController
@RequestMapping("/workorder")
public class WorkOrderController {

	 private final WorkOrderService workOrderService;
	 public WorkOrderController(WorkOrderService workOrderService) {
	        this.workOrderService = workOrderService;
	    }
	 
	 @PostMapping("/download")
	    public WorkOrder download(@RequestBody WorkOrder workOrder) {

	        return workOrderService.createWorkOrder(workOrder);
	    }
	 @GetMapping("/get")
	 public String gett() {
		 return "getting";
	 }
}
