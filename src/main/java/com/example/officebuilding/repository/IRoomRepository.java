package com.example.officebuilding.repository;

import com.example.officebuilding.entities.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoomRepository extends JpaRepository<RoomEntity,Integer> {
}
