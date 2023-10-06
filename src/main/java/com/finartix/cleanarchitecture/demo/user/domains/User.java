package com.finartix.cleanarchitecture.demo.user.domains;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class User implements IUser {

    private String name;
    private String password;

    @Override
    public boolean passwordIsValid() {
        return password != null && password.length() > 5;
    }
}
