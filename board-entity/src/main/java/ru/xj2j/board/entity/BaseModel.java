package ru.xj2j.board.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

//@Entity
//@Table(name = "base_model")
@MappedSuperclass
@Data
public abstract class BaseModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    public BaseModel() {
        this.id = UUID.randomUUID();
    }

}