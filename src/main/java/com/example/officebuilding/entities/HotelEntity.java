package com.example.officebuilding.entities;
import lombok.Data;

import javax.persistence.*;
@Entity
@Data
@Table(name = "hotel")
public class HotelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String hotelName;

    @Column(nullable = false)
    private String hotelPhone;

    @Column(nullable = false)
    private String hotelAddress;

    private String hotelDesc;
    private String hotelImg;
}
