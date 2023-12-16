package com.article.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentResponseDto {
    private Long id;
    private String text;
    private Long articleId;
    private Long userId;
    private String userFullName;
    private String userName;
    @JsonFormat(pattern = "dd MMMM yyyy hh:mm:ss a", shape = JsonFormat.Shape.STRING)
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "dd MMMM yyyy hh:mm:ss a", shape = JsonFormat.Shape.STRING)
    private LocalDateTime updatedAt;
}
