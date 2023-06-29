package ru.xj2j.board.entity;

import jakarta.persistence.Transient;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class FileAssetDTO {

    private UUID id;
    private String attributes;

    @NotNull(message = "file cannot be null")
    @Transient
    private MultipartFile file;

    public FileAssetDTO(String attributes, MultipartFile file) {
        this.attributes = attributes;
        this.file = file;
    }

    public boolean hasFile() {
        return this.file != null && !this.file.isEmpty();
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getAssetUrl() {
        if (id == null) {
            return "user-" + UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
        }
        return id.toString() + "-" + file.getOriginalFilename();
    }

    public byte[] getFileBytes() throws IOException {
        return file.getBytes();
    }
}
