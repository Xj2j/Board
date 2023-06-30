package ru.xj2j.board.userteamservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.NOT_FOUND)
public class WorkspaceNotFoundException extends Exception {
    public WorkspaceNotFoundException() {
        super("Workspace not found");
    }
}
