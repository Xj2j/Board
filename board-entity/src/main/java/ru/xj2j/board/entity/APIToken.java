package ru.xj2j.board.entity;

import java.util.UUID;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "api_tokens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class APIToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private String label;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Integer userType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="workspace_id")
    private Workspace workspace;

    public static String generateToken() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }

    public static String generateLabelToken() {
        return UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    }
}