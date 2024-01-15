package com.example.firstshop.exception;

import io.jsonwebtoken.JwtException;
import org.springframework.security.core.AuthenticationException;

public interface ListExceptions {
    class InvalidInputException extends IllegalArgumentException {
        private static final String defaultMessage = "Bad request";
        public InvalidInputException(String message) { super((message == null || message.isBlank()) ? defaultMessage : message);}
        public InvalidInputException() {
            super(defaultMessage);
        }
    }

    class UserNotFoundException extends RuntimeException{
        private static final String USER_NOT_FOUND_EXCEPTION = "Пользователь не найден!";
        public UserNotFoundException(String message) {
            super((message == null || message.isBlank()) ? USER_NOT_FOUND_EXCEPTION : message);
        }
        public UserNotFoundException() {
            super(USER_NOT_FOUND_EXCEPTION);
        }
    }

    class UserAuthenticationException extends AuthenticationException {
        private static final String WRONG_AUTHENTICATION_EXCEPTION = "Вы не аутентифицированы!";
        public UserAuthenticationException(String message) {
            super((message == null || message.isBlank()) ? WRONG_AUTHENTICATION_EXCEPTION : message);
        }
        public UserAuthenticationException() {
            super(WRONG_AUTHENTICATION_EXCEPTION);
        }
    }

    class UserIsBlocked extends AuthenticationException{
        private static final String USER_IS_ALREADY_BLOCKED = "Вы заблокированы!";
        public UserIsBlocked(String message) {
            super((message == null || message.isBlank()) ? USER_IS_ALREADY_BLOCKED : message);
        }
        public UserIsBlocked() {
            super(USER_IS_ALREADY_BLOCKED);
        }
    }

    class LoginAlreadyUsed extends RuntimeException{
        private static final String LOGIN_ALREADY_USED = "Данный логин занят!";
        public LoginAlreadyUsed(String message) {
            super((message == null || message.isBlank()) ? LOGIN_ALREADY_USED : message);
        }
        public LoginAlreadyUsed() {super(LOGIN_ALREADY_USED);}
    }

    class WrongPasswordException extends AuthenticationException{
        private static final String WRONG_PASSWORD = "Пароль неверный!";
        public WrongPasswordException(String message) {
            super((message == null || message.isBlank()) ? WRONG_PASSWORD : message);
        }
        public WrongPasswordException() {super(WRONG_PASSWORD);}
    }

    class NotFoundToken extends JwtException {
        private static final String JWT_TOKEN_NOT_FOUND = "Token not found!";
        public NotFoundToken(String message) {
            super((message == null || message.isBlank()) ? JWT_TOKEN_NOT_FOUND : message);
        }
        public NotFoundToken() {super(JWT_TOKEN_NOT_FOUND);}
    }

}
