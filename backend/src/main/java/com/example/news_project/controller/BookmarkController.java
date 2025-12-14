package com.example.news_project.controller;


import com.example.news_project.dto.bookmark.BookmarkResponse;
import com.example.news_project.dto.follow.FollowingResponse;
import com.example.news_project.security.UserPrincipal;
import com.example.news_project.service.BookmarkService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/bookmarks")
public class BookmarkController {

    private BookmarkService bookmarkService;

    public BookmarkController(BookmarkService bookmarkService) {
        this.bookmarkService = bookmarkService;
    }

    @GetMapping("/me")
    public ResponseEntity<List<BookmarkResponse>> myFollows(@AuthenticationPrincipal UserPrincipal user) {
        return ResponseEntity.ok(bookmarkService.getBookmarks(user.getId()));
    }

    @PostMapping("/add/{article_id}")
    public ResponseEntity<BookmarkResponse> addBookmark(@AuthenticationPrincipal UserPrincipal user, @PathVariable Long article_id) {
        return ResponseEntity.ok(bookmarkService.addBookmark(user.getId(),article_id));
    }

    @DeleteMapping("/del/{article_id}")
    public ResponseEntity<Map<String, Boolean>> deleteBookmark(@PathVariable Long article_id, @AuthenticationPrincipal UserPrincipal user) {
        return ResponseEntity.ok(Map.of("success", bookmarkService.deleteBookmark(user.getId(), article_id)));
    }
}
