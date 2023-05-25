package ru.xj2j.board.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "CycleIssue")
@Table(name = "cycle_issues")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CycleIssue extends ProjectBaseModel {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_id", referencedColumnName = "id")
    private Issue issue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cycle_id", referencedColumnName = "id")
    private Cycle cycle;

    @Override
    public String toString() {
        return String.format("%s", this.getCycle());
    }
}
