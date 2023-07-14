package ru.xj2j.board.userteamservice.exception;

import ru.xj2j.board.userteamservice.DTO.UserDTO;

import java.util.Set;

public class TeamAlreadyExistsException extends RuntimeException {
    private Set<UserDTO> userDtoSet;


    public TeamAlreadyExistsException(String message, Set<UserDTO> userDtoSet) {
        super(message);
        this.userDtoSet = userDtoSet;
    }

    public TeamAlreadyExistsException(String message) {
        super(message);
    }

    public Set<UserDTO> getUserLiteDtoList() {
        return userDtoSet;
    }
}
