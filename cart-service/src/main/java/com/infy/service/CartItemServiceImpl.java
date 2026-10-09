package com.infy.service;

import com.infy.dto.CartItemDTO;
import com.infy.dto.MedicineDTO;
import com.infy.entity.Cart;
import com.infy.entity.CartItem;
import com.infy.exception.NotFoundException;
import com.infy.repository.CartItemRepository;
import com.infy.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CartItemServiceImpl implements CartItemService{
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public CartItem addItemToCart(CartItemDTO dto) {

        Cart cart = cartRepository.findById(dto.getCartId())
                .orElseThrow(() ->
                        new NotFoundException("Cart not found!"));
        MedicineDTO medicineDTO = restTemplate.getForObject(
                "http://localhost:8082/medicine/" + dto.getMedicineId(),
                MedicineDTO.class
        );
        if(medicineDTO == null) {
            throw new NotFoundException("Medicine not found!");
        }

        CartItem item = new CartItem();
        item.setMedicineId(dto.getMedicineId());
        item.setQuantity(dto.getQuantity());
        item.setUnitPrice(medicineDTO.getPrice());
        item.setCart(cart);

        return cartItemRepository.save(item);
    }
}
