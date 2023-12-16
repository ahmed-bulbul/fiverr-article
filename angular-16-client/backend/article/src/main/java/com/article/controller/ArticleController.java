package com.article.controller;

import com.article.PageData;
import com.article.dto.ArticleRequestDto;
import com.article.entity.Article;
import com.article.service.ArticleService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public PageData getAllArticle(@PageableDefault(sort = "id",
            direction = Sort.Direction.DESC) Pageable pageable) {
        return  articleService.getAll(pageable);
    }

}
