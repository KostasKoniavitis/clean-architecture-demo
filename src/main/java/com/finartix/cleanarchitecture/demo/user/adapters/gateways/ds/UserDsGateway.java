package com.finartix.cleanarchitecture.demo.user.adapters.gateways.ds;

import java.util.Optional;

import com.finartix.cleanarchitecture.demo.shared.exceptions.ResourceNotFoundException;
import com.finartix.cleanarchitecture.demo.user.adapters.gateways.ds.entities.UserEntity;
import com.finartix.cleanarchitecture.demo.user.adapters.gateways.ds.repositories.IUserRepository;
import com.finartix.cleanarchitecture.demo.user.usecases.dtos.CreateUserDsDto;
import com.finartix.cleanarchitecture.demo.user.usecases.ports.out.IUserDsGateway;

public class UserDsGateway implements IUserDsGateway {

    private final IUserRepository userRepository;

    UserDsGateway(IUserRepository repository) {
        this.userRepository = repository;
    }

    @Override
    public boolean existsByName(String name) {
        return userRepository.existsById(name);
    }

    @Override
    public Optional<UserEntity> getById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public UserEntity getByName(String name) {
        return userRepository.findByName(name).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void save(CreateUserDsDto requestModel) {
        UserEntity accountDataMapper =
                new UserEntity(requestModel.getName(), requestModel.getPassword());
        userRepository.save(accountDataMapper);
    }
}
