package com.article.service;

import com.article.dto.ArticleRequestDto;
import com.article.entity.Article;

public interface ArticleService {
    Article create(ArticleRequestDto dto);
}
