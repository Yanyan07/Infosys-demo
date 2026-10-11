package com.infy.service;

import com.infy.dto.CustomerDTO;
import com.infy.entity.Cart;
import com.infy.exception.NotFoundException;
import com.infy.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class CartServiceImpl implements CartService{
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String createCart(Integer customerId) {
        // Check whether the customer already has a cart
        if (cartRepository.existsByCustomerId(customerId)) {
            return "Cart already exists for customer id: " + customerId;
        }

        // Verify that the customer exists
//        try {
//            restTemplate.getForObject(
//                    "http://localhost:8081/customer/" + customerId,
//                    CustomerDTO.class
//            );
//        } catch (HttpClientErrorException.NotFound e) {
//            throw new NotFoundException(
//                    "Customer with id: " + customerId + " not found!"
//            );
//        }

        Cart cart = new Cart();
        cart.setCustomerId(customerId);
        cart.setItems(new ArrayList<>());
        cartRepository.save(cart);

        return "Cart created successfully!";
    }
}
