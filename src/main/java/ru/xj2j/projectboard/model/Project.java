package ru.xj2j.projectboard.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Project extends DataStore {

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //private Long id;

    //аватар

    //название
    private String name;

    //описание
    private String description;

    //статус
    @ManyToOne(fetch = FetchType.LAZY)
    private Status status;

    //Руководитель
    @ManyToOne(fetch = FetchType.LAZY)
    private User head;

    //Тип (Личный, Командный)
    @ManyToOne(fetch = FetchType.LAZY)
    private Type type;

    //Дата создания
    private LocalDateTime createdDate;

    //Дата начала выполнения
    private LocalDateTime startDate;

    //Срок
    private LocalDateTime dueDate;

    //дата фактического окончания
    private LocalDateTime endDate;

    //вид по умолчанию

    //Вид деятельности

    //Участники
    @ManyToMany(cascade = {
            CascadeType.PERSIST, CascadeType.MERGE
    })
    @JoinTable(name = "project_user",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<Project> members = new HashSet<>();

    //Наблюдатели

    //команды
    @ManyToMany(cascade = {
            CascadeType.PERSIST, CascadeType.MERGE
    })
    @JoinTable(name = "project_team",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id"))
    private Set<Team> teams = new HashSet<>();

    //задачи
    @ManyToMany(mappedBy = "projects", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<Task> tasks = new HashSet<>();

}
