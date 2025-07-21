package com.evoting.evote_backend.repository;

import com.evoting.evote_backend.entity.Election;
import com.evoting.evote_backend.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OptionRepository extends JpaRepository<Option, Long> {
    List<Option> findByElection(Election election);
}
