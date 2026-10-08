package com.infy.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;
    private String customerName;
    private String customerEmailId;
    private String contactNumber;
    private String password;
    private String gender;
    private LocalDate dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "prime_plan_id")
    private PrimePlan primePlan;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmailId() {
        return customerEmailId;
    }

    public void setCustomerEmailId(String customerEmailId) {
        this.customerEmailId = customerEmailId;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public PrimePlan getPrimePlan() {
        return primePlan;
    }

    public void setPrimePlan(PrimePlan primePlan) {
        this.primePlan = primePlan;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(customerId, customer.customerId) && Objects.equals(customerName, customer.customerName) && Objects.equals(customerEmailId, customer.customerEmailId) && Objects.equals(contactNumber, customer.contactNumber) && Objects.equals(password, customer.password) && Objects.equals(gender, customer.gender) && Objects.equals(dateOfBirth, customer.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, customerName, customerEmailId, contactNumber, password, gender, dateOfBirth);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", customerEmailId='" + customerEmailId + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", password='" + password + '\'' +
                ", gender='" + gender + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }
}
