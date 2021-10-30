package com.nnk.springboot.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id", unique = true, nullable = false)
    private Integer id;
    @NotBlank(message = "Username is mandatory")
    @Column(name = "username")
    private String username;
    @NotBlank(message = "Password is mandatory")
    @Column(name = "password")
    private String password;
    @NotBlank(message = "FullName is mandatory")
    @Column(name = "fullname")
    private String fullname;
    @NotBlank(message = "Role is mandatory")
    @Column(name = "role")
    private String role;

}
