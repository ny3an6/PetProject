package com.nikita.simpleProject.exception;

public class UserExistsException extends RuntimeException{
    private static final String MESSAGE = "Такой пользователь уже существует";

    public UserExistsException() {
        super(MESSAGE);
    }
}
