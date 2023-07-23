package com.example.officebuilding.dtos;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class FloorDTO {
    private int id;
    private String floorName;
    private Timestamp fTime;
}
