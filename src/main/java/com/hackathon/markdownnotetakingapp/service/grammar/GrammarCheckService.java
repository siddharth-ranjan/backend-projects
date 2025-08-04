package com.hackathon.markdownnotetakingapp.service.grammar;

import com.hackathon.markdownnotetakingapp.util.MarkdownTextExtractor;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.languagetool.JLanguageTool;
import org.languagetool.rules.RuleMatch;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GrammarCheckService {

    private final Parser markdownParser;
    private final JLanguageTool languageTool;

    // Inject both beans via the constructor
    public GrammarCheckService(Parser markdownParser, JLanguageTool languageTool) {
        this.markdownParser = markdownParser;
        this.languageTool = languageTool;
    }

    public GrammarCheckResponse checkGrammar(MultipartFile file) throws IOException {
        // 1. Get content from the uploaded file
        String fileContent = new String(file.getBytes(), StandardCharsets.UTF_8);

        // 2. Parse the Markdown to an AST Node
        Node document = markdownParser.parse(fileContent);

        // 3. Extract plain text using our new utility
        String plainText = MarkdownTextExtractor.extractText(document);

        // If there's no text, no need to check
        if(plainText.isBlank()) {
            return new GrammarCheckResponse(null, false); // Return a response with no errors
        }

        // 4. Use LanguageTool to find rule matches (errors)
        List<RuleMatch> matches = languageTool.check(plainText);

        // 5. Convert the matches into a simple list of messages for the response
        List<String> errorMessages = matches.stream()
                .map(RuleMatch::getMessage)
                .toList();

        return new GrammarCheckResponse(errorMessages, matches.isEmpty());

    }
}