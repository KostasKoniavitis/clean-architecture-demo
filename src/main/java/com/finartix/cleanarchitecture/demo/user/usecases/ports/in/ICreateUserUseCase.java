package com.finartix.cleanarchitecture.demo.user.usecases.ports.in;

import com.finartix.cleanarchitecture.demo.user.usecases.dtos.CreateUserRequestDto;
import com.finartix.cleanarchitecture.demo.user.usecases.dtos.CreateUserResponseDto;

public interface ICreateUserUseCase {
    CreateUserResponseDto create(CreateUserRequestDto requestModel);
}
