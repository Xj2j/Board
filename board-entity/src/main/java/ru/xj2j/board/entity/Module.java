package ru.xj2j.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "modules")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Module extends ProjectBaseModel {
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "description_text")
    private String descriptionText;

    @Column(name = "description_html")
    private String descriptionHtml;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "target_date")
    private LocalDate targetDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20)
    private Status status = Status.PLANNED;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lead_id")
    private User lead;

    @JsonIgnoreProperties("modules")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "module_members",
            joinColumns = {@JoinColumn(name = "module_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<User> members = new HashSet<>();

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ModuleIssue> issueModule = new HashSet<>();

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ModuleLink> linkModule = new HashSet<>();

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("module")
    private Set<ModuleFavorite> moduleFavorites = new HashSet<>();

    public enum Status {
        BACKLOG,
        PLANNED,
        IN_PROGRESS,
        PAUSED,
        COMPLETED,
        CANCELLED
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", this.name, this.startDate, this.targetDate);
    }
}