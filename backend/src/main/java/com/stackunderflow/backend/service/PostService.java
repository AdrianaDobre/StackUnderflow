package com.stackunderflow.backend.service;

import com.stackunderflow.backend.model.Post;

import java.util.List;

public interface PostService {
    void savePost(Post post);
    List<Post> getAllPosts();
}
