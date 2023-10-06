package com.finartix.cleanarchitecture.demo.user.usecases.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CreateUserRequestDto {

    @NotBlank private String name;

    @NotBlank
    @Size(min = 5)
    private String password;
}
