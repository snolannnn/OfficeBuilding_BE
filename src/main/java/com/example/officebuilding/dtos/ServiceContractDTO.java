package com.example.officebuilding.dtos;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ServiceContractDTO {
    private int id;
    private String scDateEnd;
    private String scDateBegin;
    private double scPrice;
    private int scStatus;
    private Timestamp scTime;

    private Integer companyId;
    private Integer serviceId;
}
