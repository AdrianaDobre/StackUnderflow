package com.stackunderflow.backend.controller;

import com.stackunderflow.backend.DTOS.SaveCommentDTO;
import com.stackunderflow.backend.model.Comment;
import com.stackunderflow.backend.service.CommentService;
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
@RequestMapping("/comment")
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
}
