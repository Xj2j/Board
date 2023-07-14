package ru.xj2j.board.userteamservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.xj2j.board.userteamservice.entity.Team;
import ru.xj2j.board.userteamservice.entity.TeamMember;
import ru.xj2j.board.userteamservice.entity.Workspace;

import java.util.List;
import java.util.Set;

@Repository
public interface TeamMemberRepository extends JpaRepository<Team, Long> {

    Set<TeamMember> getTeamMembersByWorkspaceId(Long workspaceId);
    Set<Team> findByWorkspaceId(Long id);
    Set<Long> findMemberIdsByWorkspace(Workspace workspace);
    boolean existsByWorkspaceAndTeam(Workspace workspace, Team team);
}

