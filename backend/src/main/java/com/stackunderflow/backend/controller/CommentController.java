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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('USER')")
    @PostMapping
    ResponseEntity<Message> saveComment(@RequestBody SaveCommentDTO comment, Principal principal){
        return new ResponseEntity<>(commentService.saveComment(comment, principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/allComments")
    ResponseEntity<List<Comment>> getAllComments(){
        return new ResponseEntity<>(commentService.getAllComments(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<CommentDTO> getCommentById(@PathVariable Long id){
        return new ResponseEntity<>(commentService.getCommentById(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/edit/{id}")
    ResponseEntity<Message> editCommentById(@PathVariable Long id, @RequestBody EditCommentDTO editCommentDTO, Principal principal){
        return new ResponseEntity<>(commentService.editCommentById(id, editCommentDTO,principal.getName()), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/delete/{id}")
    ResponseEntity<Message> deleteCommentById(@PathVariable Long id, Principal principal){
        return new ResponseEntity<>(commentService.deleteCommentById(id,principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/all")
    ResponseEntity<List<CommentDTO>> getAllByMostLikes(@RequestParam Long postId){
        return new ResponseEntity<>(commentService.getAllByMostLikes(postId), HttpStatus.OK);
    }

    @GetMapping("/{id}/suggestions")
    ResponseEntity<List<SuggestionDTOAns>> getAllSuggestions(@PathVariable Long id){
        return new ResponseEntity<>(commentService.getAllSuggestions(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/history")
    ResponseEntity<List<EditAnswerDTO>> getAllEditsForComment(@PathVariable Long id){
        return new ResponseEntity<>(commentService.getAllEditsForComment(id), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}/accept/{suggestionId}")
    ResponseEntity<Message> acceptSuggestion(@PathVariable Long id, @PathVariable Long suggestionId){
        return new ResponseEntity<>(commentService.acceptSuggestion(id, suggestionId), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{id}/like")
    ResponseEntity<Message> LikeAnswer(@PathVariable Long id, Principal principal) {
        return new ResponseEntity<>(commentService.likeComment(id, principal.getName()), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/{id}/dislike")
    ResponseEntity<Message> DislikeAnswer(@PathVariable Long id, Principal principal) {
        return new ResponseEntity<>(commentService.dislikeComment(id, principal.getName()), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/deleteLikeOrDislike/{id}")
    ResponseEntity<Message> deleteLikeOrDislike(@PathVariable Long id, Principal principal){
        return new ResponseEntity<>(commentService.deleteLikeOrDislike(id,principal.getName()), HttpStatus.OK);
    }
}
