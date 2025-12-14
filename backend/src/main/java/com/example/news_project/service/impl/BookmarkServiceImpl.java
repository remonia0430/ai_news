package com.example.news_project.service.impl;

import com.example.news_project.dto.bookmark.BookmarkResponse;
import com.example.news_project.model.Bookmark;
import com.example.news_project.model.Article;
import com.example.news_project.model.BookmarkId;
import com.example.news_project.model.User;
import com.example.news_project.repository.*;
import com.example.news_project.service.BookmarkService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookmarkServiceImpl implements BookmarkService {

    BookmarkRepository repo;
    UserRepository userRepo;
    ArticleRepository articleRepo;

    public BookmarkServiceImpl(BookmarkRepository repo, UserRepository userRepo, ArticleRepository articleRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
        this.articleRepo = articleRepo;
    }

    private BookmarkResponse toDto(Bookmark bookmark) {
        return new BookmarkResponse(bookmark.getArticle().getId(), bookmark.getCreateAt());
    }

    @Override
    public List<BookmarkResponse> getBookmarks(Long userId) {
        List<Bookmark> bookmarks = repo.findByUserId(userId);

        return bookmarks.stream().map(this::toDto).toList();
    }

    @Override
    public BookmarkResponse addBookmark(Long userId, Long articleId) {
        User user = userRepo.findByIdAndIsDeletedFalse(userId).orElseThrow(() -> new IllegalArgumentException("user not exists"));
        Article article = articleRepo.findByIdAndIsDeletedFalse(articleId).orElseThrow(() -> new IllegalArgumentException("article not exists"));
        BookmarkId bookmarkId = new BookmarkId(userId, articleId);

        Bookmark bookmark = Bookmark.createBookmark(user, article, bookmarkId);

        repo.save(bookmark);

        return toDto(bookmark);
    }

    public boolean deleteBookmark(Long userId, Long articleId) {
        User u = userRepo.findByIdAndIsDeletedFalse(userId).orElseThrow(() -> new IllegalArgumentException("user not exists"));

        Bookmark bookmark = repo.findByUserAndArticleId(u, articleId).orElseThrow(() -> new IllegalArgumentException("bookmark not exists"));
        repo.delete(bookmark);
        return true;
    }
}
