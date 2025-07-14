package com.evoting.evote_backend.dto;

import java.util.UUID;

public record VoteRequestDTO(UUID token, Long optionId) {}
