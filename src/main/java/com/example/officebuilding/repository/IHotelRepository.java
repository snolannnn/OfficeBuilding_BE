package com.example.officebuilding.repository;

import com.example.officebuilding.entities.HotelEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IHotelRepository extends JpaRepository<HotelEntity,Integer> {

}
