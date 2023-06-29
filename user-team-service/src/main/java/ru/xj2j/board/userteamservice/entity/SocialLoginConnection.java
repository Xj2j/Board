package ru.xj2j.board.userteamservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "social_login_connections")
@NoArgsConstructor
@AllArgsConstructor
public class SocialLoginConnection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Enumerated(EnumType.STRING)
    //@Column(name = "medium")
    //private Medium medium;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @Column(name = "last_received_at")
    private LocalDateTime lastReceivedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //@Column(name = "token_data", columnDefinition = "jsonb")
    //private Text tokenData;

    //@Column(name = "extra_data", columnDefinition = "jsonb")
    //private JsonNode extraData;
}