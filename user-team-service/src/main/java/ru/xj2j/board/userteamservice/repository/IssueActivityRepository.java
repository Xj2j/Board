package ru.xj2j.board.userteamservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface IssueActivityRepository extends JpaRepository<IssueActivity, Long> {

    @Query("SELECT new com.example.demo.dto.ActivityCount(i.createdDate, COUNT(i)) "
            + "FROM IssueActivity i "
            + "WHERE i.actor.username = :username "
            + "AND i.workspace.slug = :slug "
            + "AND i.createdAt >= :date "
            + "GROUP BY i.createdDate "
            + "ORDER BY i.createdDate ASC")
    List<ActivityCount> getUserIssueActivities(@Param("slug") String slug, @Param("username") String username,
                                               @Param("date") LocalDate date);
}
