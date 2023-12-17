package com.stackunderflow.backend.repository;

import com.stackunderflow.backend.model.PostXTopic;
import com.stackunderflow.backend.model.PostXTopicId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostXTopicRepository extends JpaRepository<PostXTopic, PostXTopicId> {
}
