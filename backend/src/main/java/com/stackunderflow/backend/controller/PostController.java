package com.stackunderflow.backend.controller;

import com.stackunderflow.backend.DTOS.Message;
import com.stackunderflow.backend.DTOS.PostDTO;
import com.stackunderflow.backend.DTOS.SavePostDTO;
import com.stackunderflow.backend.model.Post;
import com.stackunderflow.backend.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @PostMapping
    Message savePost(@RequestBody SavePostDTO post, Principal principal){
        return postService.savePost(post,principal.getName());
    }

    @PutMapping("/edit/{id}")
    Message editPost(@RequestBody SavePostDTO post,@PathVariable Long id, Principal principal){
        return postService.editPost(post,id,principal.getName());
    }

    @GetMapping("/all")
    List<Post> getAllPosts(){
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    PostDTO getPostById(@PathVariable Long id, Principal principal){
        return postService.getPostById(id, principal == null? null : principal.getName());
    }

    @DeleteMapping("/delete/{id}")
    Message deletePost(@PathVariable Long id, Principal principal){
        return postService.deletePost(id,principal.getName());
    }

    @PutMapping("/{id}/bestAnswer/{answerId}")
    Message chooseBestAnswer(@PathVariable Long id,@PathVariable Long answerId, Principal principal){
        return postService.chooseBestAnswer(id,answerId, principal.getName());
    }
}
