package com.article.service;

import com.article.PageData;
import com.article.dto.ArticleRequestDto;
import com.article.dto.ArticleResponseDto;
import com.article.dto.ImageResponseDto;
import com.article.entity.Article;
import org.springframework.data.domain.Pageable;

public interface ArticleService {
    Article create(ArticleRequestDto dto);

    PageData getAll(Pageable pageable);

    ArticleResponseDto getSingle(Long id);

    ImageResponseDto getImage(Long id);

    void deleteArticle(Long id);
}
