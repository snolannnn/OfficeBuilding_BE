package com.example.officebuilding.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Table(name ="room_type")
@Entity
@NoArgsConstructor
public class RoomTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String rtName;
    private String rtDesc;
    private double rtPrice;
    private int rtBedNum;
    private String rtSize;
    private String rtImg;
    private Timestamp updateTime;
    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        updateTime = new Timestamp(System.currentTimeMillis());
    }


//    @OnDelete(action = OnDeleteAction.NO_ACTION)
//    @JoinColumn(name = "hotel_id",referencedColumnName = "id",nullable = false,foreignKey = @ForeignKey(name = "none"))
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", referencedColumnName = "id", nullable = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private HotelEntity hotel;
}
