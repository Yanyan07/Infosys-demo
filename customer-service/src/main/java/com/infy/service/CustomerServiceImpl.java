package com.infy.service;

import com.infy.dto.AddressDTO;
import com.infy.dto.CustomerDTO;
import com.infy.dto.LoginDTO;
import com.infy.dto.RegisterDTO;
import com.infy.entity.Address;
import com.infy.entity.Customer;
import com.infy.exception.DuplicateResourceException;
import com.infy.exception.NotFoundException;
import com.infy.repository.AddressRepository;
import com.infy.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private RestTemplate restTemplate;


    @Override
    @Transactional
    public String register(RegisterDTO registerDTO) {
        if(registerDTO == null) {
            throw new IllegalArgumentException("Invalid registration input");
        }

        String email = registerDTO.getCustomerEmailId();
        customerRepository.findByCustomerEmailId(email)
                .ifPresent(customer -> {
                    throw new DuplicateResourceException(
                            "Customer already exists!");
                });
        Customer customer = new Customer();
        customer.setCustomerName(registerDTO.getCustomerName());
        customer.setCustomerEmailId(registerDTO.getCustomerEmailId());
        customer.setContactNumber(registerDTO.getContactNumber());
        customer.setRole("user");
        customer.setGender(registerDTO.getGender());
        customer.setPassword(registerDTO.getPassword());

        AddressDTO addressDTO = registerDTO.getAddressDTO();
        Address address = addressRepository.findByAddressNameAndCityAndStateAndZipcode(
                addressDTO.getAddressName(),
                addressDTO.getCity(),
                addressDTO.getState(),
                addressDTO.getZipcode()
        )
                .orElseGet(() -> {
                    Address newAddress = new Address();
                    newAddress.setAddressName(addressDTO.getAddressName());
                    newAddress.setCity(addressDTO.getCity());
                    newAddress.setState(addressDTO.getState());
                    newAddress.setZipcode(addressDTO.getZipcode());
                    return addressRepository.save(newAddress);
                });

        customer.setAddress(address);
        Customer savedCustomer = customerRepository.save(customer);

        //automatically create an empty cart

        try {
            restTemplate.postForObject(
                    "http://localhost:8083/cart/"
                            + savedCustomer.getCustomerId(),
                    null,
                    String.class
            );
        } catch (HttpClientErrorException e) {
            throw new IllegalStateException(
                    "Failed to create cart for customer id: "
                            + savedCustomer.getCustomerId(),
                    e
            );
        } catch (ResourceAccessException e) {
            throw new IllegalStateException(
                    "Cart Service is unavailable.",
                    e
            );
        }

        return "Customer registered successfully";
    }

    @Override
    public String login(LoginDTO loginDTO) {
        Optional<Customer> customerOptional = customerRepository.findByCustomerEmailIdAndPassword(
                loginDTO.getCustomerEmailId(),
                loginDTO.getPassword()
        );
        if(customerOptional.isPresent()) {
            return "Login successful!";
        }
        throw new IllegalArgumentException("Failed to login, please try again.");
    }

    @Override
    public CustomerDTO getCustomerById(Integer customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new NotFoundException("Customer not found!"));

        CustomerDTO dto = new CustomerDTO();
        dto.setCustomerId(customer.getCustomerId());
        dto.setCustomerName(customer.getCustomerName());
        dto.setCustomerEmailId(customer.getCustomerEmailId());

        return dto;
    }
}
