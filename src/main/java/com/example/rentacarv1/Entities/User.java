package com.example.rentacarv1.Entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "surname",nullable = false)
    private String surname;
    @Column(name = "gsm",nullable = false)
    private String gsm;
    @Column(name = "email",nullable = false)
    private String email;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Customer> customers;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Employee> employees;
}