package com.finartix.cleanarchitecture.demo.user.usecases.ports.out;

import java.util.Optional;

import com.finartix.cleanarchitecture.demo.user.adapters.out.gateways.ds.entities.UserEntity;
import com.finartix.cleanarchitecture.demo.user.usecases.dtos.CreateUserDsDto;
import com.finartix.cleanarchitecture.demo.user.usecases.dtos.UserDsDto;

public interface IUserDsGateway {
    boolean existsByName(String id);

    Optional<UserEntity> getById(String id);

    UserDsDto getByName(String name);

    void save(CreateUserDsDto requestModel);
}
