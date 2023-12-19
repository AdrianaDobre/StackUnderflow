package com.stackunderflow.backend.controller;

import com.stackunderflow.backend.DTOS.AddTopicToPostDTO;
import com.stackunderflow.backend.model.PostXTopic;
import com.stackunderflow.backend.service.PostXTopicService;
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
@RequestMapping("/postXTopic")
@RequiredArgsConstructor
public class PostXTopicController {
    private final PostXTopicService postXTopicService;

    @PostMapping("/save")
    void savePostXTopic(@RequestBody AddTopicToPostDTO addTopicToPostDTO){
        postXTopicService.savePostXTopic(addTopicToPostDTO);
    }

    @GetMapping("/all")
    List<PostXTopic> getAllPostXTopics(){
        return postXTopicService.getAllPostXTopics();
    }
}
