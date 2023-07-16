package com.example.officebuilding.entities;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "customer")
public class CustomerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String cName;

    @Column(nullable = false)
    private String cPhone;

    @Column(nullable = false)
    private String cEmail;

    @Column(nullable = false)
    private String cTaxCode;

    private Timestamp cTime;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        cTime = new Timestamp(System.currentTimeMillis());
    }
}
