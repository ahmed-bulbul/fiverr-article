package com.article.dto;

import com.article.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ArticleRequestDto {
    private String title;
    private String body;
    private String image;

    private int likes;

    private int dislikes;

    private Boolean disabled;
    private String fileExtension;


}
