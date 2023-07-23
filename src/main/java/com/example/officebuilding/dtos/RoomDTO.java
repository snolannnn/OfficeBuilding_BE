package com.example.officebuilding.dtos;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class RoomDTO {
    private int id;
    private String roomName;
    private double roomPrice;
    private Integer roomStatus;
    private String roomDesc;
    private Timestamp rTime;

    private Integer floorId;
}
