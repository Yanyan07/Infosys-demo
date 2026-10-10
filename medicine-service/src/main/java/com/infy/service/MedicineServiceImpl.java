package com.infy.service;

import com.infy.dto.MedicineDTO;
import com.infy.entity.Medicine;
import com.infy.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MedicineServiceImpl implements MedicineService{
    @Autowired
    private MedicineRepository medicineRepository;

    @Override
    public List<MedicineDTO> getAllMedicine(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Medicine> medicinePage = medicineRepository.findAll(pageable);
        List<MedicineDTO> medicineDTOList = new ArrayList<>();
        for(Medicine medicine : medicinePage.getContent()){
            MedicineDTO dto = new MedicineDTO();
            dto.setMedicineId(medicine.getMedicineId());
            dto.setMedicineName(medicine.getMedicineName());
            dto.setManufacturer(medicine.getManufacturer());
            dto.setCategory(medicine.getCategory());
            dto.setPrice(medicine.getPrice());
            dto.setDiscountPercent(medicine.getDiscountPercent());
            dto.setManufacturingDate(medicine.getManufacturingDate());
            dto.setExpiryDate(medicine.getExpiryDate());
            medicineDTOList.add(dto);
        }
        return medicineDTOList;
    }

    @Override
    public MedicineDTO getMedicineById(Integer medicineId) {
        Medicine medicine = medicineRepository.findById(medicineId)
                .orElseThrow(() -> new IllegalArgumentException("Medicine with id: " + medicineId + " not found!"));

        return convertToDTO(medicine);
    }

    @Override
    public List<MedicineDTO> getMedicinesByCategory(String category) {
        List<Medicine> medicineList = medicineRepository.findByCategory(category);
        List<MedicineDTO> medicineDTOList = new ArrayList<>();
        for(Medicine medicine : medicineList) {
            medicineDTOList.add(convertToDTO(medicine));
        }
        return medicineDTOList;
    }

    private MedicineDTO convertToDTO(Medicine medicine) {
        MedicineDTO dto = new MedicineDTO();

        dto.setMedicineId(medicine.getMedicineId());
        dto.setMedicineName(medicine.getMedicineName());
        dto.setManufacturer(medicine.getManufacturer());
        dto.setCategory(medicine.getCategory());
        dto.setPrice(medicine.getPrice());
        dto.setDiscountPercent(medicine.getDiscountPercent());
        dto.setManufacturingDate(medicine.getManufacturingDate());
        dto.setExpiryDate(medicine.getExpiryDate());
        dto.setQuantity(medicine.getQuantity());

        return dto;
    }
}

