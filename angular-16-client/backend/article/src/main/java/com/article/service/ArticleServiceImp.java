package com.article.service;

import com.article.PageData;
import com.article.dto.*;
import com.article.entity.Article;
import com.article.entity.Comment;
import com.article.entity.User;
import com.article.repository.ArticleRepository;
import com.article.repository.CommentRepository;
import com.article.util.Helper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArticleServiceImp implements ArticleService {
    private final UserService userService;
    private final ArticleRepository articleRepository;
    private final String IMAGES_STORE_PATH = "src/images/";
    private final CommentRepository commentRepository;

    public ArticleServiceImp(UserService userService, ArticleRepository articleRepository, CommentRepository commentRepository) {
        this.userService = userService;
        this.articleRepository = articleRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public Article create(ArticleRequestDto dto) {
        if (!dto.getTitle().trim().isBlank()) {
            if (dto.getTitle().length() > 100) {
                throw new RuntimeException("title size exceed the limit must be between 100");
            }
        }
        if (!dto.getBody().trim().isBlank()) {
            if (dto.getBody().length() > 500) {
                throw new RuntimeException("title size exceed the limit must be between 500");
            }
        }

        Article article = convertToEntity(dto, new Article(), true);
        try {
            return articleRepository.save(article);
        } catch (Exception e) {
            throw new RuntimeException("Article Saved Failed. Cause: " + e.getMessage());
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


    @Override
    public PageData getAll(Pageable pageable) {
        Page<Article> pagedData = articleRepository.findAll(pageable);
        List<Article> content = pagedData.getContent();
        Set<Long> authorIds = content.stream().map(Article::getAuthorId).collect(Collectors.toSet());
        Map<Long, User> userMap = userService.findAllByIdIn(authorIds).stream().collect(Collectors.toMap(User::getId, Function.identity()));
        List<ArticleResponseDto> models = content.stream().map(article -> {
            ArticleResponseDto responseDto = convertToArticleResponseDto(article);
            AuthorResponseDto authorResponseDto = convertToAuthorResponse(userMap.get(article.getAuthorId()));
            responseDto.setAuthor(authorResponseDto);
            List<CommentResponseDto> collect = article.getComments().stream().map(comment -> convertToCommentResponseDto(comment, article.getId()))
                    .toList();
            responseDto.setComments(collect);
            return responseDto;
        }).collect(Collectors.toList());
        return PageData.builder()
                .model(models)
                .totalPages(pagedData.getTotalPages())
                .totalElements(pagedData.getTotalElements())
                .currentPage(pageable.getPageNumber() + 1)
                .build();
    }

    @Override
    public ArticleResponseDto getSingle(Long id) {
        Article article = findById(id);
        ArticleResponseDto responseDto = convertToArticleResponseDto(article);
        AuthorResponseDto authorResponseDto = convertToAuthorResponse(article.getAuthor());
        List<CommentResponseDto> collect = article.getComments().stream().map(comment -> convertToCommentResponseDto(comment, id))
                .collect(Collectors.toList());
        responseDto.setAuthor(authorResponseDto);
        responseDto.setComments(collect);
        return responseDto;
    }

    @Override
    public ImageResponseDto getImage(Long id) {
        Article article = findById(id);
        if (Objects.nonNull(article.getImage()) && !article.getImage().trim().isBlank()) {
            return getBase64(article.getImage(), id);
        } else {
            throw new RuntimeException("Base 64 conversation failed");
        }
    }

    @Override
    public void deleteArticle(Long id) {
        Article article = findById(id);
        if (Objects.nonNull(article.getImage()) && !article.getImage().trim().isBlank()) {
            try {
                Path path = Path.of("src/images/" + article.getImage());
                Files.deleteIfExists(path);
            } catch (Exception e) {
                log.error(e.getMessage());
                throw new RuntimeException(e.getMessage());
            }
        }
        try {
            articleRepository.delete(article);
        } catch (Exception e) {
            throw new RuntimeException("Cannot delete articles Cause: " + e.getMessage());
        }
    }

    @Override
    public void giveCommentInPost(CommentDto dto, Long id) {
        if (Objects.nonNull(dto.getText()) && !dto.getText().trim().isBlank()) {
            if (dto.getText().length() > 100) {
                throw new RuntimeException("comment exceed the limit must be between 100 characters");
            }
        }
        Article article = findById(id);
        User currentUser = Helper.getCurrentUser();
        if (Objects.isNull(currentUser)) {
            throw new RuntimeException("Log in user not found");
        }
        Comment comment = new Comment();
        comment.setText(dto.getText());
        comment.setUser(currentUser);
        comment.setArticle(article);

        try {
            commentRepository.save(comment);
            articleRepository.save(article);
        } catch (Exception e) {
            throw new RuntimeException("Comment not saved in article cause: " + e.getMessage());
        }

    }

    @Override
    public List<CommentResponseDto> getCommand(Long id) {
        Article article = findById(id);

        return article.getComments().stream().map(comment -> convertToCommentResponseDto(comment, id))
                .collect(Collectors.toList());

    }

    @Override
    public void updateLike(Long id) {
        Article article = findById(id);
        int likes = article.getLikes();
        article.setLikes(likes + 1);
        try {
            articleRepository.save(article);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update like Cause: " + e.getMessage());
        }
    }

    @Override
    public void updateDislike(Long id) {
        Article article = findById(id);
        int dislikes = article.getDislikes();
        article.setDislikes(dislikes + 1);
        try {
            articleRepository.save(article);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update dislike Cause: " + e.getMessage());
        }
    }

    @Override
    public void enableOrDisableArticle(Long id, boolean b) {
        Article article = findById(id);
        article.setDisabled(b);
        try {
            articleRepository.save(article);
        } catch (Exception e) {
            throw new RuntimeException("Failed to disable article Cause: " + e.getMessage());
        }
    }

    private Article findById(Long id) {
        return articleRepository.findById(id).orElseThrow(() -> new RuntimeException("Invalid Article Id or not found"));
    }

    public ArticleResponseDto convertToArticleResponseDto(Article article) {
        ArticleResponseDto responseDto = new ArticleResponseDto();
        responseDto.setArticleId(article.getId());
        responseDto.setTitle(article.getTitle());
        responseDto.setBody(article.getBody());
//        if (Objects.nonNull(article.getImage())) {
//            String image = article.getImage();
//            Path path = Paths.get("src/images/" + image);
//            String fileExtension = image.substring(image.lastIndexOf("."));
//            String[] split = fileExtension.split("\\.");
//            try {
//                byte[] fileBytes = Files.readAllBytes(path);
//                String base64String = Base64.getEncoder().encodeToString(fileBytes);
//                if (split.length > 0) {
//                    String imageExtension = fileExtension.split("\\.")[1].trim();
//                    responseDto.setFileExtension(fileExtension);
//                    responseDto.setImage("data:image/" + imageExtension + ";base64," + base64String);
//                } else {
//                    responseDto.setFileExtension(fileExtension);
//                    responseDto.setImage("data:image/" + fileExtension + "};base64," + base64String);
//                }
//            } catch (IOException e) {
//                throw new RuntimeException("Image not found");
//            }
//        }

        responseDto.setLikes(article.getLikes());
        responseDto.setDisabled(article.isDisabled());
        responseDto.setDislikes(article.getDislikes());

        responseDto.setCreatedAt(article.getCreatedAt());
        responseDto.setUpdatedAt(article.getUpdateAt());
        return responseDto;

    }

    private ImageResponseDto getBase64(String image, Long id) {
        Path path = Paths.get("src/images/" + image);
        String fileExtension = image.substring(image.lastIndexOf("."));
        String[] split = fileExtension.split("\\.");
        try {
            byte[] fileBytes = Files.readAllBytes(path);
            String base64String = Base64.getEncoder().encodeToString(fileBytes);
            if (split.length > 0) {
                String imageExtension = fileExtension.split("\\.")[1].trim();
                return new ImageResponseDto(id, "data:image/" + imageExtension + ";base64," + base64String, fileExtension);
            } else {
                return new ImageResponseDto(id, "data:image/" + fileExtension + "};base64," + base64String, fileExtension);
            }
        } catch (IOException e) {
            throw new RuntimeException("Image not found");
        }
    }


    public AuthorResponseDto convertToAuthorResponse(User user) {
        if (Objects.nonNull(user)) {
            AuthorResponseDto authorResponseDto = new AuthorResponseDto();
            authorResponseDto.setId(user.getId());
            authorResponseDto.setUserFullName(user.getName());
            authorResponseDto.setUserName(user.getUsername());
            return authorResponseDto;
        }
        return null;
    }

    public CommentResponseDto convertToCommentResponseDto(Comment comment, Long articleId) {
        if (Objects.nonNull(comment)) {
            CommentResponseDto responseDto = new CommentResponseDto();
            responseDto.setId(comment.getId());
            responseDto.setText(comment.getText());
            responseDto.setArticleId(articleId);
            User user = comment.getUser();
            if (Objects.nonNull(user)) {
                responseDto.setUserId(user.getId());
                responseDto.setUserFullName(user.getName());
                responseDto.setUserName(user.getUsername());
            }
            responseDto.setCreatedAt(comment.getCreatedAt());
            responseDto.setUpdatedAt(comment.getUpdatedAt());
            return responseDto;
        }
        return null;
    }


}
