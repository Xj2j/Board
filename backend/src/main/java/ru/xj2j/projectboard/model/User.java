package ru.xj2j.projectboard.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(	name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })
@Entity
public class User implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;

    private String lastname;

    private String surname;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private String phone;

    //private String login;

    @NotBlank
    @Size(max = 120)
    private String password;

    //@ManyToOne(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    private Role role;


    //должность
    @ManyToMany(cascade = {
            CascadeType.PERSIST, CascadeType.MERGE
    })
    @JoinTable(name = "user_position",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "position_id"))
    private Set<Position> positions = new HashSet<>();

    //команда
    @ManyToMany(mappedBy = "members")
    private Set<Team> teams = new HashSet<>();

    @ManyToMany(mappedBy = "members")
    private Set<Project> projects = new HashSet<>();

    @ManyToMany(mappedBy = "members")
    private Set<Task> tasks = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
