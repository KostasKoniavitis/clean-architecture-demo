package com.finartix.cleanarchitecture.demo.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import com.finartix.cleanarchitecture.demo.user.adapters.out.gateways.ds.UserDsGateway;
import com.finartix.cleanarchitecture.demo.user.domains.IUser;
import com.finartix.cleanarchitecture.demo.user.domains.User;
import com.finartix.cleanarchitecture.demo.user.usecases.UserInteractor;
import com.finartix.cleanarchitecture.demo.user.usecases.dtos.CreateUserDsDto;
import com.finartix.cleanarchitecture.demo.user.usecases.dtos.CreateUserRequestDto;
import com.finartix.cleanarchitecture.demo.user.usecases.dtos.CreateUserResponseDto;
import com.finartix.cleanarchitecture.demo.user.usecases.ports.in.ICreateUserUseCase;
import com.finartix.cleanarchitecture.demo.user.usecases.ports.out.IUserPresenter;

class UserUnitTest {

    UserDsGateway userDsGateway = mock(UserDsGateway.class);
    IUserPresenter userPresenter = mock(IUserPresenter.class);
    ICreateUserUseCase interactor = new UserInteractor(userDsGateway, userPresenter);

    @Test
    void given123Password_whenPasswordIsNotValid_thenIsFalse() {
        IUser user = new User("Baeldung", "123");

        assertThat(user.passwordIsValid()).isFalse();
    }

    @Test
    void givenBaeldungUserAnd123456Password_whenCreate_thenSaveItAndPrepareSuccessView() {

        IUser user = new User("baeldung", "123456");
        CreateUserRequestDto createUserRequestDto =
                new CreateUserRequestDto(user.getName(), user.getPassword());
        IUser newUser = User.builder().name(anyString()).password(anyString()).build();

        when(newUser)
                .thenReturn(
                        User.builder().name(user.getName()).password(user.getPassword()).build());

        interactor.create(createUserRequestDto);

        verify(userDsGateway, times(1)).save(any(CreateUserDsDto.class));
        verify(userPresenter, times(1)).successResponse(any(CreateUserResponseDto.class));
    }
}
