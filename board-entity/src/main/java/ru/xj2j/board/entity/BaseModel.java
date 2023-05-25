package ru.xj2j.board.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "base_model")
public class BaseModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    public BaseModel() {
        this.id = UUID.randomUUID();
    }

    // Add additional fields and methods as needed

}