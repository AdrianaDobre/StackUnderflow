package com.stackunderflow.backend.repository;

import com.stackunderflow.backend.model.PostXTopic;
import com.stackunderflow.backend.model.PostXTopicId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PostXTopicRepository extends JpaRepository<PostXTopic, PostXTopicId> {
    @Query("select pt from PostXTopic pt where pt.id.post.id = :postId")
    List<PostXTopic> findPostXTopicByPostId(Long postId);

    @Query("select pt from PostXTopic pt join Post p on p.id = pt.id.post.id where p.user.id = :userId")
    List<PostXTopic> findPostXTopicByUserId(Long userId);

    @Modifying
    @Query("delete from PostXTopic pt where pt.id.post.id = :postId")
    @Transactional
    void deletePostXTopicByPostId(Long postId);
}
