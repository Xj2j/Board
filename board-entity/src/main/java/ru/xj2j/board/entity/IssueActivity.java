package ru.xj2j.board.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "issue_activities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueActivity extends ProjectBaseModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_id")
    private Issue issue;

    @Column(name = "verb", length = 255)
    private String verb = "created";

    @Column(name = "field", length = 255)
    private String field;

    @Column(name = "old_value", columnDefinition="TEXT")
    private String oldValue;

    @Column(name = "new_value", columnDefinition="TEXT")
    private String newValue;

    @Column(name = "comment", columnDefinition="TEXT")
    private String comment;

    @ElementCollection
    private List<String> attachments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_comment_id")
    private IssueComment issueComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_id")
    private User actor;

    @Column(name = "old_identifier")
    private UUID oldIdentifier;

    @Column(name = "new_identifier")
    private UUID newIdentifier;

    @Override
    public String toString() {
        return issue.toString();
    }
}