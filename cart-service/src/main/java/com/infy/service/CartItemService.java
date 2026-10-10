package com.infy.service;

import com.infy.dto.CartItemDTO;
import com.infy.entity.CartItem;

public interface CartItemService {
    String addItemToCart(CartItemDTO dto);

    //addMidicineToCart
    //getMedicineFromCart
    //modifyQuantityOfMedicineInCart
    //deleteMedicineFromCart
    //deleteAllMedicineFromCart
}
