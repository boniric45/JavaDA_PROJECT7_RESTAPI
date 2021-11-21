package com.nnk.springboot.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "users")
@NoArgsConstructor
@Builder
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

    public User(Integer id, String username, String password, String fullname, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.role = role;
    }
}
