package com.stackunderflow.backend.service;

import com.stackunderflow.backend.DTOS.CommentDTO;
import com.stackunderflow.backend.DTOS.Message;
import com.stackunderflow.backend.DTOS.SaveCommentDTO;
import com.stackunderflow.backend.model.Comment;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface CommentService {
    void saveComment(SaveCommentDTO comment);
    List<Comment> getAllComments();
    CommentDTO getCommentById(Long id);
    Message likeComment(Long id, String email);
    Message dislikeComment(Long id, String email);
    Message deleteLikeOrDislike(Long id, String email);
}
