package com.finartix.cleanarchitecture.demo.user.domains;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements IUser {

    private String name;
    private String password;

    @Override
    public boolean passwordIsValid() {
        return password != null && password.length() > 5;
    }
}
