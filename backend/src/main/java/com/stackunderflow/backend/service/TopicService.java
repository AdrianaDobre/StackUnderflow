package com.stackunderflow.backend.service;

import com.stackunderflow.backend.model.Topic;

import java.util.List;

public interface TopicService {
    void saveTopic(Topic topic);
    List<Topic> getAllTopics();
}
