package com.example.news_project.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookmark")
public class Bookmark {
    @EmbeddedId
    private BookmarkId id;

    @ManyToOne(fetch = FetchType.LAZY) @MapsId("userId")
    @JoinColumn(name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_bookmark_user"))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY) @MapsId("articleId")
    @JoinColumn(name = "article_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_bookmark_article"))
    private Article article;

    @Column(name = "create_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createAt;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    public BookmarkId getId() {
        return id;
    }

    public void setId(BookmarkId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
    
    public static Bookmark createBookmark(User user, Article article, BookmarkId id) {
        Bookmark bookmark = new Bookmark();
        bookmark.id = id;
        bookmark.setUser(user);
        bookmark.setArticle(article);
        bookmark.setCreateAt(LocalDateTime.now());
        return bookmark;
    }
}
