package ru.xj2j.board.userteamservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.xj2j.board.userteamservice.entity.User;
import ru.xj2j.board.userteamservice.entity.WorkspaceMember;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkspaceMemberRepository extends JpaRepository<WorkspaceMember, Long> {

    List<WorkspaceMember> findByWorkspaceId(Long workspaceId);

    Optional<WorkspaceMember> findByWorkspaceIdAndId(Long workspaceId, Long id);

    Optional<WorkspaceMember> findByWorkspaceIdAndMember(Long workspaceId, User member);

    Optional<WorkspaceMember> findByWorkspaceIdAndMemberEmail(Long workspaceId, String email);

    List<WorkspaceMember> findByWorkspaceIdAndMemberEmailIn(Long workspaceId, List<String> emails);

    List<WorkspaceMember> findByWorkspaceIdAndMemberIsBotFalse(Long workspaceId);

}
