package com.stackunderflow.backend.service;

import com.stackunderflow.backend.DTOS.Message;
import com.stackunderflow.backend.DTOS.PostDTO;
import com.stackunderflow.backend.DTOS.SavePostDTO;
import com.stackunderflow.backend.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Message savePost(SavePostDTO post, String email);
    Message editPost(SavePostDTO post,Long id, String email);
    List<Post> getAllPosts();
    PostDTO getPostById(Long id, String email);
    Message deletePost(Long id, String email);
    Message chooseBestAnswer(Long answerId, String email);
}
