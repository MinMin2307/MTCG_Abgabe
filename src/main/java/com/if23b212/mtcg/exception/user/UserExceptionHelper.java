package com.if23b212.mtcg.exception.user;

public class UserExceptionHelper {
    public static final String ALREADY_REGISTERED_EXCEPTION_MESSAGE = "User with same username already registered";
    public static final String USER_NOT_FOUND_EXCEPTION_MESSAGE = "User not found.";
    public static final String INVALID_CREDENTIALS_EXCEPTION_MESSAGE = "Invalid username/password provided";

    public static class UserException extends RuntimeException {
        private static final long serialVersionUID = -2474520897134752943L;

		public UserException(String message) {
            super(message);
        }
    }

    public static class AlreadyRegisteredException extends UserExceptionHelper.UserException {
        private static final long serialVersionUID = 7944127786162057078L;

		public AlreadyRegisteredException() {
            super(ALREADY_REGISTERED_EXCEPTION_MESSAGE);
        }
    }

    public static class UserNotFoundException extends UserExceptionHelper.UserException {
        private static final long serialVersionUID = -4420357290995677470L;

		public UserNotFoundException() {
            super(USER_NOT_FOUND_EXCEPTION_MESSAGE);
        }
    }

    public static class InvalidCredentialsException extends UserExceptionHelper.UserException {
        private static final long serialVersionUID = -8463357159938156020L;

		public InvalidCredentialsException() {
            super(INVALID_CREDENTIALS_EXCEPTION_MESSAGE);
        }
    }
}
