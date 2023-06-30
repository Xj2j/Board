package ru.xj2j.board.userteamservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.CONFLICT)
public class WorkspaceAlreadyExistsException extends Exception {
    public WorkspaceAlreadyExistsException() {
        super("The workspace with the slug already exists");
    }
}
