package ru.xj2j.board.board.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Project extends AuditModel {

    public enum Network {
        SECRET, PUBLIC
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "description_text", columnDefinition = "json")
    private String descriptionText;

    @Column(name = "description_html", columnDefinition = "json")
    private String descriptionHtml;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "network")
    private Network network;

    @ManyToOne
    @JoinColumn(name = "workspace_id", foreignKey = @ForeignKey(name = "fk_project_workspace"))
    private Workspace workspace;

    @Column(name = "identifier", length = 5)
    private String identifier;

    @OneToOne
    @JoinColumn(name = "default_assignee_id", foreignKey = @ForeignKey(name = "fk_project_default_assignee"))
    private User defaultAssignee;

    @OneToOne
    @JoinColumn(name = "project_lead_id", foreignKey = @ForeignKey(name = "fk_project_project_lead"))
    private User projectLead;

    @Column(name = "icon", length = 255)
    private String icon;

    @Column(name = "module_view")
    private boolean moduleView;

    @Column(name = "cycle_view")
    private boolean cycleView;

    @Column(name = "issue_views_view")
    private boolean issueViewsView;

    @Column(name = "page_view")
    private boolean pageView;

    @Column(name = "cover_image", length = 800)
    private String coverImage;

    @ManyToOne
    @JoinColumn(name = "estimate_id", foreignKey = @ForeignKey(name = "fk_project_estimate"))
    private Estimate estimate;

    public enum Role {
        ADMIN, MEMBER, VIEWER, GUEST
    }

    public static Map<String, Object> getDefaultProps() {
        Map<String, Object> props = new HashMap<>();
        props.put("filters", Collections.singletonMap("type", null));
        props.put("orderBy", "-created_at");
        props.put("collapsed", true);
        props.put("issueView", "list");
        props.put("filterIssue", null);
        props.put("groupByProperty", null);
        props.put("showEmptyGroups", true);
        return props;
    }

    public String toString() {
        return String.format("%s <%s>", this.getName(), this.getWorkspace().getName());
    }

}