package com.example.workorder_service.Service;

import java.io.InputStream;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.workorder_service.Entity.Inventory;
import com.example.workorder_service.Repository.InventoryRepository;

@Service
public class InventoryService {

	private final InventoryRepository inventoryRepository;
	public InventoryService(InventoryRepository inventoryRepository)
	{
		this.inventoryRepository=inventoryRepository;	
	}
	
	public void uploadExcel(MultipartFile file) throws Exception {
		
		if (file == null || file.isEmpty()) {
	        throw new IllegalArgumentException("Excel file is empty");
	    }

	    String fileName = file.getOriginalFilename();

	    if (fileName == null || !fileName.toLowerCase().endsWith(".xlsx")) {
	        throw new IllegalArgumentException(
	                "Only .xlsx Excel files are allowed"
	        );
	    }

        InputStream inputStream = file.getInputStream();

        Workbook workbook = new XSSFWorkbook(inputStream);

        Sheet sheet = workbook.getSheetAt(0);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {

            Row row = sheet.getRow(i);

            if (row == null) {
                continue;
            }

            Inventory inventory = new Inventory();

            inventory.setAtrKey(UUID.randomUUID().toString());

            inventory.setMaterialId(
                    row.getCell(0).getStringCellValue()
            );

            inventory.setMaterialCode(
                    row.getCell(1).getStringCellValue()
            );

            inventory.setWorkOrder(
                    row.getCell(2).getStringCellValue()
            );

            inventory.setMaterialQty(
                    (int) row.getCell(3).getNumericCellValue()
            );

            inventory.setBookingQty(
                    (int) row.getCell(4).getNumericCellValue()
            );

            inventoryRepository.save(inventory);
        }

        workbook.close();
    }
}
