package com.example.workorder_service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.workorder_service.Entity.Inventory;

public interface InventoryRepository  extends JpaRepository<Inventory, String> {

}
