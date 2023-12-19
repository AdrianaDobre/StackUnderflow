package com.stackunderflow.backend.service;

import com.stackunderflow.backend.model.Topic;
import com.stackunderflow.backend.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicServiceImpl implements TopicService{
    private final TopicRepository topicRepository;
    @Override
    public void saveTopic(Topic topic) {
        topicRepository.save(topic);
    }

    @Override
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }
}
