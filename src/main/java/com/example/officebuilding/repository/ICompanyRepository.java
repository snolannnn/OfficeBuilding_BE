package com.example.officebuilding.repository;

import com.example.officebuilding.dtos.CompanyDTO;
import com.example.officebuilding.dtos.RentalDTO;
import com.example.officebuilding.entities.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICompanyRepository extends JpaRepository<CompanyEntity,Integer> {
    List<CompanyEntity> findAllByCusStatus(Integer cusStatus);
}
