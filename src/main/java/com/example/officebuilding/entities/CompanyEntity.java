package com.example.officebuilding.entities;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "company")
public class CompanyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String cusName;

    @Column(nullable = false)
    private String cusPhone;

    @Column(nullable = false)
    private String cusEmail;

    @Column(nullable = false)
    private String cusTaxCode;

    @Column(nullable = false)
    private Integer cusStatus;

    private Timestamp cTime;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        cTime = new Timestamp(System.currentTimeMillis());
    }
}
