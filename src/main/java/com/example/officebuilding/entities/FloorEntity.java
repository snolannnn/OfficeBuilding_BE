package com.example.officebuilding.entities;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "floor")
public class FloorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String fName;

    private Timestamp fTime;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        fTime = new Timestamp(System.currentTimeMillis());
    }
}
