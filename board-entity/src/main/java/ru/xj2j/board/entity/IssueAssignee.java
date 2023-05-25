package ru.xj2j.board.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "issue_assignees")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueAssignee extends ProjectBaseModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_id", nullable = false)
    private Issue issue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignee_id", nullable = false)
    private User assignee;

    @Override
    public String toString() {
        return issue.getName() + " " + assignee.getEmail();
    }
}