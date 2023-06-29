package ru.xj2j.board.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
public class FileAssetService {

    private final FileAssetRepository fileAssetRepository;

    @Autowired
    public FileAssetService(FileAssetRepository fileAssetRepository) {
        this.fileAssetRepository = fileAssetRepository;
    }

    public FileAsset save(FileAssetDTO fileAssetDTO) throws IOException {
        String attributes = fileAssetDTO.getAttributes();

        byte[] bytes = fileAssetDTO.getFileBytes();
        String assetUrl = fileAssetDTO.getAssetUrl();

        FileAsset fileAsset = new FileAsset(attributes, assetUrl);
        fileAsset.setCreatedAt(LocalDateTime.now());
        fileAsset.setUpdatedAt(LocalDateTime.now());

        Files.write(Paths.get(assetUrl), bytes);

        return fileAssetRepository.save(fileAsset);
    }
}
