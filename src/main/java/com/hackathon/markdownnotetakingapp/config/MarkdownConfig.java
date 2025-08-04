package com.hackathon.markdownnotetakingapp.config;

import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.languagetool.JLanguageTool;
import org.languagetool.language.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MarkdownConfig {

    @Bean
    public Parser markdownParser() {
        return Parser.builder().build();
    }

    @Bean
    public JLanguageTool languageTool() {
        return new JLanguageTool(new AmericanEnglish());
    }

    @Bean
    public HtmlRenderer htmlRenderer() {
        return HtmlRenderer.builder().build();
    }
}