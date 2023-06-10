package com.example.demo.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_tracking")
public class UserTracking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "access_time")
    private LocalDateTime accessTime;

    @Column(name = "page_url")  // Thêm thuộc tính pageUrl
    private String pageUrl;

    public UserTracking(String username, String ipAddress, LocalDateTime accessTime, String pageUrl) {
        this.username = username;
        this.ipAddress = ipAddress;
        this.accessTime = accessTime;
        this.pageUrl = pageUrl;
    }
    // getters, setters, constructors
}
