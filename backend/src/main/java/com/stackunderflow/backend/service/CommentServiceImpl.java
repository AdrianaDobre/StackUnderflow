package com.stackunderflow.backend.service;

import com.stackunderflow.backend.DTOS.SaveComment;
import com.stackunderflow.backend.model.Comment;
import com.stackunderflow.backend.repository.CommentRepository;
import com.stackunderflow.backend.repository.PostRepository;
import com.stackunderflow.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    @Override
    public void saveComment(SaveComment comment) {
        Comment newComment = Comment.builder()
                .user(userRepository.findById(comment.getUserId()).get())
                .post(postRepository.findById(comment.getPostId()).get())
                .text(comment.getText())
                .isTheBest(false)
                .date(comment.getDate()).build();
        commentRepository.save(newComment);
    }

    @Override
    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }
}
