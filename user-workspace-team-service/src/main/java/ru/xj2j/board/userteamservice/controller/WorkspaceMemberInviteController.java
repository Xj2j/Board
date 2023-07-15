package ru.xj2j.board.userteamservice.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.xj2j.board.userteamservice.DTO.*;
import ru.xj2j.board.userteamservice.entity.User;
import ru.xj2j.board.userteamservice.entity.WorkspaceMemberInvite;
import ru.xj2j.board.userteamservice.exception.*;
import ru.xj2j.board.userteamservice.service.WorkspaceMemberInviteService;
import ru.xj2j.board.userteamservice.service.WorkspaceMemberService;
import ru.xj2j.board.userteamservice.service.WorkspaceService;

import java.util.List;

@RestController
@RequestMapping("/workspaces")
public class WorkspaceMemberInviteController {

    private WorkspaceService workspaceService;

    private WorkspaceMemberService workspaceMemberService;

    private WorkspaceMemberInviteService workspaceMemberInviteService;

    @Autowired
    public WorkspaceMemberInviteController(WorkspaceService workspaceService, WorkspaceMemberService workspaceMemberService, WorkspaceMemberInviteService workspaceMemberInviteService) {
        this.workspaceService = workspaceService;
        this.workspaceMemberService = workspaceMemberService;
        this.workspaceMemberInviteService = workspaceMemberInviteService;
    }

    @GetMapping("/{workspaceId}/{id}")
    public WorkspaceMemberInvite getWorkspaceMemberInvite(@PathVariable("workspaceId") Long workspaceId, @PathVariable("id") Long id) {
        // Получаем приглашение по ID и возвращаем его в ответе
        return workspaceMemberInviteService.findById(id);
    }

    @PostMapping("/{workspaceId}/invite")
    public ResponseEntity<Object> inviteWorkspaceMembers(@PathVariable Long workspaceId,
                                                         @Valid @RequestBody List<InviteRequest> inviteRequests,
                                                         Authentication authentication) throws WorkspaceNotFoundException {
        return workspaceMemberInviteService.inviteWorkspaceMembers(workspaceId, inviteRequests, authentication);
    }

    @PostMapping("/{workspaceId}/invite/{id}")
    public ResponseEntity<?> joinWorkspace(
            @PathVariable("workspaceId") Long workspaceId,
            @PathVariable("id") Long id,
            @RequestParam("email") String email,
            @RequestBody JoinWorkspaceRequest request) {
        try {
            workspaceMemberInviteService.handleJoinWorkspaceRequest(workspaceId, id, email, request);
            return ResponseEntity.ok(new SuccessResponse("Workspace Invitation Accepted"));
        } catch (WorkspaceInviteException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ConcreteErrorResponse(e.getMessage()));
        } catch (WorkspaceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ConcreteErrorResponse(e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ConcreteErrorResponse("Something went wrong, please try again later"));
        }
    }

    @GetMapping
    public ResponseEntity<List<WorkspaceMemberInviteDTO>> getInvitations(@PathVariable Long workspaceId) {
        List<WorkspaceMemberInviteDTO> invitations = workspaceMemberInviteService.getInvitationsByWorkspaceId(workspaceId);
        return ResponseEntity.ok(invitations);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInvitation(@PathVariable Long workspaceId, @PathVariable Long id) {
        try {
            workspaceMemberInviteService.deleteInvitation(workspaceId, id);
            return ResponseEntity.noContent().build();
        } catch (InviteWorkspaceNotFoundException e) {
            // Handle other exceptions
            return ResponseEntity.badRequest().body("Something went wrong, please try again later");
        }
    }

    @GetMapping
    public List<WorkspaceMemberInvite> getInvitations(@AuthenticationPrincipal User user) {
        return workspaceMemberInviteService.getInvitationsByEmail(user.getEmail());
    }

    @PostMapping("/accept")
    public ResponseEntity<String> acceptInvitations(@AuthenticationPrincipal User user,
                                                    @RequestBody List<Long> invitationIds) {
        try {
            workspaceMemberInviteService.acceptInvitations(user, invitationIds);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Something went wrong, please try again later");
        }
    }

}