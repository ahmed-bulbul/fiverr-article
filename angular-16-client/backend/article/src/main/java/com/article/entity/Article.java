package com.article.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 500)
    private String body;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;
    @Column(name = "author_id", insertable = false, updatable = false)
    private Long authorId;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    private LocalDateTime updateAt;

    @Column(length = 500)
    private String image;

    private int likes;

    private int dislikes;

    private boolean disabled;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @PreUpdate
    private void onBaseUpdate() {
        this.updateAt = LocalDateTime.now();
    }


}