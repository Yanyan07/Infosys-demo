package com.infy.service;

import com.infy.dto.MedicineDTO;
import java.util.List;

public interface MedicineService {
    List<MedicineDTO> getAllMedicine(Integer pageNumber, Integer pageSize);
    MedicineDTO getMedicineById(Integer medicineId);
    List<MedicineDTO> getMedicinesByCategory(String category);
}
