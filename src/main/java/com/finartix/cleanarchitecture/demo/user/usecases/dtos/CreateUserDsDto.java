package com.finartix.cleanarchitecture.demo.user.usecases.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class CreateUserDsDto {

    private String name;
    private String password;
}
