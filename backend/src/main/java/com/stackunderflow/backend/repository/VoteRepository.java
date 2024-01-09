package com.stackunderflow.backend.repository;

import com.stackunderflow.backend.model.Vote;
import com.stackunderflow.backend.model.VoteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, VoteId> {
    @Modifying
    @Query("delete from Vote vote where vote.id.comment.id = :commentId")
    @Transactional
    void deleteVoteByCommentId(Long commentId);

    @Modifying
    @Query("delete from Vote vote where vote.id.user.id = :userId")
    @Transactional
    void deleteVoteByUserId(Long userId);

    @Query("select vote from Vote vote where vote.id.comment.id = :commentId")
    List<Vote> getVotesByCommentId(Long commentId);

    @Query("select vote from Vote vote where vote.id.user.id = :userId")
    List<Vote> getVotesByUserId(Long userId);

    @Query("select vote from Vote vote join Comment c on vote.id.comment.id = c.id where vote.id.user.id = :userId and c.post.id = :postId")
    List<Vote> getVotesByUserAndPostId(Long userId, Long postId);
}
