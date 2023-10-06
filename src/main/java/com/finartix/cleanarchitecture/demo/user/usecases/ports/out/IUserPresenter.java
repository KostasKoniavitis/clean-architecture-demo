package com.finartix.cleanarchitecture.demo.user.usecases.ports.out;

import com.finartix.cleanarchitecture.demo.user.usecases.dtos.CreateUserResponseDto;

public interface IUserPresenter {
    CreateUserResponseDto createdSuccessResponse(CreateUserResponseDto createUserResponseDto);

    CreateUserResponseDto alreadyExistsResponse();

    CreateUserResponseDto cannotBeAdminResponse();
}
