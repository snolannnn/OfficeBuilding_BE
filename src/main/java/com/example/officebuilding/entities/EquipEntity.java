package com.example.officebuilding.entities;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "equipment")
public class EquipEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String eName;

    @Column(nullable = false)
    private Integer eStatus;

    private String eDesc;
    private Timestamp eTime;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        eTime = new Timestamp(System.currentTimeMillis());
    }

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "floor_id", referencedColumnName= "id",nullable = true)
    private FloorEntity floor;

}
