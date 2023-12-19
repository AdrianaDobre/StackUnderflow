package com.stackunderflow.backend.service;

import com.stackunderflow.backend.DTOS.AddTopicToPostDTO;
import com.stackunderflow.backend.model.PostXTopic;

import java.util.List;

public interface PostXTopicService {
    void savePostXTopic(AddTopicToPostDTO addTopicToPostDTO);
    List<PostXTopic> getAllPostXTopics();
}
