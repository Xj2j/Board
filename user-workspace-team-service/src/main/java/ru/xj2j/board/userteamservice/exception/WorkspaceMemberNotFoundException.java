package ru.xj2j.board.userteamservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//@SuppressWarnings("serial")
//@ResponseStatus(HttpStatus.NOT_FOUND)
public class WorkspaceMemberNotFoundException extends RuntimeException  {
    public WorkspaceMemberNotFoundException(String s) {
        super(s);
    }
}
