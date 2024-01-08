package com.stackunderflow.backend.controller;

import com.stackunderflow.backend.DTOS.CommentDTO;
import com.stackunderflow.backend.DTOS.EditAnswerDTO;
import com.stackunderflow.backend.DTOS.EditCommentDTO;
import com.stackunderflow.backend.DTOS.Message;
import com.stackunderflow.backend.DTOS.SaveCommentDTO;
import com.stackunderflow.backend.DTOS.SuggestionDTOAns;
import com.stackunderflow.backend.model.Comment;
import com.stackunderflow.backend.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/answer")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    Message saveComment(@RequestBody SaveCommentDTO comment, Principal principal){
        return commentService.saveComment(comment, principal.getName());
    }

    @GetMapping("/allComments")
    List<Comment> getAllComments(){
        return commentService.getAllComments();
    }

    @GetMapping("/{id}")
    CommentDTO getCommentById(@PathVariable Long id){
        return commentService.getCommentById(id);
    }

    @PostMapping("/edit/{id}")
    Message editCommentById(@PathVariable Long id, @RequestBody EditCommentDTO editCommentDTO, Principal principal){
        return commentService.editCommentById(id, editCommentDTO,principal.getName());
    }

    @DeleteMapping("/delete/{id}")
    Message deleteCommentById(@PathVariable Long id, Principal principal){
        return commentService.deleteCommentById(id,principal.getName());
    }

    @GetMapping("/all")
    List<CommentDTO> getAllByMostLikes(@RequestParam Long postId){
        return commentService.getAllByMostLikes(postId);
    }

    @GetMapping("/{id}/suggestions")
    List<SuggestionDTOAns> getAllSuggestions(@PathVariable Long id){
        return commentService.getAllSuggestions(id);
    }

    @GetMapping("/{id}/history")
    List<EditAnswerDTO> getAllEditsForComment(@PathVariable Long id){
        return commentService.getAllEditsForComment(id);
    }

    @PutMapping("/{id}/accept/{suggestionId}")
    Message acceptSuggestion(@PathVariable Long id, @PathVariable Long suggestionId){
        return commentService.acceptSuggestion(id, suggestionId);
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
