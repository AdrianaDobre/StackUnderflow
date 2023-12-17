package com.stackunderflow.backend.repository;

import com.stackunderflow.backend.model.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {
}
