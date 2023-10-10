package com.finartix.cleanarchitecture.demo.user.adapters.in.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.finartix.cleanarchitecture.demo.user.usecases.dtos.CreateUserRequestDto;
import com.finartix.cleanarchitecture.demo.user.usecases.dtos.CreateUserResponseDto;
import com.finartix.cleanarchitecture.demo.user.usecases.ports.in.ICreateUserUseCase;

@RestController
class UserController {

    private final ICreateUserUseCase createUserUseCase;

    UserController(ICreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    @PostMapping("/user")
    CreateUserResponseDto create(@RequestBody CreateUserRequestDto createUserRequestDto) {
        return createUserUseCase.create(createUserRequestDto);
    }
}
