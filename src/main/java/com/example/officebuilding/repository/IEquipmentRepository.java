package com.example.officebuilding.repository;

import com.example.officebuilding.entities.EquipEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEquipmentRepository extends JpaRepository<EquipEntity,Integer> {
}
