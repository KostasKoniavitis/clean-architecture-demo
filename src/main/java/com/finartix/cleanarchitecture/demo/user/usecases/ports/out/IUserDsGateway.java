package com.finartix.cleanarchitecture.demo.user.usecases.ports.out;

import java.util.Optional;

import com.finartix.cleanarchitecture.demo.user.adapters.gateways.ds.entities.UserEntity;
import com.finartix.cleanarchitecture.demo.user.usecases.dtos.CreateUserDsDto;

public interface IUserDsGateway {
    boolean existsByName(String id);

    Optional<UserEntity> getById(String id);

    UserEntity getByName(String name);

    void save(CreateUserDsDto requestModel);
}
