package ru.xj2j.board.userteamservice.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import jakarta.persistence.*;
import ru.xj2j.board.userteamservice.JsonConverter;

import java.util.HashMap;
import java.util.Map;

@Data
@Entity
@Table(name = "workspace_themes")
@NoArgsConstructor
//@EqualsAndHashCode(callSuper = true) // наследуем equals() и hashCode() от BaseModel
@ToString(callSuper = true) // наследуем toString() от BaseModel
public class WorkspaceTheme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id", nullable = false)
    @JsonIgnoreProperties("themes")
    private Workspace workspace;

    @Column(name = "name", length = 300, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_id", nullable = false)
    @JsonIgnoreProperties("themes")
    private User actor;

    @Column(name = "colors", columnDefinition = "jsonb", nullable = false)
    @Convert(converter = JsonConverter.class)
    private Map<String, String> colors = new HashMap<>();

    @JsonCreator
    public WorkspaceTheme(@JsonProperty("workspace") Workspace workspace,
                          @JsonProperty("name") String name,
                          @JsonProperty("actor") User actor,
                          @JsonProperty("colors") Map<String, String> colors) {
        this.workspace = workspace;
        this.name = name;
        this.actor = actor;
        this.colors = colors;
    }

    /**@PrePersist
    void createdAt() {
        this.createdAt = LocalDate.now();
    }

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;*/
}
