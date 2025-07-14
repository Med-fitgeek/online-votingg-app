package com.evoting.evote_backend.repository;

import com.evoting.evote_backend.entity.Option;
import com.evoting.evote_backend.entity.VoterToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface VoterTokenRepository extends JpaRepository<VoterToken, Long> {
    Optional<VoterToken> findByToken(UUID token);
    boolean existsByToken(UUID token);
    long countBySelectedOption(Option option);
}
