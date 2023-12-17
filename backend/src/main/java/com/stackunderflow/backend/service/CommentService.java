package com.stackunderflow.backend.service;

import com.stackunderflow.backend.DTOS.SaveComment;
import com.stackunderflow.backend.model.Comment;

import java.util.List;

public interface CommentService {
    void saveComment(SaveComment comment);
    List<Comment> getAllComments();
}
