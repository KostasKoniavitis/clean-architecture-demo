package com.finartix.cleanarchitecture.demo.user.adapters.out.gateways.ds.entities;

import jakarta.persistence.*;

import lombok.*;

import com.finartix.cleanarchitecture.demo.shared.models.Auditable;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class UserEntity extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password", nullable = false)
    private String password;
}
