package com.article.controller;

import com.article.dto.ArticleRequestDto;
import com.article.entity.Article;
import com.article.service.ArticleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/article")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping
    public ResponseEntity<String> createArticle(@RequestBody ArticleRequestDto dto) {
        Article article = articleService.create(dto);
        return ResponseEntity.ok("Successfully create Article " + article.getId());
    }

    @GetMapping
    public void getAllArticle(){
       // articleService
    }

}
