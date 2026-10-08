package com.infy.repository;

import com.infy.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address,Integer> {
    Optional<Address> findByAddressNameAndCityAndStateAndZipcode(
            String addressName,
            String city,
            String state,
            String zipcode
    );
}
