package com.finartix.cleanarchitecture.demo.user.domains;

public interface IUser {

    String getName();

    String getPassword();

    boolean passwordIsValid();
}
