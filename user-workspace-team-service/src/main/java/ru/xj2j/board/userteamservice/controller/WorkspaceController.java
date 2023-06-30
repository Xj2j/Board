package ru.xj2j.board.userteamservice.controller;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import ru.xj2j.board.userteamservice.DTO.WorkspaceDTO;
import ru.xj2j.board.userteamservice.entity.Workspace;
import ru.xj2j.board.userteamservice.exception.WorkspaceAlreadyExistsException;
import ru.xj2j.board.userteamservice.exception.WorkspaceNotFoundException;
import ru.xj2j.board.userteamservice.service.WorkspaceService;

@RestController
@RequestMapping("/api/workspaces")
public class WorkspaceController {

    @Autowired
    private WorkspaceService workspaceService;

    @GetMapping("/{workspaceId}")
    public ResponseEntity<?> getWorkspace(@PathVariable Long workspaceId) {
        try {
            WorkspaceDTO workSpace = workspaceService.getWorkspaceById(workspaceId);
            return ResponseEntity.ok(workSpace);
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /*@PostMapping("")
    public ResponseEntity<?> createWorkspace(@RequestBody WorkspaceDTO request, Authentication authentication) {
        try {
            WorkspaceDTO newWorkSpace = workspaceService.create(request, authentication.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(newWorkSpace);
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }*/

    @PostMapping("")
    public ResponseEntity<?> createWorkspace(@Valid @RequestBody WorkspaceDTO workspaceDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String ownerUsername = authentication.getName();

        try {
            Workspace createdWorkspace = workspaceService.create(workspaceDTO, ownerUsername);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdWorkspace);
        } catch (WorkspaceAlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("The workspace with the slug already exists");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong. Please try again later.");
        }
    }

    @PatchMapping("/{workspaceId}")
    public ResponseEntity<?> updateWorkSpace(@PathVariable("workspaceId") Long workspaceId, @Valid @RequestBody WorkspaceDTO workspaceDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
        }

        try {
            Workspace updatedWorkspace = workspaceService.updateWorkspace(workspaceId, workspaceDTO);
            return ResponseEntity.ok(updatedWorkspace);
        } catch (WorkspaceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Workspace not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong. Please try again later.");
        }
    }

    @DeleteMapping("/{workspaceId}")
    public ResponseEntity<?> deleteWorkspace(@PathVariable("workspaceId") Long workspaceId) {
        try {
            workspaceService.deleteWorkspace(workspaceId);
            return ResponseEntity.noContent().build();
        } catch (WorkspaceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Workspace not found");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong. Please try again later.");
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getUserWorkspaces(Authentication authentication) {
        try {
            List<WorkspaceDTO> workSpaces = workspaceService.getUserWorkspaces(authentication.getName());
            return ResponseEntity.ok(workSpaces);
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/availability/{workspaceId}")
    public ResponseEntity<?> checkWorkspaceAvailability(@PathVariable Long workspaceId) {
        try {
            boolean isAvailable = workspaceService.isWorkspaceAvailable(workspaceId);
            return ResponseEntity.ok(isAvailable);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong. Please try again later.");
        }
    }
}
