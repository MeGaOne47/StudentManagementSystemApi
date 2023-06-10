// AdminController.java

package com.example.demo.controller;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.IRoleRepository;
import com.example.demo.repository.IUserRepository;
import jakarta.transaction.Transactional;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @GetMapping("/users")
    public String getUsers(Model model) {
        List<User> users = userRepository.findAll();

        model.addAttribute("users", users);

        // Lặp qua danh sách người dùng và lấy quyền hiện tại của từng người dùng
        Map<Long, Role> userCurrentRoles = new HashMap<>();
        for (User user : users) {
            if (!user.getRoles().isEmpty()) {
                Role currentRole = user.getRoles().iterator().next();
                userCurrentRoles.put(user.getId(), currentRole);
            }
        }
        model.addAttribute("userCurrentRoles", userCurrentRoles);

        model.addAttribute("roles", roleRepository.findAll());
        return "users";
    }

    @Transactional
    @PostMapping("/users/{userId}/role")
    public String updateUserRole(@PathVariable("userId") Long userId, @RequestParam("roleId") Long roleId) {
        User user = userRepository.findById(userId).orElse(null);
        Role role = roleRepository.findById(roleId).orElse(null);

        if (user != null && role != null) {
            // Kiểm tra xác thực người dùng hiện tại
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUsername = authentication.getName();
            if (!currentUsername.equals(user.getUsername())) {
                // Không cho phép người dùng thay đổi vai trò của người dùng khác
                return "redirect:/admin/users";
            }

            user.getRoles().clear();
            user.getRoles().add(role);
            userRepository.save(user);
        }

        return "redirect:/admin/users";
    }
}
