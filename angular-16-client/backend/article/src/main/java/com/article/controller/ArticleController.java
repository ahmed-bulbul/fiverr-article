package com.article.controller;

import com.article.PageData;
import com.article.dto.*;
import com.article.entity.Article;
import com.article.service.ArticleService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/article")
@CrossOrigin("*")
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
        return articleService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public ArticleResponseDto getSingle(@PathVariable Long id) {
        return articleService.getSingle(id);
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<ImageResponseDto> getImage(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.getImage(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.ok("Id: " + id + " Article Delete successfully");
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<String> giveComment(@RequestBody CommentDto dto, @PathVariable Long id) {
        articleService.giveCommentInPost(dto, id);
        return ResponseEntity.ok("Added Comment successfully in id: " + id);
    }

    @GetMapping("/{id}/comment")
    public ResponseEntity<List<CommentResponseDto>> getCommand(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.getCommand(id));
    }

    @PutMapping("/{id}/like")
    public ResponseEntity<String> updateLike(@PathVariable Long id) {
        articleService.updateLike(id);
        return ResponseEntity.ok("Id: "+id+" Article Liked Successfully ");
    }


    @PutMapping("/{id}/dislike")
    public ResponseEntity<String> updateDislike(@PathVariable Long id) {
        articleService.updateDislike(id);
        return ResponseEntity.ok("Id: "+id+" Article disliked Successfully ");
    }


    @PutMapping("/{id}/disable")
    public ResponseEntity<String> updateDisable(@PathVariable Long id) {
        articleService.enableOrDisableArticle(id, false);
        return ResponseEntity.ok("Id: "+id+" Article Disable Successfully ");
    }

    @PutMapping("/{id}/enable")
    public ResponseEntity<String> updateEnable(@PathVariable Long id) {
        articleService.enableOrDisableArticle(id, true);
        return ResponseEntity.ok("Id: "+id+" Article enable Successfully ");
    }

}
