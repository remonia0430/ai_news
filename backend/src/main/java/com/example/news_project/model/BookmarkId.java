package com.example.news_project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class BookmarkId {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "article_id")
    private Long articleId;

    public BookmarkId(Long articleId, Long userId) {
        this.articleId = articleId;
        this.userId = userId;
    }

    public BookmarkId() {

    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }
}
