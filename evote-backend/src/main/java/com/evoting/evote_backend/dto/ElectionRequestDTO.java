package com.evoting.evote_backend.dto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class ElectionRequestDTO {
    private String title;
    private String description;
    private List<String> options;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<String> voters;
}

