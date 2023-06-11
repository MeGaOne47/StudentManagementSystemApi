package com.example.demo.controller;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.services.UserService;
import com.example.demo.services.UserTrackingService;
import jakarta.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserTrackingService userTrackingService;

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "user/register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "user/register";
        }

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userService.save(user);
        return "redirect:/login";
    }


    @GetMapping("/usersTracking")
    public String getUser(Authentication authentication, Model model, HttpServletRequest request) {
        String username = authentication.getName();
        List<String> roles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        LocalDateTime currentTime = LocalDateTime.now();

        String ipAddress = request.getRemoteAddr(); // Lấy địa chỉ IP của người dùng
        userTrackingService.trackUserLogin(username, ipAddress, "/users"); // Theo dõi người dùng khi truy cập trang users

        model.addAttribute("username", username);
        model.addAttribute("roles", roles);
        model.addAttribute("currentTime", currentTime);
        model.addAttribute("ipAddress", ipAddress); // Truyền giá trị của địa chỉ IP vào trong model

        return "user/usersTracking";
    }

    @GetMapping("/users")
    public String getUsersPage(Model model) {
        List<User> users = userService.getAllUsers();
        List<Role> roles = userService.getAllRoles();
        model.addAttribute("users", users);
        model.addAttribute("roles", roles);
        return "user/listUser";
    }


//    @GetMapping("/users/{userId}/edit")
//    public String editUser(@PathVariable Long userId, Model model) {
//        User user = userService.findUserById(userId);
//        List<Role> allRoles = userService.getAllRoles();
//
//        model.addAttribute("user", user);
//        model.addAttribute("allRoles", allRoles);
//
//        return "user/editUser";
//    }

//    @PostMapping("/users/{userId}/update")
//    public String updateUser(
//            @PathVariable Long userId,
//            @ModelAttribute("user") User updatedUser,
//            BindingResult bindingResult,
//            Model model) {
//
//        if (bindingResult.hasErrors()) {
//            // Xử lý lỗi nếu có
//            return "user/editUser";
//        }
//
//        // Tìm người dùng cần sửa thông tin
//        User existingUser = userService.findUserById(userId);
//        if (existingUser == null) {
//            // Xử lý trường hợp không tìm thấy người dùng
//            return "error";
//        }
//
//        // Cập nhật thông tin người dùng
//        existingUser.setName(updatedUser.getName());
//        existingUser.setEmail(updatedUser.getEmail());
//
//        // Cập nhật quyền của người dùng
//        Set<Role> updatedRoles = updatedUser.getRoles();
//        existingUser.setRoles(updatedRoles);
//
//        // Lưu lại các thay đổi vào cơ sở dữ liệu
//        userService.save(existingUser);
//
//        return "redirect:/users";
//    }

}
