package com.infy.repository;

import com.infy.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem,Integer> {
    Optional<CartItem> findByCart_CartIdAndMedicineId(Integer cartId, Integer medicineId);
}
