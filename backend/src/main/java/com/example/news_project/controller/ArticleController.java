package com.example.news_project.controller;

import com.example.news_project.dto.PageResponse;
import com.example.news_project.dto.article.ArticleResponse;
import com.example.news_project.dto.article.ArticleResponseLite;
import com.example.news_project.dto.article.ModifyArticleRequest;
import com.example.news_project.repository.ArticleRepository;
import com.example.news_project.security.UserPrincipal;
import com.example.news_project.service.ArticleService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.service.annotation.PatchExchange;

@RestController
@RequestMapping("api/articles")
public class ArticleController {

    ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping
    public ResponseEntity<PageResponse<ArticleResponseLite>> getPagedArticles(@RequestParam(defaultValue = "1") int page,
                                                                              @RequestParam(defaultValue = "10") int page_size) {
        return ResponseEntity.ok(articleService.getPaged(page, page_size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponse> getArticleById(@PathVariable long id, @AuthenticationPrincipal UserPrincipal user ) {
        return ResponseEntity.ok(articleService.getArticleById(id, user.getId()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/admin/{id}")
    public ResponseEntity<Map<String, Boolean>> modifyArticle(@PathVariable long id, @RequestBody ModifyArticleRequest req){

        return ResponseEntity.ok(Map.of("success", articleService.modifyById(id, req.is_hidden())));
    }
}
