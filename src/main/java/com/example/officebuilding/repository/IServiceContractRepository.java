package com.example.officebuilding.repository;

import com.example.officebuilding.entities.ServiceContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IServiceContractRepository extends JpaRepository<ServiceContractEntity,Integer> {
}
