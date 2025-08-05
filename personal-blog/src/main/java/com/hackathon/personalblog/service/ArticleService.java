package com.hackathon.personalblog.service;

import com.hackathon.personalblog.model.Article;
import com.hackathon.personalblog.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.jar.Attributes;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public Article getArticleById(Long id) {
        Optional<Article> article = articleRepository.findById(id);
        return article.orElse(null);
    }

    public Article createArticle(Article article) {
        return articleRepository.save(article);
    }


    public Article updateArticle(Long id, Article article) {
        Article toUpdate = articleRepository.findById(id).orElse(null);
        if(toUpdate == null) {return null;}
        toUpdate.setTitle(article.getTitle());
        toUpdate.setContent(article.getContent());
        return articleRepository.save(toUpdate);
    }

    public void deleteArticle(Long id) {
        Optional<Article> article = articleRepository.findById(id);
        article.ifPresent(articleRepository::delete);
    }
}
