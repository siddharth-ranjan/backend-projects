package com.hackathon.markdownnotetakingapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class NoteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originalFileName;
    private String savedFileName;
    private String filePath;
    private Long fileSizeInBytes;
    private LocalDateTime uploadedDateTime;

    @Override
    public String toString() {
        return "NoteModel{" +
                "id=" + id +
                ", originalFileName='" + originalFileName + '\'' +
                ", savedFileName='" + savedFileName + '\'' +
                ", filePath='" + filePath + '\'' +
                ", fileSizeInBytes=" + fileSizeInBytes +
                ", uploadedDateTime=" + uploadedDateTime +
                '}';
    }

    public Long getId() {
        return id;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getSavedFileName() {
        return savedFileName;
    }

    public void setSavedFileName(String savedFileName) {
        this.savedFileName = savedFileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getFileSizeInBytes() {
        return fileSizeInBytes;
    }

    public void setFileSizeInBytes(Long fileSizeInBytes) {
        this.fileSizeInBytes = fileSizeInBytes;
    }

    public LocalDateTime getUploadedDateTime() {
        return uploadedDateTime;
    }

    public void setUploadedDateTime(LocalDateTime uploadedDateTime) {
        this.uploadedDateTime = uploadedDateTime;
    }

    public NoteModel() {
    }

    public NoteModel(String originalFileName, String savedFileName, String filePath, Long fileSizeInBytes, LocalDateTime uploadedDateTime) {
        this.originalFileName = originalFileName;
        this.savedFileName = savedFileName;
        this.filePath = filePath;
        this.fileSizeInBytes = fileSizeInBytes;
        this.uploadedDateTime = uploadedDateTime;
    }
}
