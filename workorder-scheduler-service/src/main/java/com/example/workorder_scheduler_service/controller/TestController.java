package com.example.workorder_scheduler_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.workorder_scheduler_service.entity.WorkOrder;
import com.example.workorder_scheduler_service.service.WorkOrderSchedulerService;

@RestController
public class TestController {

    private final WorkOrderSchedulerService service;

    public TestController(WorkOrderSchedulerService service) {
        this.service = service;
    }

    @GetMapping("/test/released")
    public List<WorkOrder> getReleasedOrders() {
        return service.getReleasedWorkOrders();
    }
}