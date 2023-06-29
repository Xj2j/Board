package ru.xj2j.board.userteamservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/workspace/dashboard/{slug}")
public class UserWorkspaceDashboardEndpoint {

    @Autowired
    private IssueActivityRepository issueActivityRepository;

    @Autowired
    private IssueRepository issueRepository;

    @GetMapping("")
    public ResponseEntity<?> getUserWorkspaceDashboard(
            @PathVariable String slug,
            @RequestParam(value = "month", defaultValue = "1") int month
    ) {
        try {
            // Get issue activities for last 3 months
            List<ActivityCount> issueActivities = issueActivityRepository.getUserIssueActivities(
                    slug, SecurityContextHolder.getContext().getAuthentication().getName());

            // Get completed issues for a specific month
            List<CompletedCount> completedIssues = issueRepository.getCompletedIssuesByMonth(
                    slug, SecurityContextHolder.getContext().getAuthentication().getName(), month);

            // Get assigned issues count
            Long assignedIssuesCount = issueRepository.countAssignedIssues(slug, SecurityContextHolder.getContext().getAuthentication().getName());

            // Get pending issues count
            Long pendingIssuesCount = issueRepository.countPendingIssues(slug, SecurityContextHolder.getContext().getAuthentication().getName());

            // Get completed issues count
            Long completedIssuesCount = issueRepository.countCompletedIssues(slug, SecurityContextHolder.getContext().getAuthentication().getName());

            // Get issues due in the current week
            Long issuesDueWeekCount = issueRepository.countIssuesDueInCurrentWeek(slug, SecurityContextHolder.getContext().getAuthentication().getName());

            // Get state distribution of all issues assigned to the user
            List<StateCount> stateDistribution = issueRepository.getStateDistribution(
                    slug, SecurityContextHolder.getContext().getAuthentication().getName());

            // Get overdue issues which are not completed or cancelled
            List<Issue> overdueIssues = issueRepository.getOverdueIssues(
                    slug, SecurityContextHolder.getContext().getAuthentication().getName());

            // Get upcoming issues which are not completed or cancelled
            List<Issue> upcomingIssues = issueRepository.getUpcomingIssues(
                    slug, SecurityContextHolder.getContext().getAuthentication().getName());

            // Return user workspace dashboard data
            UserWorkspaceDashboardDto dto = new UserWorkspaceDashboardDto();
            dto.setIssueActivities(issueActivities);
            dto.setCompletedIssues(completedIssues);
            dto.setAssignedIssuesCount(assignedIssuesCount);
            dto.setPendingIssuesCount(pendingIssuesCount);
            dto.setCompletedIssuesCount(completedIssuesCount);
            dto.setIssuesDueWeekCount(issuesDueWeekCount);
            dto.setStateDistribution(stateDistribution);
            dto.setOverdueIssues(overdueIssues);
            dto.setUpcomingIssues(upcomingIssues);

            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Something went wrong please try again later"));
        }
    }
