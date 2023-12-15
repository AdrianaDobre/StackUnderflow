package com.stackunderflow.backend.controller;

import com.stackunderflow.backend.model.Post;
import com.stackunderflow.backend.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/save")
    void savePost(@RequestBody Post post){
        postService.savePost(post);
    }
    @GetMapping("/all")
    List<Post> getAllPosts(){
        return postService.getAllPosts();
    }
}
