package com.finartix.cleanarchitecture.demo.user.adapters.gateways.ds.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.finartix.cleanarchitecture.demo.user.adapters.gateways.ds.entities.UserEntity;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByName(String name);
}
