package com.example.officebuilding.repository;

import com.example.officebuilding.entities.EquipEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IEquipmentRepository extends JpaRepository<EquipEntity,Integer> {
    List<EquipEntity> findByFloorId(Integer floorId);
}
