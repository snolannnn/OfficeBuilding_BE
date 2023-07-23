package com.example.officebuilding.repository;

import com.example.officebuilding.entities.FloorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFloorRepository extends JpaRepository<FloorEntity,Integer> {
}
