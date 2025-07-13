package com.evoting.evote_backend.repository;

import com.evoting.evote_backend.entity.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, Long> {
}
