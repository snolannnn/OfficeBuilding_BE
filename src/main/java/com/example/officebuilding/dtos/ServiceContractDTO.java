package com.example.officebuilding.dtos;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class ServiceContractDTO {
    private int id;
    private String scDateBegin;
    private String scDesc;
    private Timestamp scTime;

    private Integer roomId;
    private Integer customerId;
    private Integer serviceId;
}
