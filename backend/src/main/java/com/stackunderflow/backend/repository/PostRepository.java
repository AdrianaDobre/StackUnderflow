package com.stackunderflow.backend.repository;

import com.stackunderflow.backend.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Long> {

    @Query("select p from Post p where p.user.id = :userId")
    List<Post> findPostsByUserId(Long userId);
}
