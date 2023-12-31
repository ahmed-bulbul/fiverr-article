package com.article.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArticleResponseDto {
    private Long articleId;
    private String title;
    private String body;
    private String image;

    private int likes;

    private int dislikes;

    private Boolean disabled;
    private String fileExtension;
    private AuthorResponseDto author;
    @JsonFormat(pattern = "dd MMMM yyyy hh:mm:ss a", shape = JsonFormat.Shape.STRING)
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "dd MMMM yyyy hh:mm:ss a", shape = JsonFormat.Shape.STRING)
    private LocalDateTime updatedAt;

    private List<CommentResponseDto> comments;

}
