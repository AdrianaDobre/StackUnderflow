package com.stackunderflow.backend.repository;

import com.stackunderflow.backend.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users,Long> {
}
