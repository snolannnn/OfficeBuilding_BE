package com.example.officebuilding.repository;

import com.example.officebuilding.entities.InfoEntity;
import com.example.officebuilding.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IInfoRepository extends JpaRepository<InfoEntity,Integer> {
    List<InfoEntity> findByUserId(Integer userId);
}
