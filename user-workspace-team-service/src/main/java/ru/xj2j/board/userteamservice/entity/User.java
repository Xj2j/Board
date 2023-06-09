package ru.xj2j.board.userteamservice.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;


//@EntityListeners(AuditingEntityListener.class)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "sur_name")
    private String surname;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "date_joined")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateJoined;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(name = "last_location")
    private String lastLocation;

    @Column(name = "created_location")
    private String createdLocation;

    @Column(name = "is_superuser")
    private Boolean isSuperuser;

    @Column(name = "is_managed")
    private Boolean isManaged;

    @Column(name = "is_password_expired")
    private Boolean isPasswordExpired;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_staff")
    private Boolean isStaff;

    @Column(name = "is_email_verified")
    private Boolean isEmailVerified;

    @Column(name = "is_password_autoset")
    private Boolean isPasswordAutoset;

    @Column(name = "is_onboarded")
    private Boolean isOnboarded;

    @Column(name = "token")
    private String token;

    @Column(name = "billing_address_country")
    private String billingAddressCountry;

    @Column(name = "billing_address", columnDefinition = "jsonb")
    private String billingAddress;

    @Column(name = "has_billing_address")
    private Boolean hasBillingAddress;

    @Column(name = "user_timezone")
    private String userTimezone;

    @Column(name = "last_active")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastActive;

    @Column(name = "last_login_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoginTime;

    @Column(name = "last_logout_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLogoutTime;

    @Column(name = "last_login_ip")
    private String lastLoginIp;

    @Column(name = "last_logout_ip")
    private String lastLogoutIp;

    @Column(name = "last_login_medium")
    private String lastLoginMedium;

    @Column(name = "last_login_uagent")
    private String lastLoginUagent;

    @Column(name = "token_updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tokenUpdatedAt;

    @Column(name = "last_workspace_id")
    private Long lastWorkspaceId;

    @Column(name = "my_issues_prop", columnDefinition = "jsonb")
    private String myIssuesProp;

    @Column(name = "role")
    private String role;

    @Column(name = "is_bot")
    private Boolean isBot;

    @Column(name = "theme", columnDefinition = "jsonb")
    private String theme;

    @PrePersist
    public void prePersist() {
        if (StringUtils.hasText(email)) {
            email = email.toLowerCase().trim();
        }

        if (StringUtils.hasText((CharSequence) tokenUpdatedAt)) {
            token = UUID.randomUUID().toString().replaceAll("-", "");
            tokenUpdatedAt = new Date();
        }

        if (isSuperuser) {
            isStaff = true;
        }
    }

}
