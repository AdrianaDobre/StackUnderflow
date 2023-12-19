package com.stackunderflow.backend.repository;

import com.stackunderflow.backend.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TopicRepository extends JpaRepository<Topic,Long > {
}
