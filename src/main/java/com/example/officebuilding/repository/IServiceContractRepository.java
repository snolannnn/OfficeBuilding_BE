package com.example.officebuilding.repository;

import com.example.officebuilding.entities.ServiceContractEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IServiceContractRepository extends JpaRepository<ServiceContractEntity,Integer> {
    List<ServiceContractEntity> findByScStatus(Integer scStatus);
}
