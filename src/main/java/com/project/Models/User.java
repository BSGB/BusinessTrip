package com.project.Models;

import Validators.RegisterLoginConstraint;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private int id;

    @Column(name = "user_com_name")
    @NotNull@NotEmpty(message = "Pole nazwy firmy nie może być puste!")
    private String companyName;

    @Column(name = "user_com_nip")
    @NotNull@NotEmpty(message = "Pole NIP nie może być puste!")
    private String companyNip;

    @Column(name = "user_login")
    @RegisterLoginConstraint@NotNull@NotEmpty(message = "Pole loginu nie może być puste!")
    private String userLogin;

    @Column(name = "user_password")
    @NotNull@NotEmpty(message = "Pole hasła nie może być puste!")
    private String userPassword;

    @Column(name = "user_privilege")
    private String userRole;

    @Column(name = "account_non_locked")
    private boolean isAccountNonLocked;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    @OrderBy("report_id DESC")
    private Set<Report> reports = new HashSet<>();

    public User() {

    }

    public boolean index(String name, String password) {
        return this.userLogin.equals(name) && this.userPassword.equals(password);
    }
}
