package ru.xj2j.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@EntityListeners(AuditingEntityListener.class)
@Entity(name = "Issue")
@Table(name = "issues")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Issue {
    public enum Priority {
        URGENT("Urgent"),
        HIGH("High"),
        MEDIUM("Medium"),
        LOW("Low");

        private final String label;

        Priority(String label) {
            this.label = label;
        }

        @Override
        public String toString() {
            return label;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "id", nullable = false)
    private Project project;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private Issue parent;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id", referencedColumnName = "id")
    private State state;

    @NotNull(message = "Estimate point is required.")
    @Min(value = 0, message = "Estimate point cannot be less than 0.")
    @Column(name = "estimate_point")
    private Integer estimatePoint;

    @NotBlank(message = "Name is required.")
    @Column(name = "name")
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "description", columnDefinition = "json", nullable = false)
    private String description;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "description_html", columnDefinition = "text", nullable = false)
    private String descriptionHtml;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "description_stripped", columnDefinition = "text")
    private String descriptionStripped;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private Priority priority;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "start_date")
    private LocalDateTime startDate;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "target_date")
    private LocalDateTime targetDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "issue_assignees",
            joinColumns = {@JoinColumn(name = "issue_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private List<User> assignees;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IssueBlocker> blockerIssues;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IssueLink> links;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "issue", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IssueAttachment> attachments;

    @JsonIgnore
    @Column(name = "sequence_id")
    private Integer sequenceId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "sort_order")
    private Double sortOrder;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "completed_by_id", referencedColumnName = "id")
    private User completedBy;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Column(name = "completed_at")
    private LocalDateTime completedAt;

}
