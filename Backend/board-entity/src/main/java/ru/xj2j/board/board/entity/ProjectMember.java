package ru.xj2j.board.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "project_members")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ProjectMember extends ProjectBaseModel {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private User member;

    @Column(name = "comment", columnDefinition = "text", nullable = true)
    private String comment;

    @Column(name = "role")
    private Integer role;

    @Column(name = "view_props", columnDefinition = "jsonb")
    private JsonNode viewProps;

    @Column(name = "default_props", columnDefinition = "jsonb")
    private JsonNode defaultProps;


    public String toString() {
        return String.format("%s <%s>", this.getMember().getEmail(), this.getProject().getName());
    }
}