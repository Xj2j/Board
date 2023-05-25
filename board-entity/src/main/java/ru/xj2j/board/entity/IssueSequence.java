package ru.xj2j.board.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "issue_sequences")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueSequence extends ProjectBaseModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_id", nullable = true, referencedColumnName = "id")
    private Issue issue;

    @Column(name = "sequence")
    private Long sequence;

    @Column(name = "deleted")
    private boolean deleted;

    @Override
    public String toString() {
        return issue != null ? issue.getName() : "" + " - " + sequence;
    }
}
