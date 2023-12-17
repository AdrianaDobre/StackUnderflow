package com.stackunderflow.backend.repository;

import com.stackunderflow.backend.model.Vote;
import com.stackunderflow.backend.model.VoteId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, VoteId> {
}
