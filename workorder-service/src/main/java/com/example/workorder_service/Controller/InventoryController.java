package com.example.workorder_service.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.workorder_service.Service.InventoryService;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadInventory(
            @RequestParam("file") MultipartFile file) {

        try {

            inventoryService.uploadExcel(file);

            return ResponseEntity.ok(
                    "Inventory Excel uploaded successfully"
            );

        } catch (Exception e) {

            return ResponseEntity.badRequest().body(
                    "Failed to upload Inventory Excel: "
                    + e.getMessage()
            );
        }
    }
}	
