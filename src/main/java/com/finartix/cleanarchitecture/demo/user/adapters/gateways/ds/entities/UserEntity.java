package com.finartix.cleanarchitecture.demo.user.adapters.gateways.ds.entities;

import jakarta.persistence.*;

import lombok.EqualsAndHashCode;

import com.finartix.cleanarchitecture.demo.shared.models.Auditable;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
public class UserEntity extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;

    public UserEntity() {}

    public UserEntity(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
