package ru.xj2j.projectboard.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
public abstract class DataStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //вхождение в
    @ManyToOne
    private DataStore parent;

    //содержание в себе
    @OneToMany(mappedBy="parent")
    private Set<DataStore> children;

    @ManyToOne(fetch = FetchType.LAZY)
    private Space space;

}
