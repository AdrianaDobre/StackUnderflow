package com.stackunderflow.backend.service;

import com.stackunderflow.backend.DTOS.CommentDTO;
import com.stackunderflow.backend.DTOS.EditAnswerDTO;
import com.stackunderflow.backend.DTOS.EditCommentDTO;
import com.stackunderflow.backend.DTOS.Message;
import com.stackunderflow.backend.DTOS.Message;
import com.stackunderflow.backend.DTOS.SaveCommentDTO;
import com.stackunderflow.backend.DTOS.SuggestionDTOAns;
import com.stackunderflow.backend.model.Comment;
import org.apache.coyote.BadRequestException;

import java.util.List;

public interface CommentService {
    Message saveComment(SaveCommentDTO comment, String email);
    List<Comment> getAllComments();
    CommentDTO getCommentById(Long id);
    Message editCommentById(Long commentId, EditCommentDTO editCommentDTO, String email);
    Message deleteCommentById(Long commentId, String email);
    List<CommentDTO> getAllByMostLikes(Long postId);
    List<SuggestionDTOAns> getAllSuggestions(Long id);
    List<EditAnswerDTO> getAllEditsForComment(Long id);
    Message acceptSuggestion(Long id, Long suggestionId);
    Message likeComment(Long id, String email);
    Message dislikeComment(Long id, String email);
    Message deleteLikeOrDislike(Long id, String email);
}
