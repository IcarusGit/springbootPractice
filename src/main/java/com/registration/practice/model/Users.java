package com.registration.practice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "First name is required")
    private String firstName;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "Last name is required")
    private String lastName;

    @Column(nullable = false, unique = true, length = 30)
    @NotBlank(message = "Username is required")
    private String username;

    @Column(nullable = false, unique = true, length = 50)
    @Email(message = "Invalid email format")
    private String email;

    @Column(nullable = false, length = 15)
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Contact number must be 10–15 digits")
    private String contactNumber;

    @Column(nullable = false, length = 60) // length 60 for hashing
    @Pattern( regexp = "^(?=.*[0-9])(?=.*[!@#$%^&*])(?=.*[A-Za-z]).{6,}$", message = "Password must be at least 6 characters and include a letter, number, and special character")
    private String password;

    @Column(nullable = false, length = 20)
    private String role = "USER"; // default role
}
