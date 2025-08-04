package com.hackathon.markdownnotetakingapp.service.storage;// In your StorageService implementation
import com.hackathon.markdownnotetakingapp.config.StorageProperties;
import com.hackathon.markdownnotetakingapp.model.NoteModel;
import com.hackathon.markdownnotetakingapp.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class FileSystemStorageService implements StorageService {

    private final NoteRepository noteRepository;
    private final Path rootLocation;

    @Autowired
    public FileSystemStorageService(NoteRepository noteRepository, StorageProperties storageProperties) {
        this.noteRepository = noteRepository;
        this.rootLocation = Paths.get(storageProperties.getLocation());
    }

    @Override
    public ResponseEntity<String> save(MultipartFile file) {
        // Your logic to save the file
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        String originalFileName = file.getOriginalFilename();
        String savedFileName = UUID.randomUUID() + "_" + originalFileName;
        String fullPath = rootLocation + "/" + savedFileName;

        try {
            // Save file to disk
            Path path = Paths.get(fullPath);
            Files.write(path, file.getBytes());

            // Create metadata (assuming a model class like StoredNoteMetadata)
            NoteModel metadata = new NoteModel(originalFileName, savedFileName, fullPath, file.getSize(), LocalDateTime.now());

            // TODO: Save metadata to DB if needed
            noteRepository.save(metadata);

            return ResponseEntity.ok().body("Saved successfully! URL: http://localhost:8080/file/" + savedFileName);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public String loadFileAsString(String filename) {
//         Your logic to load the file
        try {
            Path file = rootLocation.resolve(filename);
            if (!Files.exists(file) || !Files.isReadable(file)) {
                // Throw a specific exception for the controller to catch
                throw new FileNotFoundException("File not found: " + filename);
            }
            return Files.readString(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            // Wrap the original exception
            throw new RuntimeException("Could not read file: " + filename, e);
        }
    }
}