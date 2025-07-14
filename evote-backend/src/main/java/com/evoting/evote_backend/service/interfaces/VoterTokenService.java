package com.evoting.evote_backend.service.interfaces;

import com.evoting.evote_backend.dto.VoteRequestDTO;

import java.util.UUID;

public interface VoterTokenService {
    void vote(VoteRequestDTO request);
}
