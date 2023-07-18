package ru.xj2j.board.userteamservice.controller;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.xj2j.board.userteamservice.entity.User;
import ru.xj2j.board.userteamservice.entity.WorkspaceTheme;
import ru.xj2j.board.userteamservice.exception.WorkspaceNotFoundException;
import ru.xj2j.board.userteamservice.service.WorkspaceThemeService;

import java.util.List;

@RestController
@RequestMapping("/workspaces/{id}/themes")
public class WorkspaceThemeController {

    private WorkspaceThemeService workspaceThemeService;

    @GetMapping
    public List<WorkspaceTheme> getAllWorkspaceThemesBySlug(@PathVariable Long id) throws WorkspaceNotFoundException {
        return workspaceThemeService.getAllWorkspaceThemesById(id);
    }

    @PostMapping
    public ResponseEntity<?> createWorkspaceTheme(@PathVariable Long id,
                                                  @RequestBody WorkspaceTheme workspaceTheme,
                                                  Authentication authentication) throws WorkspaceNotFoundException {
        User actor = (User) authentication.getPrincipal();
        WorkspaceTheme createdWorkspaceTheme = workspaceThemeService.createWorkspaceTheme(id, workspaceTheme, actor);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdWorkspaceTheme);
    }

}
