package com.article.service;

import com.article.PageData;
import com.article.dto.*;
import com.article.entity.Article;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleService {
    Article create(ArticleRequestDto dto);

    PageData getAll(Pageable pageable);

    ArticleResponseDto getSingle(Long id);

    ImageResponseDto getImage(Long id);

    void deleteArticle(Long id);

    void giveCommentInPost(CommentDto dto, Long id);

    List<CommentResponseDto> getCommand(Long id);

    void updateLike(Long id);

    void updateDislike(Long id);

    void enableOrDisableArticle(Long id, boolean b);

  Article findById(Long id);
}
