package com.hackathon.markdownnotetakingapp.service.render;

import com.hackathon.markdownnotetakingapp.service.storage.StorageService;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class RenderService {
    private final Parser markdownParser;
    private final HtmlRenderer htmlRenderer;
    private final StorageService storageService;

    @Autowired
    public RenderService(Parser markdownParser, HtmlRenderer htmlRenderer, StorageService storageService) {
        this.markdownParser = markdownParser;
        this.htmlRenderer = htmlRenderer;
        this.storageService = storageService;
    }

    public ResponseEntity<String> renderNote(@PathVariable String fileName) {
        try {
            // 1. Load the markdown content from the file
            String markdownContent = storageService.loadFileAsString(fileName);

            // 2. Parse markdown to a Node and then render it to an HTML fragment
            Node document = markdownParser.parse(markdownContent);
            String noteHtmlContent = htmlRenderer.render(document);

            // 3. Load the HTML template from the classpath
            ClassPathResource resource = new ClassPathResource("static/note_template.html");
            String template = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

            // 4. Inject the parsed content into the template
            String finalHtml = template.replace("{{content}}", noteHtmlContent);

            // 5. Return the final HTML page
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_HTML)
                    .body(finalHtml);

        } catch (FileNotFoundException e) {
            return ResponseEntity.status(404).body("Note not found.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error rendering note.");
        }
    }
}
