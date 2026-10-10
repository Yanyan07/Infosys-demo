package com.infy.controller;

import com.infy.dto.CartDTO;
import com.infy.dto.CartItemDTO;
import com.infy.dto.CartItemResponseDTO;
import com.infy.entity.CartItem;
import com.infy.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart-item")
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;

    @PostMapping
    public ResponseEntity<String> addMedicineToCart(@RequestBody CartItemDTO cartItemDTO) {
        return new ResponseEntity<>(cartItemService.addMedicineToCart(cartItemDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<String> modifyQuantityOfMedicineInCart(@RequestBody CartItemDTO dto) {
        return new ResponseEntity<>(cartItemService.modifyQuantityOfMedicineInCart(dto), HttpStatus.OK);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartDTO> getMedicineFromCart(@PathVariable("cartId") Integer cartId) {
        return new ResponseEntity<>(cartItemService.getMedicineFromCart(cartId), HttpStatus.OK);
    }

    @DeleteMapping("/{cartId}/{medicineId}")
    public ResponseEntity<String> deleteMedicineFromCart(
            @PathVariable("cartId") Integer cartId, @PathVariable("medicineId") Integer medicineId) {
        return new ResponseEntity<>(cartItemService.deleteMedicineFromCart(cartId, medicineId), HttpStatus.OK);
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<String> deleteAllMedicineFromCart(@PathVariable("cartId") Integer cartId) {
        return new ResponseEntity<>(cartItemService.deleteAllMedicineFromCart(cartId), HttpStatus.OK);
    }

}
