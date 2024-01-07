package com.stackunderflow.backend.controller;

import com.stackunderflow.backend.DTOS.CommentDTO;
import com.stackunderflow.backend.DTOS.Message;
import com.stackunderflow.backend.DTOS.SaveCommentDTO;
import com.stackunderflow.backend.model.Comment;
import com.stackunderflow.backend.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/answer")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/save")
    void saveComment(@RequestBody SaveCommentDTO comment){
        commentService.saveComment(comment);
    }

    @GetMapping("/all")
    List<Comment> getAllComments(){
        return commentService.getAllComments();
    }

    @GetMapping("/{id}")
    CommentDTO getCommentById(@PathVariable Long id){
        return commentService.getCommentById(id);
    }

    @PostMapping("/{id}/like")
    Message LikeAnswer(@PathVariable Long id, Principal principal) {
         return commentService.likeComment(id, principal.getName());
    }

    @PostMapping("/{id}/dislike")
    Message DislikeAnswer(@PathVariable Long id, Principal principal) {
        return commentService.dislikeComment(id, principal.getName());
    }

    @DeleteMapping("/{id}")
    Message deleteLikeOrDislike(@PathVariable Long id, Principal principal) throws BadRequestException {
        return commentService.deleteLikeOrDislike(id,principal.getName());
    }
}
