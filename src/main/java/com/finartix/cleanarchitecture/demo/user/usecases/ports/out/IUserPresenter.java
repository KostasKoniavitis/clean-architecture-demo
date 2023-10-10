package com.finartix.cleanarchitecture.demo.user.usecases.ports.out;

import com.finartix.cleanarchitecture.demo.user.usecases.dtos.CreateUserResponseDto;
import com.finartix.cleanarchitecture.demo.user.usecases.exceptions.UserAlreadyExistsException;
import com.finartix.cleanarchitecture.demo.user.usecases.exceptions.UserCannotBeAdminException;

public interface IUserPresenter {
    CreateUserResponseDto successResponse(CreateUserResponseDto createUserResponseDto);

    CreateUserResponseDto alreadyExistsResponse() throws UserAlreadyExistsException;

    CreateUserResponseDto cannotBeAdminResponse() throws UserCannotBeAdminException;
}
