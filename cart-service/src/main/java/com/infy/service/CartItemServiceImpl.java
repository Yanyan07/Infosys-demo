package com.infy.service;

import com.infy.dto.CartItemDTO;
import com.infy.dto.MedicineDTO;
import com.infy.entity.Cart;
import com.infy.entity.CartItem;
import com.infy.exception.InsufficientStockException;
import com.infy.exception.NotFoundException;
import com.infy.repository.CartItemRepository;
import com.infy.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService{
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    @Transactional
    public String addItemToCart(CartItemDTO dto) {
        if(dto==null || dto.getQuantity()<=0) {
            throw new IllegalArgumentException("Invalid cart item or quantity!");
        }

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

        Optional<CartItem> existingItem = cartItemRepository.findByCart_CartIdAndMedicineId(
                dto.getCartId(),
                dto.getMedicineId()
        );
        int quantityTotal = dto.getQuantity();
        if(existingItem.isPresent()) {
            quantityTotal += existingItem.get().getQuantity();
        }
        if(quantityTotal > medicineDTO.getQuantity()) {
            throw new InsufficientStockException("Stock is insufficient!");
        }

        if(existingItem.isPresent()) {
            // Medicine already exists: update its quantity
            CartItem item = existingItem.get();
            item.setQuantity(quantityTotal);
            cartItemRepository.save(item);
        }else {
            // New medicine: create a cart item
            CartItem item = new CartItem();
            item.setMedicineId(dto.getMedicineId());
            item.setQuantity(dto.getQuantity());
            item.setUnitPrice(medicineDTO.getPrice());
            item.setCart(cart);
            cartItemRepository.save(item);
        }

        return "Item saved successfully!";
    }
}
