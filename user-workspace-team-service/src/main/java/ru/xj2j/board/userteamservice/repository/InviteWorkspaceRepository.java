package ru.xj2j.board.userteamservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.xj2j.board.userteamservice.entity.Workspace;
import ru.xj2j.board.userteamservice.entity.WorkspaceMemberInvite;

import java.util.List;
import java.util.Optional;

@Repository
public interface InviteWorkspaceRepository extends JpaRepository<WorkspaceMemberInvite, Long> {

    Optional<WorkspaceMemberInvite> findByIdAndWorkspaceId(Long inviteId, Long workspaceId);

    List<WorkspaceMemberInvite> findByWorkspaceId(Long workspaceId);

    List<WorkspaceMemberInvite> findByEmail(String email);

    void deleteByWorkspace(Workspace workspace);


}