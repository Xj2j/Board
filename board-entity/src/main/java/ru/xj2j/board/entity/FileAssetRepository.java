package ru.xj2j.board.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileAssetRepository extends JpaRepository<FileAsset, Long> {
}