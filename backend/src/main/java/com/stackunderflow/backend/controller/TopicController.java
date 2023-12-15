package com.stackunderflow.backend.controller;

import com.stackunderflow.backend.model.Topic;
import com.stackunderflow.backend.service.TopicService;
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
@RequestMapping("/topic")
@RequiredArgsConstructor
public class TopicController {
    private final TopicService topicService;

    @PostMapping("/save")
    void saveTopic(@RequestBody Topic topic){
        topicService.saveTopic(topic);
    }
    @GetMapping("/all")
    List<Topic> getAllTopics(){
        return topicService.getAllTopics();
    }
}
