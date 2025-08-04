package com.hackathon.markdownnotetakingapp.controller;

import com.hackathon.markdownnotetakingapp.service.grammar.GrammarCheckResponse;
import com.hackathon.markdownnotetakingapp.service.grammar.GrammarCheckService;
import com.hackathon.markdownnotetakingapp.service.render.RenderService;
import com.hackathon.markdownnotetakingapp.service.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class NoteUploadController {

    private final RenderService renderService;
    private final StorageService storageService;
    private final GrammarCheckService grammarCheckService;


    @Autowired
    public NoteUploadController( GrammarCheckService grammarCheckService, StorageService storageService, RenderService renderService) {
        this.storageService = storageService;
        this.grammarCheckService = grammarCheckService;
        this.renderService = renderService;
    }

    @GetMapping("/file/{fileName}")
    public ResponseEntity<String> renderNote(@PathVariable String fileName) {
        return renderService.renderNote(fileName);
    }
    @PostMapping("/upload")
    public ResponseEntity<String> uploadNote(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        return storageService.save(file);
    }

    @PostMapping("/check")
    public ResponseEntity<String> checkNote(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        if(file.isEmpty()) {return ResponseEntity.noContent().build();}

        try {
            GrammarCheckResponse grammarCheckResponse = grammarCheckService.checkGrammar(file);
            // no grammatical errors
            if(grammarCheckResponse.isSuccess()) {
                return ResponseEntity.ok().build();
            }
            // grammatical error present
            else return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(grammarCheckResponse.getResponse().toString());

        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }
}
