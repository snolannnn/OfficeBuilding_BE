package com.example.officebuilding.repository;

import com.example.officebuilding.entities.RoomTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IRoomTypeRepository extends JpaRepository<RoomTypeEntity, Integer> {
    List<RoomTypeEntity> getRoomTypeEntitiesByHotel_Id(Integer id);
    boolean existsByIdAndHotelId(int roomTypeId, int hotelId);
}
