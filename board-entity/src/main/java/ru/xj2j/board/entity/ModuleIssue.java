package ru.xj2j.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "module_issues")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ModuleIssue extends ProjectBaseModel {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id")
    private Module module;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "issue_id")
    private Issue issue;

    @Override
    public String toString() {
        return String.format("%s %s", this.module.getName(), this.issue.getName());
    }
}
