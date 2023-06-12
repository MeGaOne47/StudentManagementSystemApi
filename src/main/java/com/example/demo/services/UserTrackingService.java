package com.example.demo.services;

import com.example.demo.entity.UserTracking;
import com.example.demo.repository.IUserTrackingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserTrackingService {

    private final IUserTrackingRepository userTrackingRepository;

    @Autowired
    public UserTrackingService(IUserTrackingRepository userTrackingRepository) {
        this.userTrackingRepository = userTrackingRepository;
    }

    public void trackUserAccessed(String username, String ipAddress, String pageUrl) {
        // Tạo đối tượng UserTracking từ thông tin người dùng
        UserTracking userTracking = new UserTracking(username, ipAddress, LocalDateTime.now(), pageUrl);
        // Lưu đối tượng UserTracking vào cơ sở dữ liệu
        userTrackingRepository.save(userTracking);
    }


    public void trackUserLogin(String username, String ipAddress, String pageUrl) {
        LocalDateTime accessTime = LocalDateTime.now();
        // Tạo đối tượng UserTracking từ thông tin người dùng
        UserTracking userTracking = new UserTracking(username, ipAddress, accessTime, pageUrl);
        // Lưu đối tượng UserTracking vào cơ sở dữ liệu
        userTrackingRepository.save(userTracking);
    }
}

