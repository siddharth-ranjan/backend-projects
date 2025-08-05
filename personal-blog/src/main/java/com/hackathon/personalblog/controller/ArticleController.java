package com.hackathon.personalblog.controller;

import com.hackathon.personalblog.model.Article;
import com.hackathon.personalblog.service.ArticleService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping(value = { "/", "/home" })
    public ResponseEntity<Resource> home() {
        Resource htmlFile = new ClassPathResource("static/html/index.html");
        return ResponseEntity
                .ok()
                .contentType(MediaType.TEXT_HTML)
                .body(htmlFile);
    }

    @GetMapping("/api/articles")
    public ResponseEntity<List<Article>> getAllArticles() {
        List<Article> articles = articleService.getAllArticles();
        if(articles.isEmpty()){return new ResponseEntity<>(HttpStatus.NO_CONTENT);}
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    @GetMapping("/api/article/{article-id}")
    public ResponseEntity<Article> getArticleById(@PathVariable("article-id") Long articleId) {
        Article article = articleService.getArticleById(articleId);
        if(article == null){return new ResponseEntity<>(HttpStatus.NO_CONTENT);}
        return new ResponseEntity<>(article, HttpStatus.OK);
    }

    // mandate
    @GetMapping("/api/admin")
    public ResponseEntity<List<Article>> getAdminArticles() {
        List<Article> articles = articleService.getAllArticles();
        if(articles.isEmpty()){return new ResponseEntity<>(HttpStatus.NO_CONTENT);}
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

    // mandate
    @GetMapping("/admin")
    public ResponseEntity<Resource> getAdminArticle() {
        Resource htmlFile = new ClassPathResource("static/html/admin.html");
        return ResponseEntity
                .ok()
                .contentType(MediaType.TEXT_HTML)
                .body(htmlFile);
    }

    // mandate
    @GetMapping("/new")
    public ResponseEntity<Resource> getNewArticleFormHtml() {
        Resource htmlFile = new ClassPathResource("static/html/create.html");
        return ResponseEntity
                .ok()
                .contentType(MediaType.TEXT_HTML)
                .body(htmlFile);
    }

    @PostMapping("/api/create")
    public ResponseEntity<Article> createArticle(@RequestBody Article article) {
        return new ResponseEntity<>(articleService.createArticle(article), HttpStatus.CREATED);
    }

    @GetMapping("/edit/{article-id}")
    public ResponseEntity<Resource> editArticle(@PathVariable("article-id") Long articleId) {
        Resource htmlFile = new ClassPathResource("static/html/edit.html");
        return ResponseEntity
                .ok()
                .contentType(MediaType.TEXT_HTML)
                .body(htmlFile);
    }

    @PostMapping("/api/edit/{article-id}")
    public ResponseEntity<Article> updateArticle(@PathVariable("article-id") Long articleId, @RequestBody Article article) {
        return new ResponseEntity<>(articleService.updateArticle(articleId, article), HttpStatus.OK);
    }

    @DeleteMapping("/api/delete/{article-id}")
    public ResponseEntity<String> deleteArticle(@PathVariable("article-id") Long articleId) {
        articleService.deleteArticle(articleId);
        if(articleService.getArticleById(articleId) == null) {
            return ResponseEntity.ok().body("Article deleted successfully!!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
