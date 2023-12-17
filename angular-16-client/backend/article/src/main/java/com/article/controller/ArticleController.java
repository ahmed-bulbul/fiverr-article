package com.article.controller;

import com.article.PageData;
import com.article.constant.ERole;
import com.article.dto.*;
import com.article.entity.Article;
import com.article.entity.User;
import com.article.service.ArticleService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/article")
@CrossOrigin("*")
public class ArticleController {
  private final ArticleService articleService;

  public ArticleController(ArticleService articleService) {
    this.articleService = articleService;
  }

  @PostMapping
  @PreAuthorize("hasRole('ROLE_USER')")
  public ResponseEntity<Map<String, String>> createArticle(@RequestBody ArticleRequestDto dto) {
    Map<String, String> response = new HashMap<>();
    Article article = articleService.create(dto);
    response.put("Success", "Successfully created");
    return ResponseEntity.ok(response);
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
  @PreAuthorize("hasRole('ROLE_USER')")
  public ResponseEntity<String> deleteArticle(@PathVariable Long id) {
    articleService.deleteArticle(id);
    return ResponseEntity.ok("Id: " + id + " Article Delete successfully");
  }

  @PostMapping("/{id}/comment")
  @PreAuthorize("hasRole('ROLE_USER')")
  public ResponseEntity<String> giveComment(@RequestBody CommentDto dto, @PathVariable Long id) {
    articleService.giveCommentInPost(dto, id);
    return ResponseEntity.ok("Added Comment successfully in id: " + id);
  }

  @GetMapping("/{id}/comment")
  public ResponseEntity<List<CommentResponseDto>> getCommand(@PathVariable Long id) {
    return ResponseEntity.ok(articleService.getCommand(id));
  }

  @PutMapping("/{id}/like")
  @PreAuthorize("hasRole('ROLE_USER')")
  public ResponseEntity<Map<String, String>> updateLike(@PathVariable Long id) {
    Map<String, String> response = new HashMap<>();
    articleService.updateLike(id);
    response.put("Success", "Successfully created");
    return ResponseEntity.ok(response);
  }


  @PutMapping("/{id}/dislike")
  @PreAuthorize("hasRole('ROLE_USER')")
  public ResponseEntity<Map<String, String>>  updateDislike(@PathVariable Long id) {
    Map<String, String> response = new HashMap<>();
    articleService.updateDislike(id);
    response.put("Success", "Successfully created");
    return ResponseEntity.ok(response);
  }



  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PutMapping("/{id}/disable")
  public ResponseEntity<Map<String, String>> updateDisable(@PathVariable Long id) {
    Map<String, String> response = new HashMap<>();
    boolean isDisable = true;
    Article article = articleService.findById(id);
    if (article.isDisabled()) {
      isDisable = false;
    }
    articleService.enableOrDisableArticle(id, isDisable);
    response.put("Success", "Successfully updated");
    return ResponseEntity.ok(response);
  }

  @PutMapping("/{id}/enable")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<Map<String, String>> updateEnable(@PathVariable Long id) {
    Map<String, String> response = new HashMap<>();
    articleService.enableOrDisableArticle(id, true);
    response.put("Success", "Successfully updated");
    return ResponseEntity.ok(response);
  }

}
