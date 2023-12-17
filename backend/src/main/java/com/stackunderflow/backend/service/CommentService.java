package com.stackunderflow.backend.service;

import com.stackunderflow.backend.DTOS.SaveCommentDTO;
import com.stackunderflow.backend.model.Comment;

import java.util.List;

public interface CommentService {
    void saveComment(SaveCommentDTO comment);
    List<Comment> getAllComments();
}
