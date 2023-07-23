package com.example.officebuilding.entities;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "service")
public class ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String sName;

    @Column(nullable = false)
    private Integer sStatus;

    @Column(nullable = false)
    private double sPrice;

    private String sDesc;
    private Timestamp sTime;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        sTime = new Timestamp(System.currentTimeMillis());
    }
}