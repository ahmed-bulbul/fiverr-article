package com.article.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArticleRequestDto {
    @NotBlank
    private String title;
    @NotBlank
    private String body;
    @NotBlank
    private String image;

    @NotNull
    private Integer likes;
    @NotNull
    private Integer dislikes;

    private Boolean disabled = false;
    @NotBlank
    private String fileExtension;


}
