package ru.xj2j.board.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "issue_labels")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueLabel extends ProjectBaseModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_id", nullable = false, referencedColumnName = "id")
    private Issue issue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "label_id", nullable = false, referencedColumnName = "id")
    private Label label;

    @Override
    public String toString() {
        return issue.getName() + " " + label.getName();
    }
}
