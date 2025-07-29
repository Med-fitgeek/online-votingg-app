package com.evoting.evote_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;


public record ElectionResultDTO (Long optionId, String optionLabel, long voteCount){}
