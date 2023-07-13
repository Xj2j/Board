package ru.xj2j.board.userteamservice.service;

import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.xj2j.board.userteamservice.DTO.*;
import ru.xj2j.board.userteamservice.entity.User;
import ru.xj2j.board.userteamservice.entity.Workspace;
import ru.xj2j.board.userteamservice.entity.WorkspaceMember;
import ru.xj2j.board.userteamservice.entity.WorkspaceMemberInvite;
import ru.xj2j.board.userteamservice.exception.InviteWorkspaceNotFoundException;
import ru.xj2j.board.userteamservice.exception.WorkspaceInviteException;
import ru.xj2j.board.userteamservice.exception.WorkspaceNotFoundException;
import ru.xj2j.board.userteamservice.repository.UserRepository;
import ru.xj2j.board.userteamservice.repository.InviteWorkspaceRepository;
import ru.xj2j.board.userteamservice.repository.WorkspaceMemberRepository;
import ru.xj2j.board.userteamservice.util.WorkspaceMapper;


import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class InviteWorkspaceService {

    //private final KafkaTemplate<String, InviteRequest> kafkaTemplate;

    private final InviteWorkspaceRepository inviteWorkspaceRepository;

    private final UserRepository userRepository;

    private final WorkspaceMemberService workspaceMemberService;

    private final WorkspaceService workspaceService;

    private final WorkspaceMemberRepository workspaceMemberRepository;

    private final UserService userService;

    private final WorkspaceMapper workspaceMapper;

    @Autowired
    public InviteWorkspaceService(InviteWorkspaceRepository workspaceMemberInviteRepository, WorkspaceMemberService workspaceMemberService, WorkspaceService workspaceService, UserRepository userRepository, WorkspaceMemberRepository workspaceMemberRepository, UserService userService, WorkspaceMapper workspaceMapper) {
        this.inviteWorkspaceRepository = workspaceMemberInviteRepository;
        this.workspaceMemberService = workspaceMemberService;
        this.workspaceService = workspaceService;
        this.userRepository = userRepository;
        this.workspaceMemberRepository = workspaceMemberRepository;
        this.userService = userService;
        this.workspaceMapper = workspaceMapper;
    }

    @Transactional
    public ResponseEntity<Object> inviteWorkspaceMembers(Long workspaceId, List<InviteRequest> inviteRequests,
                                                         Authentication authentication) throws WorkspaceNotFoundException {
        // Check if email is provided
        if (inviteRequests.isEmpty()) {
            return ResponseEntity.badRequest().body("Emails are required");
        }

        // Check for role level
        User user = (User) authentication.getPrincipal();
        WorkspaceMember requestingUser = workspaceMemberService.getWorkspaceMemberByWorkspaceIdAndMemberEmail(workspaceId, user.getEmail());
        boolean hasHigherRole = inviteRequests.stream()
                .anyMatch(req -> req.getRole().compareTo(requestingUser.getRole()) > 0);
        if (hasHigherRole) {
            return ResponseEntity.badRequest().body("You cannot invite a user with a higher role");
        }

        WorkspaceDTO workspaceDto = workspaceService.getWorkspaceById(workspaceId);

        List<String> memberEmails = inviteRequests.stream()
                .map(InviteRequest::getEmail)
                .collect(Collectors.toList());
        List<WorkspaceMember> workspaceMembers = workspaceMemberService.getWorkspaceMembersByWorkspaceIdAndMemberEmails(workspaceDto.getId(), memberEmails);
        if (!workspaceMembers.isEmpty()) {
            List<WorkspaceMemberDTO> workspaceMemberDTOs = workspaceMembers.stream()
                    .map(workspaceMapper::toDto)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(Map.of("error", "Some users are already members of the workspace",
                    "workspace_users", workspaceMemberDTOs));
        }

        List<WorkspaceMemberInvite> workspaceInvitations = new ArrayList<>();
        for (InviteRequest inviteRequest : inviteRequests) {
            try {
                String emailString = inviteRequest.getEmail().trim().toLowerCase();
                String role = inviteRequest.getRole();
                //validateEmail(emailString);
                byte[] token = JWT.create()
                        .withClaim("email", emailString)
                        .withClaim("timestamp", Instant.now().getEpochSecond())
                        .sign(Algorithm.HMAC256("your-secret-key"));
                workspaceInvitations.add(new WorkspaceMemberInvite(emailString, workspaceMapper.toEntity(workspaceDto), new String(token, StandardCharsets.UTF_8), role));
            } catch (ValidationException e) {
                return ResponseEntity.badRequest().body("Invalid email address");
            }
        }

        inviteWorkspaceRepository.saveAll(workspaceInvitations);

        // отправка приглашения в брокер

        return ResponseEntity.ok().build();
    }

    @Transactional
    public void handleJoinWorkspaceRequest(Long workspaceId, Long id, String email, JoinWorkspaceRequest request) throws WorkspaceInviteException, WorkspaceNotFoundException {
        WorkspaceMemberInvite workspaceInvite = inviteWorkspaceRepository.findByIdAndWorkspaceId(id, workspaceId).orElse(null);
        if (workspaceInvite == null) {
            throw new WorkspaceNotFoundException("The invitation either got expired or could not be found");
        }

        if (!email.equals("") && !workspaceInvite.getEmail().equals(email)) {
            throw new WorkspaceInviteException("You do not have permission to join the workspace");
        }

        if (workspaceInvite.getRespondedAt() == null) {
            workspaceInvite.setAccepted(request.isAccepted());
            workspaceInvite.setRespondedAt(LocalDateTime.now());

            if (request.isAccepted()) {
                createWorkspaceMember(workspaceInvite.getWorkspace(), email, workspaceInvite.getRole());
            }

            inviteWorkspaceRepository.save(workspaceInvite);
        } else {
            throw new WorkspaceInviteException("You have already responded to the invitation request");
        }
    }

    private void createWorkspaceMember(Workspace workspace, String email, String role) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NoSuchElementException("Value not found"));

        if (user != null) {
            WorkspaceMember workspaceMember = new WorkspaceMember(workspace, user, role);
            workspaceMemberRepository.save(workspaceMember);

            user.setLastWorkspaceId(workspace.getId());
            userRepository.save(user);

            inviteWorkspaceRepository.deleteByWorkspace(workspace);
        }
    }

    public List<WorkspaceMemberInviteDTO> getInvitationsByWorkspaceId(Long id) {
        List<WorkspaceMemberInvite> invites = inviteWorkspaceRepository
                .findByWorkspaceId(id);

        return invites.stream()
                .map(workspaceMapper::toDto)
                .collect(Collectors.toList());
    }

    public void deleteInvitation(Long workspaceId, Long id) throws InviteWorkspaceNotFoundException {
        WorkspaceMemberInvite invite = inviteWorkspaceRepository
                .findByIdAndWorkspaceId(id, workspaceId)
                .orElseThrow(() -> new InviteWorkspaceNotFoundException("Invite will not find"));

        if (isSignupDisabled()) {
            userService.deleteUserIfExist(invite.getEmail());
        }

        inviteWorkspaceRepository.delete(invite);
    }

    private boolean isSignupDisabled() {
        return DockerizedSettings.isDockerized() && !EnableSignupSettings.isSignupEnabled();
    }

    public List<WorkspaceMemberInvite> getInvitationsByEmail(String email) {
        return inviteWorkspaceRepository.findByEmail(email);
    }

    public void acceptInvitations(User user, List<Long> invitationIds) {
        List<WorkspaceMemberInvite> workspaceInvitations = inviteWorkspaceRepository.findAllById(invitationIds);

        List<WorkspaceMember> workspaceMembers = workspaceInvitations.stream()
                .map(invitation -> new WorkspaceMember(
                        invitation.getWorkspace(),
                        user,
                        invitation.getRole()))
                .collect(Collectors.toList());

        workspaceMemberRepository.saveAll(workspaceMembers);
        inviteWorkspaceRepository.deleteAll(workspaceInvitations);
    }

    /*private void validateEmail(String email) throws ValidationException {
        // Проверка правильности формата электронной почты
        // Если электронная почта недопустима, выбросить ValidationException
    }*/
}
