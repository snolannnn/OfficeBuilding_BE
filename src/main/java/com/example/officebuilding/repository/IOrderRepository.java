package com.example.officebuilding.repository;

import com.example.officebuilding.entities.OrderEntity;
import com.example.officebuilding.entities.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderRepository extends JpaRepository<OrderEntity,Integer> {
    List<OrderEntity> findByUserId(Integer userId);
    List<OrderEntity> findByHotelId(Integer userId);
}
