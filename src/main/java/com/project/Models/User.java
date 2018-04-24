package com.project.Models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
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
    @NotEmpty(message = "Pole e-mail nie moze byc puste")
    private String companyName;

    @Column(name = "user_com_nip")
    @NotEmpty(message = "Pole NIP nie moze byc puste")
    private String companyNip;

    @Column(name = "user_login")
    @NotEmpty(message = "Pole loginu nie moze byc puste")
    private String userLogin;

    @Column(name = "user_password")
    @NotEmpty(message = "Pole hasla nie moze byc puste")
    private String userPassword;

    @Column(name = "user_privilege")
    private String userRole;
}
