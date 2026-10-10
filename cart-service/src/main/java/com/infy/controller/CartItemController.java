package com.infy.controller;

import com.infy.dto.CartItemDTO;
import com.infy.entity.CartItem;
import com.infy.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart-item")
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;

    @PostMapping
    public ResponseEntity<String> addItemToCart(@RequestBody CartItemDTO cartItemDTO) {
        return new ResponseEntity<>(cartItemService.addItemToCart(cartItemDTO), HttpStatus.CREATED);
    }
}
