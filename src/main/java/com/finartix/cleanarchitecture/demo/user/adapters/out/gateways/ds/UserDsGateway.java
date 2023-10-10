package com.finartix.cleanarchitecture.demo.user.adapters.out.gateways.ds;

import java.util.Optional;

import com.finartix.cleanarchitecture.demo.shared.exceptions.ResourceNotFoundException;
import com.finartix.cleanarchitecture.demo.user.adapters.out.gateways.ds.entities.UserEntity;
import com.finartix.cleanarchitecture.demo.user.adapters.out.gateways.ds.repositories.IUserRepository;
import com.finartix.cleanarchitecture.demo.user.usecases.dtos.CreateUserDsDto;
import com.finartix.cleanarchitecture.demo.user.usecases.dtos.UserDsDto;
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
    public UserDsDto getByName(String name) {
        UserEntity userEntity =
                userRepository.findByName(name).orElseThrow(ResourceNotFoundException::new);

        return UserDsDto.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .password(userEntity.getPassword())
                .createdDate(userEntity.getCreatedDate())
                .createdBy(userEntity.getCreatedBy())
                .lastModifiedDate(userEntity.getLastModifiedDate())
                .lastModifiedBy(userEntity.getLastModifiedBy())
                .build();
    }

    @Override
    public void save(CreateUserDsDto requestModel) {
        UserEntity accountDataMapper =
                UserEntity.builder()
                        .name(requestModel.getName())
                        .password(requestModel.getPassword())
                        .build();

        userRepository.save(accountDataMapper);
    }
}
