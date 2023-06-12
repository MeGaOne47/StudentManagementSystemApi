package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "role")
public class Role{
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Size(max = 50, message = "Name must be less than 50 characters")
        @NotBlank(message = "Name is required")
        @Column(name = "name", length = 50, nullable = false)
        private String name;

        @Size (max = 250, message = "Description must be less than 250 characters")
        @Column(name = "description", length = 250)
        private String description;

        @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
        private Set<User> users = new HashSet<>();


}
