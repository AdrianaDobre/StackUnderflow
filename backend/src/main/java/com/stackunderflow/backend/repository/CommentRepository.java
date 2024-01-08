package com.stackunderflow.backend.repository;

import com.stackunderflow.backend.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {
    @Modifying
    @Query("update Comment c set c.isTheBest = true where c.id = :answerId")
    @Transactional
    void chooseBestAnswer(Long answerId);

    List<Comment> findAllByPostId(Long postId);
}
