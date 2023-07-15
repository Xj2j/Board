package ru.xj2j.board.userteamservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.xj2j.board.userteamservice.service.UserLastProjectWithWorkspaceService;

import java.util.Map;

@RestController
@RequestMapping("/users/last-visited-workspace")
public class UserLastProjectWithWorkspaceController {
    private final UserLastProjectWithWorkspaceService service;

    public UserLastProjectWithWorkspaceController(UserLastProjectWithWorkspaceService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getLastProjectWithWorkspace() {
            //User user =  get the user from the request or authentication context

            Map<String, Object> response = service.getLastProjectWithWorkspace(user);
            return ResponseEntity.ok(response);
    }
}
