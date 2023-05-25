package ru.xj2j.board.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

// TODO: length
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"},
        allowGetters = true)
@Entity
@Table(name = "file_assets")
public class FileAsset extends BaseModel {

    @Column(columnDefinition = "json")
    private String attributes;

    @NotBlank
    //@Length(max = 255)
    private String assetUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workspace_id")
    private Workspace workspace;

    public FileAsset() {}

    public FileAsset(String attributes, String assetUrl) {
        this.attributes = attributes;
        this.assetUrl = assetUrl;
    }

    @Override
    public String toString() {
        return assetUrl;
    }
}