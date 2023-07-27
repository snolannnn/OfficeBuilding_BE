package com.example.officebuilding.repository;

import com.example.officebuilding.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICustomerRepository extends JpaRepository<CustomerEntity,Integer> {
}
