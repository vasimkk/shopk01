package com.shop.shop.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "register")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Register {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Column(unique = true, nullable = false)
    private String mobileNumber;

    private String password;

    @Enumerated(EnumType.STRING) // Stores role as "USER", "SELLER", "ADMIN"
    private Role role;  // ✅ use your custom enum
}
