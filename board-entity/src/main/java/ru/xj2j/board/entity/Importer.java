package ru.xj2j.board.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import jakarta.persistence.*;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

//TODO: toString
@Entity
@Data
@Table(name = "importers")
public class Importer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private ServiceName service;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private ImportStatus status = ImportStatus.QUEUED;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "initiated_by")
    private User initiatedBy;

    @JsonProperty("metadata")
    @Column(columnDefinition = "jsonb default '{}'::jsonb")
    private String metadataJson;

    @Transient
    private Map<String, Object> metadata;

    @JsonProperty("config")
    @Column(columnDefinition = "jsonb default '{}'::jsonb")
    private String configJson;

    @Transient
    private Map<String, Object> config;

    @JsonProperty("data")
    @Column(columnDefinition = "jsonb default '{}'::jsonb")
    private String dataJson;

    @Transient
    private Map<String, Object> data;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "token_id")
    private APIToken token;

    @Column(updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt = new Date();

    public enum ServiceName {
        GITHUB,
        JIRA
    }

    public enum ImportStatus {
        QUEUED,
        PROCESSING,
        COMPLETED,
        FAILED
    }

    //@Override
    //public String toString() {
       // return String.format("%s <%s>", this.service, this.project.getName());
    //}

    public void setMetadata(Map<String,Object> metadata) {
        this.metadata = metadata;
        try {
            this.metadataJson = new ObjectMapper().writeValueAsString(metadata);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to serialize metadata: " + e.getMessage(), e);
        }
    }

    @JsonProperty("metadata")
    public Map<String, Object> getMetadata() {
        if (metadata == null && metadataJson != null) {
            try {
                metadata = new ObjectMapper().readValue(metadataJson, new TypeReference<Map<String, Object>>() {});
            } catch (IOException e) {
                throw new RuntimeException("Unable to deserialize metadata: " + e.getMessage(), e);
            }
        }
        return metadata;
    }

    public void setConfig(Map<String,Object> metadata) {
        this.config = config;
        try {
            this.configJson = new ObjectMapper().writeValueAsString(config);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to serialize config: " + e.getMessage(), e);
        }
    }

    @JsonProperty("config")
    public Map<String, Object> getConfig() {
        if (config == null && configJson != null) {
            try {
                config = new ObjectMapper().readValue(configJson, new TypeReference<Map<String, Object>>() {});
            } catch (IOException e) {
                throw new RuntimeException("Unable to deserialize config: " + e.getMessage(), e);
            }
        }
        return config;
    }

    public void setData(Map<String,Object> data) {
        this.data = data;
        try {
            this.dataJson = new ObjectMapper().writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to serialize data: " + e.getMessage(), e);
        }
    }

    @JsonProperty("config")
    public Map<String, Object> getData() {
        if (data == null && dataJson != null) {
            try {
                data = new ObjectMapper().readValue(dataJson, new TypeReference<Map<String, Object>>() {});
            } catch (IOException e) {
                throw new RuntimeException("Unable to deserialize data: " + e.getMessage(), e);
            }
        }
        return data;
    }
}
