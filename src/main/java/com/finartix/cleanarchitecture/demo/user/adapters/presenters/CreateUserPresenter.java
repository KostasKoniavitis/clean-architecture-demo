package com.finartix.cleanarchitecture.demo.user.adapters.presenters;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.finartix.cleanarchitecture.demo.user.usecases.dtos.CreateUserResponseDto;
import com.finartix.cleanarchitecture.demo.user.usecases.exceptions.UserAlreadyExistsException;
import com.finartix.cleanarchitecture.demo.user.usecases.exceptions.UserCannotBeAdminException;
import com.finartix.cleanarchitecture.demo.user.usecases.ports.out.IUserPresenter;

public class CreateUserPresenter implements IUserPresenter {

    @Override
    public CreateUserResponseDto createdSuccessResponse(CreateUserResponseDto response) {
        LocalDateTime responseTime = LocalDateTime.parse(response.getCreationTime());
        response.setCreationTime(responseTime.format(DateTimeFormatter.ofPattern("hh:mm:ss")));
        return response;
    }

    @Override
    public CreateUserResponseDto alreadyExistsResponse() {
        throw new UserAlreadyExistsException();
    }

    @Override
    public CreateUserResponseDto cannotBeAdminResponse() {
        throw new UserCannotBeAdminException();
    }
}
