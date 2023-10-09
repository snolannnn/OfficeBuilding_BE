package com.example.officebuilding.dtos;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class EquipmentDTO {
    private int id;
    private String equipmentName;
    private Integer equipmentStatus;
    private String equipmentDesc;
    private Timestamp eTime;

    private Integer floorId;
    private Integer roomId;
}
