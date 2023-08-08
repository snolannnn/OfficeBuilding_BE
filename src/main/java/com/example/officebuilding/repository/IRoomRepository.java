package com.example.officebuilding.repository;

import com.example.officebuilding.entities.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRoomRepository extends JpaRepository<RoomEntity,Integer> {
    List<RoomEntity> findByFloorId(Integer floorId);
}
