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

    @Query("select vote from Vote vote where vote.id.comment.id = :commentId")
    List<Vote> getVotesByCommentId(Long commentId);
}
