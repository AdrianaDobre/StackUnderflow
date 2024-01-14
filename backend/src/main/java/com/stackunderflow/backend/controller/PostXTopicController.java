package com.stackunderflow.backend.controller;

import com.stackunderflow.backend.DTOS.AddTopicToPostDTO;
import com.stackunderflow.backend.model.PostXTopic;
import com.stackunderflow.backend.service.PostXTopicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/postXTopic")
@RequiredArgsConstructor
public class PostXTopicController {
    private final PostXTopicService postXTopicService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/save")
    void savePostXTopic(@RequestBody AddTopicToPostDTO addTopicToPostDTO){
        postXTopicService.savePostXTopic(addTopicToPostDTO);
    }

    @GetMapping("/all")
    ResponseEntity<List<PostXTopic>> getAllPostXTopics(){
        return new ResponseEntity<>(postXTopicService.getAllPostXTopics(), HttpStatus.OK);
    }
}
