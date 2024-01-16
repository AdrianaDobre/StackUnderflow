package com.stackunderflow.backend.controller;

import com.stackunderflow.backend.DTOS.Message;
import com.stackunderflow.backend.DTOS.PostDTO;
import com.stackunderflow.backend.DTOS.SavePostDTO;
import com.stackunderflow.backend.model.Post;
import com.stackunderflow.backend.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    ResponseEntity<Message> savePost(@RequestBody SavePostDTO post, Principal principal){
        return new ResponseEntity<>(postService.savePost(post,principal.getName()), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/edit/{id}")
    ResponseEntity<Message> editPost(@RequestBody SavePostDTO post,@PathVariable Long id, Principal principal){
        return new ResponseEntity<>(postService.editPost(post,id,principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/all")
    ResponseEntity<List<Post>> getAllPosts(){
        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    ResponseEntity<PostDTO> getPostById(@PathVariable Long id, Principal principal){
        return new ResponseEntity<>(postService.getPostById(id, principal == null ? null : principal.getName()), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/delete/{id}")
    ResponseEntity<Message> deletePost(@PathVariable Long id, Principal principal){
        return new ResponseEntity<>(postService.deletePost(id,principal.getName()), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}/bestAnswer/{answerId}")
    ResponseEntity<Message> chooseBestAnswer(@PathVariable Long id,@PathVariable Long answerId, Principal principal){
        return new ResponseEntity<>(postService.chooseBestAnswer(id,answerId, principal.getName()), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}/bestAnswer/{answerId}")
    ResponseEntity<Message> deleteBestAnswer(@PathVariable Long id,@PathVariable Long answerId, Principal principal){
        return new ResponseEntity<>(postService.deleteBestAnswer(id,answerId, principal.getName()), HttpStatus.OK);
    }
}
