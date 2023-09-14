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
    private String serviceName;

    @Column(nullable = false)
    private Integer serviceStatus;

    @Column(nullable = false)
    private double servicePrice;

    private String serviceDesc;
    private Timestamp sTime;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        sTime = new Timestamp(System.currentTimeMillis());
    }
}
