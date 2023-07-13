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
import ru.xj2j.board.userteamservice.service.InviteWorkspaceService;
import ru.xj2j.board.userteamservice.service.WorkspaceMemberService;
import ru.xj2j.board.userteamservice.service.WorkspaceService;

import java.util.List;

@RestController
@RequestMapping("/workspaces")
public class InviteWorkspaceController {

    private WorkspaceService workspaceService;

    private WorkspaceMemberService workspaceMemberService;

    private InviteWorkspaceService inviteWorkspaceService;

    @Autowired
    public InviteWorkspaceController(WorkspaceService workspaceService, WorkspaceMemberService workspaceMemberService, InviteWorkspaceService inviteWorkspaceService) {
        this.workspaceService = workspaceService;
        this.workspaceMemberService = workspaceMemberService;
        this.inviteWorkspaceService = inviteWorkspaceService;
    }

    @PostMapping("/{workspaceId}/invite")
    public ResponseEntity<Object> inviteWorkspaceMembers(@PathVariable Long workspaceId,
                                                         @Valid @RequestBody List<InviteRequest> inviteRequests,
                                                         Authentication authentication) throws WorkspaceNotFoundException {
        return inviteWorkspaceService.inviteWorkspaceMembers(workspaceId, inviteRequests, authentication);
    }

    @PostMapping("/{workspaceId}/invite/{id}")
    public ResponseEntity<?> joinWorkspace(
            @PathVariable("workspaceId") Long workspaceId,
            @PathVariable("id") Long id,
            @RequestParam("email") String email,
            @RequestBody JoinWorkspaceRequest request) {
        try {
            inviteWorkspaceService.handleJoinWorkspaceRequest(workspaceId, id, email, request);
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
        List<WorkspaceMemberInviteDTO> invitations = inviteWorkspaceService.getInvitationsByWorkspaceId(workspaceId);
        return ResponseEntity.ok(invitations);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInvitation(@PathVariable Long workspaceId, @PathVariable Long id) {
        try {
            inviteWorkspaceService.deleteInvitation(workspaceId, id);
            return ResponseEntity.noContent().build();
        } catch (InviteWorkspaceNotFoundException e) {
            // Handle other exceptions
            return ResponseEntity.badRequest().body("Something went wrong, please try again later");
        }
    }

    @GetMapping
    public List<WorkspaceMemberInvite> getInvitations(@AuthenticationPrincipal User user) {
        return inviteWorkspaceService.getInvitationsByEmail(user.getEmail());
    }

    @PostMapping("/accept")
    public ResponseEntity<String> acceptInvitations(@AuthenticationPrincipal User user,
                                                    @RequestBody List<Long> invitationIds) {
        try {
            inviteWorkspaceService.acceptInvitations(user, invitationIds);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Something went wrong, please try again later");
        }
    }

}