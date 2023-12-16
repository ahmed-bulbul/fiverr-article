package com.article.service;

import com.article.dto.ArticleRequestDto;
import com.article.entity.Article;
import com.article.entity.User;
import com.article.repository.ArticleRepository;
import com.article.util.Helper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class ArticleServiceImp implements ArticleService {
    private final UserService userService;
    private final ArticleRepository articleRepository;
    private final String IMAGES_STORE_PATH = "src/images/";

    public ArticleServiceImp(UserService userService, ArticleRepository articleRepository) {
        this.userService = userService;
        this.articleRepository = articleRepository;
    }

    @Override
    public Article create(ArticleRequestDto dto) {

        Article article = convertToEntity(dto, new Article(), true);
        try {
            return articleRepository.save(article);
        } catch (Exception e) {
            throw new RuntimeException("Article Saved Failed. Cause: "+e.getMessage());
        }

    }

    private Article convertToEntity(ArticleRequestDto dto, Article article, Boolean isCreate) {
        User currentUser = Helper.getCurrentUser();
        if (Objects.nonNull(currentUser)) {
            article.setAuthor(currentUser);
        } else {
            throw new RuntimeException("Log in user not found");
        }
        article.setTitle(dto.getTitle());
        article.setBody(dto.getBody());
        article.setLikes(dto.getLikes());
        article.setDislikes(dto.getDislikes());
        article.setDisabled(dto.getDisabled());

        if (isCreate) {
            saveImageInFile(dto, article);
        }
        return article;
    }

    private void saveImageInFile(ArticleRequestDto dto, Article entity) {
        String newFileName = UUID.randomUUID() + dto.getFileExtension();

        // Path to the folder where you want to move the file
        String destinationFolderPath = IMAGES_STORE_PATH;

        try {
            if (Objects.nonNull(entity.getImage()) && !entity.getImage().isBlank()) {
                Path path = Path.of(destinationFolderPath + entity.getImage());
                Files.deleteIfExists(path);
            }
            Path path = Path.of(destinationFolderPath + newFileName);


            String base64Image = dto.getImage();

            if (base64Image.startsWith("data:image")) {
                base64Image = base64Image.split(",")[1];
            }

            byte[] bytes = Base64.getDecoder().decode(base64Image);
            Files.write(path, bytes);
            entity.setImage(newFileName);

        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException("Image not saved");
        }
    }
}
