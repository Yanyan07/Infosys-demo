package com.infy.controller;

import com.infy.dto.MedicineDTO;
import com.infy.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicine")
public class MedicineController {
    @Autowired
    private MedicineService medicineService;

    @GetMapping
    public ResponseEntity<List<MedicineDTO>> getAllMedicine(
            @RequestParam("pageNumber") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize
    ) {
        List<MedicineDTO> medicineDTOList = medicineService.getAllMedicine(pageNumber, pageSize);
        return new ResponseEntity<>(medicineDTOList, HttpStatus.OK);
    }

    @GetMapping("/{medicineId}")
    public ResponseEntity<MedicineDTO> getMedicineById(@PathVariable("medicineId") Integer medicineId) {
        MedicineDTO medicineDTO = medicineService.getMedicineById(medicineId);
        return new ResponseEntity<>(medicineDTO, HttpStatus.OK);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<MedicineDTO>> getMedicineByCategory(@PathVariable("category") String category) {
        List<MedicineDTO> medicineDTOList = medicineService.getMedicinesByCategory(category);
        return new ResponseEntity<>(medicineDTOList, HttpStatus.OK);
    }
}
