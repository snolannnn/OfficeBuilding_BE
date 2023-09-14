package com.example.officebuilding.dtos;

import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class RentalDTO {
    private int id;
    private String reDateBegin;
    private String reDateEnd;
    private double rePrice;
    private int reStatus;
    private Timestamp reTime;

    private Integer roomId;
    private Integer companyId;
}
