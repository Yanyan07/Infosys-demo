package com.infy.service;

import com.infy.dto.CartDTO;
import com.infy.dto.CartItemDTO;
import com.infy.dto.CartItemResponseDTO;
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

import java.util.ArrayList;
import java.util.List;
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
    public String addMedicineToCart(CartItemDTO dto) {
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

    @Override
    @Transactional
    public String modifyQuantityOfMedicineInCart(CartItemDTO dto) {
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

        //Find the existing cart item
        CartItem item = cartItemRepository.findByCart_CartIdAndMedicineId(
                dto.getCartId(), dto.getMedicineId()
        ).orElseThrow(() -> new NotFoundException("Medicine not found in cart!"));

        //Check available stock
        if(dto.getQuantity() > medicineDTO.getQuantity()) {
            throw new InsufficientStockException("Stock is insufficient!");
        }

        //update the quantity
        item.setQuantity(dto.getQuantity());
        cartItemRepository.save(item);

        return "Cart item quantity updated successfully!";
    }

    @Override
    public CartDTO getMedicineFromCart(Integer cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new NotFoundException("Cart not found!"));
        CartDTO cartDTO = new CartDTO();
        cartDTO.setCartId(cart.getCartId());
        cartDTO.setCustomerId(cart.getCustomerId());

        List<CartItemResponseDTO> items = new ArrayList<>();
        double totalAmount = 0.0;
        for(CartItem cartItem : cart.getItems()) {
            MedicineDTO medicineDTO = restTemplate.getForObject(
                    "http://localhost:8082/medicine/" + cartItem.getMedicineId(),
                    MedicineDTO.class
            );
            CartItemResponseDTO dto = new CartItemResponseDTO();
            dto.setMedicineId(cartItem.getMedicineId());
            dto.setMedicineName(medicineDTO.getMedicineName());
            dto.setPrice(medicineDTO.getPrice());
            dto.setQuantity(cartItem.getQuantity());
            double subtotal = medicineDTO.getPrice() * cartItem.getQuantity();
            dto.setSubtotal(subtotal);
            items.add(dto);
            totalAmount += subtotal;
        }
        cartDTO.setItems(items);
        cartDTO.setTotalAmount(totalAmount);
        return cartDTO;
    }

    @Override
    @Transactional
    public String deleteMedicineFromCart(Integer cartId, Integer medicineId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new NotFoundException("Cart not found!"));
        CartItem cartItem = cart.getItems().stream()
                .filter(item -> item.getMedicineId().equals(medicineId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Medicine ot found in cart!"));
        cartItemRepository.delete(cartItem);

        return "Medicine deleted from cart successfully!";
    }

    @Override
    @Transactional
    public String deleteAllMedicineFromCart(Integer cartId) {

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() ->
                        new NotFoundException("Cart not found!"));

        List<CartItem> cartItems = cart.getItems();

        if (cartItems.isEmpty()) {
            return "Cart is already empty!";
        }

        cartItemRepository.deleteAll(cartItems);
        cart.getItems().clear();

        return "All medicines deleted from cart successfully!";
    }
}
