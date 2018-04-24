package com.project.Models;

import lombok.Getter;
import lombok.Setter;


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
    @NotNull@NotEmpty(message = "Pole nazwy firmy nie moze byc puste")
    private String companyName;

    @Column(name = "user_com_nip")
    @NotNull@NotEmpty(message = "Pole NIP nie moze byc puste")
    private String companyNip;

    @Column(name = "user_login")
    @NotNull@NotEmpty(message = "Pole loginu nie moze byc puste")
    private String userLogin;

    @Column(name = "user_password")
    @NotNull@NotEmpty(message = "Pole hasla nie moze byc puste")
    private String userPassword;

    @Column(name = "user_privilege", insertable = false)
    private String userRole;
}
