package com.example.officebuilding.dtos;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class OrderDTO {
    private int id;
    private Integer orderStatus;
    private Double orderCost;
    private String orderCheckIn;
    private String orderCheckOut;
    private Timestamp updateTime;

    private Integer roomTypeId;
    private Integer hotelId;
    private Integer userId;
}
