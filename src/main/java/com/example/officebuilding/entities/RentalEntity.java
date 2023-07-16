package com.example.officebuilding.entities;

import com.example.officebuilding.security.entities.Role;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

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
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @ManyToMany(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinTable(name = "rental_service",
            joinColumns = {@JoinColumn(name = "rental_id")},
            inverseJoinColumns = {@JoinColumn(name = "service_id")})
    private List<ServiceEntity> services;
}
