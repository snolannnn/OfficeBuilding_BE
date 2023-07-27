package com.example.officebuilding.repository;

import com.example.officebuilding.entities.RentalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRentalRepository extends JpaRepository<RentalEntity,Integer> {
}
