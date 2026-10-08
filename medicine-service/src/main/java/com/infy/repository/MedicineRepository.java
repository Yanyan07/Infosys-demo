package com.infy.repository;

import com.infy.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicineRepository extends JpaRepository<Medicine,Integer> {
    List<Medicine> findByCategory(String category);
}
