package com.example.officebuilding.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "rental")
public class RentalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String reDateBegin;

    @Column(nullable = false)
    private String reDateEnd;

    @Column(nullable = false)
    private double rePrice;

    @Column(nullable = false)
    private Integer reStatus;

    private Timestamp reTime;

    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        reTime = new Timestamp(System.currentTimeMillis());
    }

    @OneToOne
    @JoinColumn(name = "room_id")
    private RoomEntity room;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private CompanyEntity company;

}
