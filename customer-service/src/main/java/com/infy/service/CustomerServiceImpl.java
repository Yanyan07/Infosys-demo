package com.infy.service;

import com.infy.dto.AddressDTO;
import com.infy.dto.LoginDTO;
import com.infy.dto.RegisterDTO;
import com.infy.entity.Address;
import com.infy.entity.Customer;
import com.infy.repository.AddressRepository;
import com.infy.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AddressRepository addressRepository;


    @Override
    public String register(RegisterDTO registerDTO) {
        if(registerDTO == null) {
            throw new IllegalArgumentException("Invalid registration input");
        }

        String email = registerDTO.getCustomerEmailId();
        Optional<Customer> customerOptional = customerRepository.findByCustomerEmailId(email);
        if(customerOptional.isPresent()) {
            throw new IllegalArgumentException("Customer already exists!");
        }

        Customer customer = new Customer();
        customer.setCustomerName(registerDTO.getCustomerName());
        customer.setCustomerEmailId(registerDTO.getCustomerEmailId());
        customer.setContactNumber(registerDTO.getContactNumber());
        customer.setDateOfBirth(registerDTO.getDateOfBirth());
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
        customerRepository.save(customer);
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
}
