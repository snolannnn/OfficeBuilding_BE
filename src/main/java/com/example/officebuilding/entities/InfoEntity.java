package com.example.officebuilding.entities;

import com.example.officebuilding.security.entities.User;
import lombok.Data;

import javax.persistence.*;


@Entity
@Data
@Table(name = "info")
public class InfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String infoName;

    @Column(nullable = false)
    private String infoPhone;

    @Column(nullable = false)
    private String infoGmail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
