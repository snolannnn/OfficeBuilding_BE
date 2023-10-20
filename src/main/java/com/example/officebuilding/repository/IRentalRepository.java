package com.example.officebuilding.repository;

import com.example.officebuilding.entities.RentalEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.text.ParseException;
import java.util.List;

public interface IRentalRepository extends JpaRepository<RentalEntity,Integer> {
    List<RentalEntity> findByReStatus(Integer reStatus);
    List<RentalEntity> findByRoomId(Integer roomId);

    List<RentalEntity> findAllByRoomIdAndReStatus(Integer roomId, Integer reStatus);
}
