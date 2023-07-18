package ru.xj2j.board.userteamservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface IssueActivityRepository extends JpaRepository<IssueActivity, Long> {
    @Query("SELECT NEW com.example.IssueActivityGraphData(i.createdDate, COUNT(i)) " +
            "FROM IssueActivity i " +
            "WHERE i.actorId = :actorId " +
            "AND i.workspace.workspaceId = :id " +
            "AND i.createdAt >= :startDate " +
            "GROUP BY i.createdDate " +
            "ORDER BY i.createdDate")
    List<IssueActivityGraphData> getUserActivityGraph(
            @Param("slug") String workspaceId,
            @Param("actorId") Long actorId,
            @Param("startDate") LocalDate startDate);
}
