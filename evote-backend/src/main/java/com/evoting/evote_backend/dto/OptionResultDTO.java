package com.evoting.evote_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OptionResultDTO {
    private Long optionId;
    private String optionText;
    private long voteCount;
}
