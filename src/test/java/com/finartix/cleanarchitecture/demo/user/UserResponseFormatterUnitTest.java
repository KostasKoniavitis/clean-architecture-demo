package com.finartix.cleanarchitecture.demo.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.springframework.web.server.ResponseStatusException;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import com.finartix.cleanarchitecture.demo.user.adapters.gateways.ds.UserDsGateway;
import com.finartix.cleanarchitecture.demo.user.adapters.presenters.CreateUserPresenter;
import com.finartix.cleanarchitecture.demo.user.domains.IUser;
import com.finartix.cleanarchitecture.demo.user.domains.User;
import com.finartix.cleanarchitecture.demo.user.usecases.UserInteractor;
import com.finartix.cleanarchitecture.demo.user.usecases.dtos.CreateUserRequestDto;
import com.finartix.cleanarchitecture.demo.user.usecases.dtos.CreateUserResponseDto;
import com.finartix.cleanarchitecture.demo.user.usecases.ports.in.ICreateUserUseCase;
import com.finartix.cleanarchitecture.demo.user.usecases.ports.out.IUserPresenter;

class UserResponseFormatterUnitTest {

    CreateUserPresenter createUserPresenter = new CreateUserPresenter();
    UserDsGateway userDsGateway = mock(UserDsGateway.class);
    IUserPresenter userPresenter = mock(IUserPresenter.class);
    ICreateUserUseCase createUserUseCase = new UserInteractor(userDsGateway, createUserPresenter);
    ArgumentCaptor<String> userRequestModelArgumentCaptor = ArgumentCaptor.forClass(String.class);

    @Test
    void givenDateAnd3HourTime_whenPrepareSuccessView_thenReturnOnly3HourTime() {
        CreateUserResponseDto modelResponse =
                new CreateUserResponseDto("baeldung", "2020-12-20T03:00:00.000");
        CreateUserResponseDto formattedResponse =
                createUserPresenter.createdSuccessResponse(modelResponse);

        assertThat(formattedResponse.getCreationTime()).isEqualTo("03:00:00");
    }

    @Test
    void whenPrepareFailView_thenThrowHttpConflictException() {
        assertThatThrownBy(() -> createUserPresenter.cannotBeAdminResponse("Invalid password"))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void whenCreateUser_thenSuccess() {

        CreateUserRequestDto createUserRequestDto = new CreateUserRequestDto("baeldung", "123456");

        IUser newUser = User.builder().name(anyString()).password(anyString()).build();

        when(newUser).thenReturn(User.builder().name("baeldung").password("123456").build());

        createUserUseCase.create(createUserRequestDto);

        verify(userDsGateway).existsByName(userRequestModelArgumentCaptor.capture());
        String name = createUserRequestDto.getName();
        assertEquals("baeldung", name);
    }
}
