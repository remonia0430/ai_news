package com.example.news_project.controller;

import com.example.news_project.dto.PageResponse;
import com.example.news_project.dto.user.UserCreateRequest;
import com.example.news_project.dto.user.UserResponse;
import com.example.news_project.dto.user.UserUpdateRequest;
import com.example.news_project.model.enums.UserRole;
import com.example.news_project.security.UserPrincipal;
import com.example.news_project.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService service;
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("me")
    public ResponseEntity<UserResponse> getMyProfile(@AuthenticationPrincipal UserPrincipal user){
        return ResponseEntity.ok(service.getById(user.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("updateMe")
    public ResponseEntity<UserResponse> updateMyProfile(@AuthenticationPrincipal UserPrincipal user , @RequestBody UserUpdateRequest req){
        return ResponseEntity.ok(service.updateProfile(req, user.getId()));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<PageResponse<UserResponse>> getUserAll(@RequestParam(defaultValue = "1") int page,
                                                                 @RequestParam(defaultValue = "10") int page_size) {
        return ResponseEntity.ok(service.getAllUsers(page, page_size));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/create")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserCreateRequest req){

        return ResponseEntity.ok(service.createUser(req));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/admin/{user_id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long user_id, @RequestBody UserUpdateRequest req){
        return ResponseEntity.ok(service.updateRole(req, user_id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/admin/delete/{id}")
    public ResponseEntity<Boolean> deleteUser(@PathVariable Long id){
        service.deleteUser(id);
        return ResponseEntity.ok(Boolean.TRUE);
    }

}
