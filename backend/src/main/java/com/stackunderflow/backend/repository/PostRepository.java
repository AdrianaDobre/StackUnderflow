package com.stackunderflow.backend.repository;

import com.stackunderflow.backend.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
