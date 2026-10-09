package com.infy.dto;

import java.util.ArrayList;
import java.util.List;

public class CartDTO {

    private Integer cartId;
    private Integer customerId;
    private List<CartItemDTO> items = new ArrayList<>();

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public List<CartItemDTO> getItems() {
        return items;
    }

    public void setItems(List<CartItemDTO> items) {
        this.items = items;
    }
}