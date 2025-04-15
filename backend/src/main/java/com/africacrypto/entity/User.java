package com.africacrypto.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder  // ✅ THIS is required to enable User.builder()
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String phone;
    private String username;
    private String role;

    private boolean enabled;       // ✅ Must be declared here
    private boolean kycVerified;
}
