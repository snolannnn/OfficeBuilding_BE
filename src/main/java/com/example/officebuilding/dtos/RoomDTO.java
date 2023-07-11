package com.example.officebuilding.dtos;

import lombok.Data;

import java.sql.Timestamp;
@Data
public class RoomDTO {
    private int id;
    private String rmNumber;
    private int rmStatus;
    private Timestamp updateTime;

    private Integer roomTypeId;
    private Integer hotelId;
}
