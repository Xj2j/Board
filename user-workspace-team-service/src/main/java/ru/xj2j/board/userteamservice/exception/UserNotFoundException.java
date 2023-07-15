package ru.xj2j.board.userteamservice.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException() {
    }
}
