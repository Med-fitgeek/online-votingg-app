package com.evoting.evote_backend.service.interfaces;

import java.util.UUID;

public interface VoterTokenService {
    void vote(UUID token, Long optionId);
}
