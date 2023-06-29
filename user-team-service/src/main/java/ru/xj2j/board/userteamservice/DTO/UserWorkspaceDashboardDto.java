package ru.xj2j.board.userteamservice.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserWorkspaceDashboardDto {

    private List<ActivityCount> issueActivities;
    private List<CompletedCount> completedIssues;
    private Long assignedIssuesCount;
    private Long pendingIssuesCount;
    private Long completedIssuesCount;
    private Long issuesDueWeekCount;
    private List<StateCount> stateDistribution;
    private List<Issue> overdueIssues;
    private List<Issue> upcomingIssues;
}
