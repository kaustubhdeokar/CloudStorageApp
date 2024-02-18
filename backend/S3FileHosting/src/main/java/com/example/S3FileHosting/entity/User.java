package com.example.S3FileHosting.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    //cascade - user parent child roles,
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "userid")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "roleid")})
    private List<Role> roles = new ArrayList<>();

    public User(String username, String email, String name, String password, List<Role> roles) {
        this.username = username;
        this.email = email;
        this.name = name;
        this.password = password;

        this.roles = roles;
    }
}
