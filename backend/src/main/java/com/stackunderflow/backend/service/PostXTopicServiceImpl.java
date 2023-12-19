package com.stackunderflow.backend.service;

import com.stackunderflow.backend.DTOS.AddTopicToPostDTO;
import com.stackunderflow.backend.Exception.ObjectNotFound;
import com.stackunderflow.backend.model.Post;
import com.stackunderflow.backend.model.PostXTopic;
import com.stackunderflow.backend.model.PostXTopicId;
import com.stackunderflow.backend.model.Topic;
import com.stackunderflow.backend.repository.PostRepository;
import com.stackunderflow.backend.repository.PostXTopicRepository;
import com.stackunderflow.backend.repository.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostXTopicServiceImpl implements PostXTopicService{
    private final PostXTopicRepository postXTopicRepository;
    private final PostRepository postRepository;
    private final TopicRepository topicRepository;

    @Override
    public void savePostXTopic(AddTopicToPostDTO addTopicToPostDTO) {
        Post post = postRepository.findById(addTopicToPostDTO.getPostId()).orElseThrow(() -> new ObjectNotFound("Post not found"));
        Topic topic = topicRepository.findById(addTopicToPostDTO.getTopicId()).orElseThrow(() -> new ObjectNotFound("Topic not found"));
        PostXTopicId id = new PostXTopicId(post,topic);
        postXTopicRepository.save(new PostXTopic(id));
    }

    @Override
    public List<PostXTopic> getAllPostXTopics() {
        return postXTopicRepository.findAll();
    }
}
