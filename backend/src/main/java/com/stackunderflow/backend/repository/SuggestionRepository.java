package com.stackunderflow.backend.repository;

import com.stackunderflow.backend.model.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {
    List<Suggestion> findByUserIdAndCommentIdOrderByAcceptedOnDateDesc(Long userId, Long commentId);
    @Query("select s from Suggestion s where s.comment.id = :commentId and s.user.id <> :userId order by s.date desc ")
    List<Suggestion> findSuggestionsWithoutEdits(Long commentId,Long userId);
}
