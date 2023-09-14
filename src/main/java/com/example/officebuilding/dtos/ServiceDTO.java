package com.example.officebuilding.dtos;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ServiceDTO {
    private int id;
    private String serviceName;
    private double servicePrice;
    private int serviceStatus;
//    private Integer serviceStatus;
    private String serviceDesc;
    private Timestamp sTime;
}
