package ru.xj2j.projectboard.model;

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
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //name
    private String name;

    //описание
    private String description;

    @ManyToOne
    private Task parent;

    //уровень доступа

    //участники
    @ManyToMany(cascade = {
            CascadeType.PERSIST, CascadeType.MERGE
    })
    @JoinTable(name = "task_user",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> members = new HashSet<>();

    //ответственный
    @ManyToOne(fetch = FetchType.LAZY)
    private User curator;

    //поручитель
    @ManyToOne(fetch = FetchType.LAZY)
    private User reporter;

    //Подписанные
    @OneToMany
    private Set<User> watchers = new HashSet<>();

    //статус
    @ManyToOne(fetch = FetchType.LAZY)
    private Status status;

    //приоритет
    @ManyToOne(fetch = FetchType.LAZY)
    private Priority priority;

    //проекты
    @ManyToMany(cascade = {
            CascadeType.PERSIST, CascadeType.MERGE
    })
    @JoinTable(name = "task_project",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    private Set<User> projects = new HashSet<>();

    //подзадачи
    @OneToMany(mappedBy="parent")
    private Set<Task> subtasks;

    //чек-лист(этапы)

    //Дата создания
    private LocalDateTime createdDate;

    //Дата начала выполнения
    private LocalDateTime startDate;

    //Дата обновление задачи
    private LocalDateTime updateDate;

    //Срок
    private LocalDateTime dueDate;

    //дата фактического окончания
    private LocalDateTime endDate;

    //метки

    //вложения(изображения, документы, …)

    //компонент

    //эпик??

    //Комментарии

    //Связанные задачи(тип связи)

}
