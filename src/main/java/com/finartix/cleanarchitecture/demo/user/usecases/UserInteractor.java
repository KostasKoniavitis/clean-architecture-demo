package com.finartix.cleanarchitecture.demo.user.usecases;

import java.time.Instant;

import com.finartix.cleanarchitecture.demo.user.domains.IUser;
import com.finartix.cleanarchitecture.demo.user.domains.User;
import com.finartix.cleanarchitecture.demo.user.usecases.dtos.CreateUserDsDto;
import com.finartix.cleanarchitecture.demo.user.usecases.dtos.CreateUserRequestDto;
import com.finartix.cleanarchitecture.demo.user.usecases.dtos.CreateUserResponseDto;
import com.finartix.cleanarchitecture.demo.user.usecases.ports.in.ICreateUserUseCase;
import com.finartix.cleanarchitecture.demo.user.usecases.ports.out.IUserDsGateway;
import com.finartix.cleanarchitecture.demo.user.usecases.ports.out.IUserPresenter;

public class UserInteractor implements ICreateUserUseCase {

    private final IUserDsGateway userDsGateway;
    private final IUserPresenter userPresenter;

    public UserInteractor(IUserDsGateway userDsGateway, IUserPresenter userPresenter) {
        this.userDsGateway = userDsGateway;
        this.userPresenter = userPresenter;
    }

    @Override
    public CreateUserResponseDto create(CreateUserRequestDto createUserRequestDto) {
        if (userDsGateway.existsByName(createUserRequestDto.getName())) {
            return userPresenter.alreadyExistsResponse();
        }

        if (createUserRequestDto.getName().equalsIgnoreCase("admin")) {
            return userPresenter.cannotBeAdminResponse();
        }

        IUser user =
                User.builder()
                        .name(createUserRequestDto.getName())
                        .password(createUserRequestDto.getPassword())
                        .build();

        CreateUserDsDto userDsDto =
                CreateUserDsDto.builder().name(user.getName()).password(user.getPassword()).build();

        userDsGateway.save(userDsDto);

        CreateUserResponseDto createUserResponseDto =
                CreateUserResponseDto.builder()
                        .login(user.getName())
                        .creationTime(Long.toString(Instant.now().toEpochMilli()))
                        .build();

        return userPresenter.createdSuccessResponse(createUserResponseDto);
    }
}
