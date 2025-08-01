package com.evoting.evote_backend.repository;

import com.evoting.evote_backend.entity.Election;
import com.evoting.evote_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ElectionRepository extends JpaRepository<Election, Long> {
    List<Election> findByCreatedBy(User user);
}
