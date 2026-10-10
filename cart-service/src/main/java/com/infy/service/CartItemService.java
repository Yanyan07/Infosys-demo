package com.infy.service;

import com.infy.dto.CartDTO;
import com.infy.dto.CartItemDTO;
import com.infy.dto.CartItemResponseDTO;
import com.infy.entity.CartItem;

import java.util.List;

public interface CartItemService {
    String addMedicineToCart(CartItemDTO dto);
    String modifyQuantityOfMedicineInCart(CartItemDTO dto);
    CartDTO getMedicineFromCart(Integer cartId);
    String deleteMedicineFromCart(Integer cartId, Integer medicineId);
    String deleteAllMedicineFromCart(Integer cartId);

}
