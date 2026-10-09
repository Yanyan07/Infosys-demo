package com.infy.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class PrimePlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer planId;
    private String planName;
    private String planDescription;

    @OneToMany(mappedBy = "primePlan")
    private List<Customer> customers = new ArrayList<>();

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanDescription() {
        return planDescription;
    }

    public void setPlanDescription(String planDescription) {
        this.planDescription = planDescription;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PrimePlan primePlan = (PrimePlan) o;
        return Objects.equals(planId, primePlan.planId) && Objects.equals(planName, primePlan.planName) && Objects.equals(planDescription, primePlan.planDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(planId, planName, planDescription);
    }

    @Override
    public String toString() {
        return "PrimePlan{" +
                "planId=" + planId +
                ", planName='" + planName + '\'' +
                ", planDescription='" + planDescription + '\'' +
                '}';
    }
}
